package LinguaLink;

import LinguaLink.components.wordblock.WordBlock;
import LinguaLink.guiComponents.wordBankPanel.WordBankPanel;
import LinguaLink.logger.Logger;
import LinguaLink.components.word.PartOfSpeech;
import LinguaLink.components.word.Word;
import LinguaLink.guiComponents.wordBankPanel.ComplexCellRenderer;
import LinguaLink.guiComponents.workSpacePanel.WorkSpacePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class App extends JFrame implements ModelObserver {
    private Model model;
    private Controller controller;
    private WordBankPanel wordBankPanel;
    private WorkSpacePanel workSpacePanel;
    private DefaultListModel<Word> wordListRender;

    public App() {
        super("Interactive Language Learning Tool");
        model = Model.getInstance();
        model.addObserver(this);
        controller = Controller.getInstance();
        initUI();
    }

    private void initUI() {
        // Get the screen size and determine the maximum allowable size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = Math.min(screenSize.width, 1920);
        int height = Math.min(screenSize.height, 1080);

        // Suggest a preferred size for the window
        setPreferredSize(new Dimension(width, height));

        // Set the maximum size to prevent exceeding 1920x1080 or the screen size
        setMaximumSize(new Dimension(width, height));

        // Pack the frame to respect preferred size
        pack();

        // Set default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Center the window
        setLocationRelativeTo(null);

        setupMenuBar();
        setupMainAndSideLayout();
    }


    private void setupMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(e -> {
            Util.saveApplicationState(model);
        });
        fileMenu.add(saveItem);
        JMenuItem loadItem = new JMenuItem("Load");
        loadItem.addActionListener(e -> {
            Util.loadApplicationState(model);
        });
        fileMenu.add(loadItem);
        fileMenu.add(new JSeparator());
        JMenuItem exportItem = new JMenuItem("Export");
        exportItem.addActionListener(e -> {
            Util.exportPanelAsImage(workSpacePanel);
        });
        fileMenu.add(exportItem);
        fileMenu.add(new JSeparator());
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);

        JMenu editMenu = new JMenu("Edit");
        JMenuItem clearWordBank = new JMenuItem("Clear Word Bank");
        clearWordBank.addActionListener(e -> {
            wordBankPanel.clear();
            controller.clearWordBank();
        });
        editMenu.add(clearWordBank);
        JMenuItem clearWorkSpace = new JMenuItem("Clear Work Space");
        clearWorkSpace.addActionListener(e -> {
            controller.clearWorkSpace();
        });
        editMenu.add(clearWorkSpace);
        menuBar.add(editMenu);

        setJMenuBar(menuBar);
    }

    private void constructWordBank() {
        wordBankPanel = new WordBankPanel();
    }

    private void constructWorkSpace() {
        workSpacePanel = new WorkSpacePanel();

        for (WordBlock wordBlock : model.getWorkSpaceWordBlocks()) {
            workSpacePanel.addWordBlock(wordBlock);
        }
    }

    private void setupMainAndSideLayout() {
        // Initialize the panels
        constructWordBank();
        constructWorkSpace();

        // Create a split pane that divides the workspace and the word bank
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, workSpacePanel, wordBankPanel);
        splitPane.setOneTouchExpandable(true);
        splitPane.setResizeWeight(1); // Give more space to the workspace initially, adjust as necessary

        // Adding the split pane to the frame's content pane
        getContentPane().add(splitPane, BorderLayout.CENTER);
    }

    @Override
    public void update() {
        // Refresh UI components based on the updated model
        refreshWordBank();
        refreshWorkSpace();
    }

    private void refreshWordBank() {
        List<Word> currentWords = model.getWordBankWords();
        wordBankPanel.refreshWordBank(currentWords);
    }

    private void refreshWorkSpace() {
        workSpacePanel.clearWordBlocks();
        for (WordBlock wordBlock : model.getWorkSpaceWordBlocks()) {
            workSpacePanel.addWordBlock(wordBlock); // Add WordBlocks from the model
        }
        workSpacePanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App().setVisible(true));
    }
}

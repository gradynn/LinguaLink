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
        setupMenuBar();
        setupMainAndSideLayout();
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

    private JPanel constructWordBank() {
        WordBankPanel wordBankPanel = new WordBankPanel();
        return wordBankPanel;
    }

    private JPanel constructWorkSpace() {
        WorkSpacePanel workSpacePanel = new WorkSpacePanel();

        for (WordBlock wordBlock : model.getWorkSpaceWordBlocks()) {
            workSpacePanel.addWordBlock(wordBlock);
        }

        return workSpacePanel;
    }

    private void setupMainAndSideLayout() {
        wordBankPanel = (WordBankPanel) constructWordBank();
        workSpacePanel = (WorkSpacePanel) constructWorkSpace();
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, workSpacePanel, wordBankPanel);
        splitPane.setSize(1920, 1080);
        splitPane.setDividerLocation(0.8);
        splitPane.setOneTouchExpandable(false); // Disable the one-touch expandable buttons
        splitPane.setEnabled(false); // Disable the divider so it can't be moved
        splitPane.setDividerSize(0);

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

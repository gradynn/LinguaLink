package LinguaLink;

import LinguaLink.components.wordblock.WordBlock;
import LinguaLink.logger.Logger;
import LinguaLink.components.word.PartOfSpeech;
import LinguaLink.components.word.Word;
import LinguaLink.guiComponents.ComplexCellRenderer;
import LinguaLink.guiComponents.workSpacePanel.WorkSpacePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class App extends JFrame implements ModelObserver {
    private Model model;
    private Controller controller;
    private JPanel wordBankPanel;
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
        fileMenu.add(saveItem);
        JMenuItem loadItem = new JMenuItem("Load");
        fileMenu.add(loadItem);
        fileMenu.add(new JSeparator());
        JMenuItem exportItem = new JMenuItem("Export");
        fileMenu.add(exportItem);
        fileMenu.add(new JSeparator());
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);

        JMenu editMenu = new JMenu("Edit");
        JMenuItem clearWordBank = new JMenuItem("Clear Word Bank");
        clearWordBank.addActionListener(e -> {
            wordListRender.clear();
            controller.clearWordBank();
            Logger.info("WordBank cleared");
        });
        editMenu.add(clearWordBank);
        menuBar.add(editMenu);

        setJMenuBar(menuBar);
    }

    private JPanel constructWordBank() {
        wordBankPanel = new JPanel();
        wordBankPanel.setLayout(new BorderLayout()); // Use BorderLayout for the entire panel

        // Upper panel with input fields and add button
        JPanel inputPanel = new JPanel(new FlowLayout());

        // Create text input for word
        JTextField wordInput = new JTextField(20); // 20 columns width
        inputPanel.add(wordInput);

        // Create drop down with parts of speech
        JComboBox<PartOfSpeech> partsOfSpeechDropdown = new JComboBox<>(PartOfSpeech.values());
        inputPanel.add(partsOfSpeechDropdown);

        // Create add button
        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            String wordText = wordInput.getText().trim();
            PartOfSpeech selectedPos = (PartOfSpeech) partsOfSpeechDropdown.getSelectedItem();
            if (!wordText.isEmpty()) {
                Word newWord = new Word(wordText, selectedPos);
                controller.addWordBankElement(newWord);
                wordInput.setText(""); // Clear input field
            } else {
                Logger.error("Empty word string could not be added to WordBank");
            }
        });
        inputPanel.add(addButton);

        // Create the word list model
        wordListRender = new DefaultListModel<>();

        // Create the JList and set it to use the word list model
        JList<Word> wordList = new JList<>(wordListRender);
        wordList.setCellRenderer(new ComplexCellRenderer());
        wordList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        wordList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = wordList.locationToIndex(e.getPoint());
                    if (index >= 0) {
                        Word selectedWord = wordList.getModel().getElementAt(index);
                        controller.moveWordToWorkSpace(selectedWord);
                    }
                }
            }
        });

        // Add JList to a scroll pane (in case of many entries)
        JScrollPane listScroller = new JScrollPane(wordList);
        listScroller.setBorder(null);

        // Create a split pane to hold both the input panel and the list scroller
        JSplitPane splitPane = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                inputPanel,
                listScroller);
        splitPane.setResizeWeight(0); // The inputPanel area will not expand with the window size
        splitPane.setDividerLocation(75); // You can adjust this value as needed
        splitPane.setOneTouchExpandable(false); // Disable the one-touch expandable buttons
        splitPane.setEnabled(false); // Disable the divider so it can't be moved
        splitPane.setDividerSize(0);

        // Add the split pane to the wordBankPanel
        wordBankPanel.add(splitPane, BorderLayout.CENTER);

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
        wordBankPanel = constructWordBank();
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

        wordListRender.clear();

        for (Word word : currentWords) {
            wordListRender.addElement(word);
        }
        Logger.info("WordBank updated.");
    }

    private void refreshWorkSpace() {
        workSpacePanel.clearWordBlocks(); // Clear existing WordBlocks

        for (WordBlock wordBlock : model.getWorkSpaceWordBlocks()) {
            workSpacePanel.addWordBlock(wordBlock); // Add WordBlocks from the model
        }

        workSpacePanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App().setVisible(true));
    }
}

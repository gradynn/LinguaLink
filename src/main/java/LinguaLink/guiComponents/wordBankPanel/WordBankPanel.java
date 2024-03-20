package LinguaLink.guiComponents.wordBankPanel;

import LinguaLink.components.word.PartOfSpeech;
import LinguaLink.components.word.Word;
import LinguaLink.guiComponents.wordTransferable.WordTransferable;
import LinguaLink.logger.Logger;
import LinguaLink.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class WordBankPanel extends JPanel {
	private Controller controller;
	private DefaultListModel<Word> wordListModel;
	private JList<Word> wordList;

	public WordBankPanel() {
		this.controller = Controller.getInstance();
		this.setLayout(new BorderLayout());
		initializeUI();
	}

	private void initializeUI() {
		JPanel inputPanel = new JPanel(new FlowLayout());
		JTextField wordInput = new JTextField(20);
		JComboBox<PartOfSpeech> partsOfSpeechDropdown = new JComboBox<>(PartOfSpeech.values());
		JButton addButton = new JButton("Add");

		addButton.addActionListener(e -> {
			String wordText = wordInput.getText().trim();
			PartOfSpeech selectedPos = (PartOfSpeech) partsOfSpeechDropdown.getSelectedItem();
			if (!wordText.isEmpty()) {
				Word newWord = new Word(wordText, selectedPos);
				controller.addWordBankElement(newWord);
				wordInput.setText("");
			} else {
				Logger.error("Empty word string could not be added to WordBank");
			}
		});

		inputPanel.add(wordInput);
		inputPanel.add(partsOfSpeechDropdown);
		inputPanel.add(addButton);

		wordListModel = new DefaultListModel<>();
		wordList = new JList<>(wordListModel);

		wordList.setDragEnabled(true);
		wordList.setTransferHandler(new TransferHandler() {
			@Override
			protected Transferable createTransferable(JComponent c) {
				JList<Word> list = (JList<Word>) c;
				Word selectedWord = list.getSelectedValue();
				return new WordTransferable(selectedWord);
			}

			@Override
			public int getSourceActions(JComponent c) {
				return TransferHandler.COPY;
			}
		});

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

		JScrollPane listScroller = new JScrollPane(wordList);
		listScroller.setBorder(null);

		JSplitPane splitPane = new JSplitPane(
				JSplitPane.VERTICAL_SPLIT,
				inputPanel,
				listScroller);
		splitPane.setResizeWeight(0.1);
		splitPane.setDividerLocation(75);
		splitPane.setOneTouchExpandable(false);
		splitPane.setEnabled(false);
		splitPane.setDividerSize(0);

		this.add(splitPane, BorderLayout.CENTER);
	}

	public void refreshWordBank(List<Word> words) {
		wordListModel.clear();
		for (Word word : words) {
			wordListModel.addElement(word);
		}
	}
}

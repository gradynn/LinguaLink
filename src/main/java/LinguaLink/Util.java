package LinguaLink;

import LinguaLink.components.connection.Connection;
import LinguaLink.components.word.PartOfSpeech;
import LinguaLink.components.word.Word;
import LinguaLink.components.wordblock.WordBlock;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Util {
	/**
	 * Returns the background color associated with a given part of speech.
	 * @param pos The part of speech for which the background color is needed.
	 * @return The background color for the specified part of speech.
	 */
	public static Color getBackgroundColor(PartOfSpeech pos) {
		return switch (pos) {
			case NOUN -> new Color(0, 95, 115);
			case VERB -> new Color(10, 147, 150);
			case PRONOUN -> new Color(148, 210, 189);
			case ARTICLE -> new Color(233, 216, 166);
			case ADVERB -> new Color(238, 155, 0);
			case ADJECTIVE -> new Color(202, 103, 2);
			case CONJUNCTION -> new Color(187, 62, 3);
			case PREPOSITION -> new Color(174, 32, 18);
			case INTERJECTION -> Color.getHSBColor(155, 34, 38);
		};
	}

	/**
	 * Returns the primary text color for a given part of speech.
	 * @param pos The part of speech for which the primary text color is needed.
	 * @return The primary text color for the specified part of speech.
	 */
	public static Color getPrimaryTextColor(PartOfSpeech pos) {
		ArrayList<PartOfSpeech> lightColors = new ArrayList<>();
		lightColors.add(PartOfSpeech.PRONOUN);
		lightColors.add(PartOfSpeech.ARTICLE);
		if (lightColors.contains(pos)) { return Color.BLACK; }
		return Color.WHITE;
	}

	/**
	 * Returns the secondary text color for a given part of speech.
	 * @param pos The part of speech for which the secondary text color is needed.
	 * @return The secondary text color for the specified part of speech.
	 */
	public static Color getSecondaryTextColor(PartOfSpeech pos) {
		ArrayList<PartOfSpeech> lightColors = new ArrayList<>();
		lightColors.add(PartOfSpeech.PRONOUN);
		lightColors.add(PartOfSpeech.ARTICLE);
		if (lightColors.contains(pos)) { return Color.DARK_GRAY; }
		return Color.LIGHT_GRAY;
	}

	/**
	 * Exports a JPanel as an image file, allowing the user to save it to their system.
	 * @param panel The JPanel to export as an image.
	 */
	public static void exportPanelAsImage(JPanel panel) {
		// 1. Capture the JPanel contents as an image
		BufferedImage image = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = image.createGraphics();
		panel.paint(g2d);
		g2d.dispose();

		// 2. Open a file chooser to let the user select the save location
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Save as PNG");
		fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("PNG Image", "png"));

		int userSelection = fileChooser.showSaveDialog(panel);

		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToSave = fileChooser.getSelectedFile();
			String filePath = fileToSave.getAbsolutePath();

			// Ensure the file has a .png extension
			if (!filePath.toLowerCase().endsWith(".png")) {
				fileToSave = new File(filePath + ".png");
			}

			// 3. Write the image to the selected file
			try {
				ImageIO.write(image, "PNG", fileToSave);
				JOptionPane.showMessageDialog(panel, "Saved image to: " + fileToSave.getAbsolutePath(), "Image Saved Successfully", JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(panel, "Error saving image: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Saves the current application state to a file, including word bank and workspace elements.
	 * @param model The model containing the current application state.
	 */
	public static void saveApplicationState(Model model) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Save Work Space");
		if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			if (!file.getPath().toLowerCase().endsWith(".llws")) {
				file = new File(file.getPath() + ".llws");
			}

			List<Word> wordBank = model.getWordBankWords();
			List<WordBlock> workSpace = model.getWorkSpaceWordBlocks();
			List<Connection> connections = model.getActiveConnections();

			try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
				for (Word word : wordBank) {
					writer.write("WordBank:" + word.getWord() + "," + word.getPartOfSpeech() + "\n");
				}
				for (WordBlock block : workSpace) {
					writer.write("WorkSpace:" + block.getWord().getWord() + "," + block.getWord().getPartOfSpeech() + "," + block.getPosition().x + "," + block.getPosition().y + "\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Loads the application state from a file, updating the word bank and workspace elements.
	 * @param model The model to update with the loaded application state.
	 */
	public static void loadApplicationState(Model model) {
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("LinguaLink WorkSpace Files (*.llws)", "llws");
		fileChooser.setFileFilter(filter);
		fileChooser.setDialogTitle("Open Work Space");

		List<Word> loadedWordBank = new ArrayList<>();
		List<WordBlock> loadedWorkSpace = new ArrayList<>();

		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			if (!file.getPath().toLowerCase().endsWith(".llws")) {
				JOptionPane.showMessageDialog(null, "Invalid file type selected. Please select a .llws file.", "Invalid File Type", JOptionPane.ERROR_MESSAGE);
				return; // Return empty lists if file type is incorrect
			}

			try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
				String line;
				while ((line = reader.readLine()) != null) {
					if (line.startsWith("WordBank:")) {
						String[] details = line.substring("WordBank:".length()).split(",");
						if (details.length == 2) {
							loadedWordBank.add(new Word(details[0], PartOfSpeech.valueOf(details[1])));
						}
					} else if (line.startsWith("WorkSpace:")) {
						String[] details = line.substring("WorkSpace:".length()).split(",");
						if (details.length == 4) {
							Word word = new Word(details[0], PartOfSpeech.valueOf(details[1]));
							Point point = new Point(Integer.parseInt(details[2]), Integer.parseInt(details[3]));
							loadedWorkSpace.add(new WordBlock(point, word));
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		model.loadFromFile(loadedWordBank, loadedWorkSpace);
	}
}

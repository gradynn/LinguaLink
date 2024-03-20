package LinguaLink;

import LinguaLink.components.word.PartOfSpeech;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class Util {
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

	public static Color getPrimaryTextColor(PartOfSpeech pos) {
		ArrayList<PartOfSpeech> lightColors = new ArrayList<>();
		lightColors.add(PartOfSpeech.PRONOUN);
		lightColors.add(PartOfSpeech.ARTICLE);
		if (lightColors.contains(pos)) { return Color.BLACK; }
		return Color.WHITE;
	}

	public static Color getSecondaryTextColor(PartOfSpeech pos) {
		ArrayList<PartOfSpeech> lightColors = new ArrayList<>();
		lightColors.add(PartOfSpeech.PRONOUN);
		lightColors.add(PartOfSpeech.ARTICLE);
		if (lightColors.contains(pos)) { return Color.DARK_GRAY; }
		return Color.LIGHT_GRAY;
	}

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
}

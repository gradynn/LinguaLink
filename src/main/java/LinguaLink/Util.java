package LinguaLink;

import LinguaLink.components.word.PartOfSpeech;

import java.awt.*;
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
}

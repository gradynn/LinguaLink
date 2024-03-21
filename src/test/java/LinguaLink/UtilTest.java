package LinguaLink;

import LinguaLink.Util;
import LinguaLink.components.word.PartOfSpeech;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;

public class UtilTest {
	@Test
	public void testGetBackgroundColor() {
		// Test each PartOfSpeech with the expected color
		Assertions.assertEquals(new Color(0, 95, 115), Util.getBackgroundColor(PartOfSpeech.NOUN));
		Assertions.assertEquals(new Color(10, 147, 150), Util.getBackgroundColor(PartOfSpeech.VERB));
		// Continue for other PartOfSpeech values...
	}

	@Test
	public void testGetPrimaryTextColor() {
		// Assuming the method logic and expected colors
		Assertions.assertEquals(Color.BLACK, Util.getPrimaryTextColor(PartOfSpeech.PRONOUN));
		Assertions.assertEquals(Color.WHITE, Util.getPrimaryTextColor(PartOfSpeech.NOUN));
		// Continue for other PartOfSpeech values...
	}

	@Test
	public void testGetSecondaryTextColor() {
		// Assuming the method logic and expected colors
		Assertions.assertEquals(Color.DARK_GRAY, Util.getSecondaryTextColor(PartOfSpeech.PRONOUN));
		Assertions.assertEquals(Color.LIGHT_GRAY, Util.getSecondaryTextColor(PartOfSpeech.NOUN));
		// Continue for other PartOfSpeech values...
	}
}

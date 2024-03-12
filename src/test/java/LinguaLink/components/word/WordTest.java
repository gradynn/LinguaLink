package LinguaLink.components.word;

import LinguaLink.components.word.Word;
import LinguaLink.components.word.PartOfSpeech;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WordTest {
    @Test
    @DisplayName("Get word value")
    void testGetWord() {
        Word word = new Word("Hello", PartOfSpeech.NOUN);
        assertEquals(word.getWord(), "Hello");
    }

    @Test
    @DisplayName("Create a word with each PartOfSpeech and verify getters")
    void testWordCreationAndGetters() {
        for (PartOfSpeech pos : PartOfSpeech.values()) {
            String testWord = "testWord";
            Word word = new Word(testWord, pos);

            assertEquals(testWord, word.getWord(), "The getWord method should return the correct word.");
            assertEquals(pos, word.getPartOfSpeech(), "The getPartOfSpeech method should return the correct PartOfSpeech.");
        }
    }
}

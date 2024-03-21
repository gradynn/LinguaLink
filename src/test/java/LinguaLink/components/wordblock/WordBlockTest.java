package LinguaLink.components.wordblock;

import LinguaLink.components.word.Word;
import LinguaLink.components.word.PartOfSpeech;
import org.junit.jupiter.api.Test;

import java.awt.Point;

import static org.junit.jupiter.api.Assertions.*;

public class WordBlockTest {

    @Test
    public void testWordBlockConstructorAndGetters() {
        Point testPoint = new Point(10, 20);
        Word testWord = new Word("example", PartOfSpeech.NOUN);
        WordBlock wordBlock = new WordBlock(testPoint, testWord);

        assertNotNull(wordBlock, "WordBlock object should not be null");
        assertEquals(testPoint, wordBlock.getPosition(), "Position should match constructor argument");
        assertEquals(testWord.getWord(), wordBlock.getWord().getWord(), "Word should match constructor argument");
        assertEquals(testWord.getPartOfSpeech(), wordBlock.getWord().getPartOfSpeech(), "PartOfSpeech should match constructor argument");
    }

    @Test
    public void testSetPosition() {
        WordBlock wordBlock = new WordBlock(new Point(5, 5), new Word("change", PartOfSpeech.VERB));
        wordBlock.setPosition(15, 25);

        assertEquals(15, wordBlock.getPosition().x, "X position should be updated");
        assertEquals(25, wordBlock.getPosition().y, "Y position should be updated");
    }

    @Test
    public void testGetPositionImmutability() {
        Point initialPosition = new Point(30, 40);
        WordBlock wordBlock = new WordBlock(initialPosition, new Word("immutable", PartOfSpeech.ADJECTIVE));

        Point position = wordBlock.getPosition();
        position.translate(10, 10);  // Attempt to mutate the returned position

        // The WordBlock's position should remain unchanged
        assertNotEquals(position, wordBlock.getPosition(), "WordBlock's position should be immutable");
    }

    @Test
    public void testGetWordImmutability() {
        Word initialWord = new Word("constant", PartOfSpeech.NOUN);
        WordBlock wordBlock = new WordBlock(new Point(50, 60), initialWord);

        Word word = wordBlock.getWord();
        assertNotSame(initialWord, word, "Word should be a new instance");
    }
}

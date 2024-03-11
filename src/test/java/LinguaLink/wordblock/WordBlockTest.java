package LinguaLink.wordblock;

import LinguaLink.components.wordblock.WordBlock;
import LinguaLink.components.coordinate.Coordinate;
import LinguaLink.components.word.Word;
import LinguaLink.components.word.PartOfSpeech;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WordBlockTest {

    private WordBlock wordBlock;
    private Word testWord;
    private Coordinate initialPosition;

    @BeforeEach
    void setUp() {
        // Setup initial conditions for the tests
        initialPosition = new Coordinate(0, 0); // Assuming a Coordinate class with an appropriate constructor exists
        testWord = new Word("test", PartOfSpeech.NOUN); // Assuming a Word class with an appropriate constructor exists
        wordBlock = new WordBlock(initialPosition, testWord); // Assuming default or parameterized constructor exists

        wordBlock.setPosition(initialPosition.getX(), initialPosition.getY()); // Setup initial position
        // Assuming a method to set Word directly or via constructor exists
    }

    @Test
    @DisplayName("getPosition should return a new instance with the same x and y")
    void testGetPosition() {
        Coordinate position = wordBlock.getPosition();
        assertAll(
                () -> assertEquals(initialPosition.getX(), position.getX(), "X coordinate should match initial position"),
                () -> assertEquals(initialPosition.getY(), position.getY(), "Y coordinate should match initial position")
        );
    }

    @Test
    @DisplayName("getWord should return a copy of the Word")
    void testGetWord() {
        Word retrievedWord = wordBlock.getWord();
        assertAll(
                () -> assertEquals(testWord.getWord(), retrievedWord.getWord(), "Word text should match"),
                () -> assertEquals(testWord.getPartOfSpeech(), retrievedWord.getPartOfSpeech(), "Part of speech should match")
        );
    }

    @Test
    @DisplayName("setPosition should update the WordBlock's position")
    void testSetPosition() {
        int newX = 5, newY = 10;
        wordBlock.setPosition(newX, newY);
        Coordinate newPosition = wordBlock.getPosition();
        assertAll(
                () -> assertEquals(newX, newPosition.getX(), "X coordinate should be updated"),
                () -> assertEquals(newY, newPosition.getY(), "Y coordinate should be updated")
        );
    }
}

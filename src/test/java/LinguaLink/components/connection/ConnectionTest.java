package LinguaLink.components.connection;

import LinguaLink.components.word.PartOfSpeech;
import LinguaLink.components.word.Word;
import LinguaLink.components.wordblock.WordBlock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Point;

import static org.junit.jupiter.api.Assertions.*;

public class ConnectionTest {
    private WordBlock from;
    private WordBlock to;
    private Connection connection;

    @BeforeEach
    public void setup() {
        Word fromWord = new Word("apple", PartOfSpeech.NOUN);
        Word toWord = new Word("red", PartOfSpeech.ADJECTIVE);
        from = new WordBlock(new Point(0, 0), fromWord);
        to = new WordBlock(new Point(10, 10), toWord);
        connection = new Connection(from, to);
    }

    @Test
    public void testGetFrom() {
        assertEquals(from, connection.getFrom());
    }

    @Test
    public void testGetTo() {
        assertEquals(to, connection.getTo());
    }

    @Test
    public void testIsValidWithInvalidConnection() {
        assertFalse(connection.isValid());
    }

    @Test
    public void testIsValidWithValidConnection() {
        Word newToWord = new Word("quickly", PartOfSpeech.ADVERB);
        WordBlock newTo = new WordBlock(new Point(20, 20), newToWord);
        Connection newConnection = new Connection(from, newTo);
        assertTrue(newConnection.isValid());
    }

    @Test
    public void testContainsTrue() {
        assertTrue(connection.contains(from));
    }

    @Test
    public void testContainsFalse() {
        Word otherWord = new Word("blue", PartOfSpeech.ADJECTIVE);
        WordBlock otherBlock = new WordBlock(new Point(30, 30), otherWord);
        assertFalse(connection.contains(otherBlock));
    }
}

package LinguaLink.containers.wordbank;

import LinguaLink.components.word.Word;
import LinguaLink.components.word.PartOfSpeech;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordBankTest {
    private WordBank wordBank;

    @BeforeEach
    void setUp() {
        wordBank = WordBank.getInstance();
        wordBank.clearWords(); // Ensure each test starts with a clean slate
    }

    @Test
    void testSingletonInstance() {
        WordBank anotherInstance = WordBank.getInstance();
        assertSame(wordBank, anotherInstance, "WordBank should be a singleton");
    }

    @Test
    void testAddWord() {
        Word word = new Word("test", PartOfSpeech.NOUN);
        wordBank.addWord(word);
        assertFalse(wordBank.getWords().isEmpty(), "WordBank should contain words after addition");
        assertTrue(wordBank.getWords().contains(word), "WordBank should contain the added word");
    }

    @Test
    void testRemoveWord() {
        Word word = new Word("test", PartOfSpeech.NOUN);
        wordBank.addWord(word);
        wordBank.removeWord(word);
        assertFalse(wordBank.getWords().contains(word), "WordBank should not contain the removed word");
    }

    @Test
    void testClearWords() {
        wordBank.addWord(new Word("test1", PartOfSpeech.NOUN));
        wordBank.addWord(new Word("test2", PartOfSpeech.VERB));
        wordBank.clearWords();
        assertTrue(wordBank.getWords().isEmpty(), "WordBank should be empty after clearing words");
    }

    @Test
    void testGetWordsImmutability() {
        wordBank.addWord(new Word("immutable", PartOfSpeech.ADJECTIVE));
        List<Word> words = wordBank.getWords();

        // Attempting to modify the returned list should result in an UnsupportedOperationException
        assertThrows(UnsupportedOperationException.class, () -> words.add(new Word("test", PartOfSpeech.NOUN)),
                "The words list should be immutable");
    }
}

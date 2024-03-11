package LinguaLink.containers.wordbank;

import LinguaLink.components.word.Word;
import LinguaLink.components.word.PartOfSpeech;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class WordBankTest {

    private WordBank wordBank;
    private Word word1;
    private Word word2;

    @BeforeEach
    void setUp() {
        wordBank = WordBank.getInstance(); // Get the singleton instance
        word1 = new Word("apple", PartOfSpeech.NOUN);
        word2 = new Word("run", PartOfSpeech.VERB);

        // Clear the wordBank list for a fresh start
       wordBank.clearWords();
    }

    @Test
    @DisplayName("WordBank is a singleton class")
    void testSingleton() {
        WordBank anotherInstance = WordBank.getInstance();
        assertSame(wordBank, anotherInstance, "There should only be one instance of WordBank");
    }

    @Test
    @DisplayName("Words can be added to the WordBank")
    void testAddWord() {
        wordBank.addWord(word1);
        assertTrue(wordBank.getWords().contains(word1), "WordBank should contain the added word");
    }

    @Test
    @DisplayName("Duplicate words are not added to the WordBank")
    void testAddDuplicateWord() {
        wordBank.addWord(word1);
        wordBank.addWord(word1); // Attempt to add the same word again
        List<Word> words = wordBank.getWords();
        assertEquals(1, words.size(), "WordBank should not contain duplicates of the same word");
    }

    @Test
    @DisplayName("Words can be removed from the WordBank")
    void testRemoveWord() {
        wordBank.addWord(word1);
        wordBank.removeWord(word1);
        assertFalse(wordBank.getWords().contains(word1), "WordBank should not contain the removed word");
    }

    @Test
    @DisplayName("getWords returns an unmodifiable list")
    void testGetWordsUnmodifiable() {
        wordBank.addWord(word1);
        List<Word> words = wordBank.getWords();

        assertThrows(UnsupportedOperationException.class, () -> {
            // Attempt to modify the list directly
            words.add(word2);
        }, "The list returned by getWords should be unmodifiable");
    }
}

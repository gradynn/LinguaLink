package LinguaLink.containers.wordbank;

import LinguaLink.components.word.Word;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WordBank {
    private static WordBank wordBank = null;
    private final ArrayList<Word> words;

    /**
     * Private Class Constructor for Singleton class.
     */
    private WordBank() {
        words = new ArrayList<>();
    }

    /**
     * Get reference to Singleton WordBank class.
     * @return WordBank object.
     */
    public static WordBank getInstance() {
        if (wordBank == null) {
            wordBank = new WordBank();
        }
        return wordBank;
    }

    /**
     * Gets Word objects active in WordBank.
     * @return Unmodifiable list of Word objects.
     */
    public List<Word> getWords() {
        return Collections.unmodifiableList(words);
    }

    /**
     * Add a new word to the WordBank.
     * @param word: Word object to be added.
     */
    public void addWord(Word word) {
        words.add(word);
    }

    /**
     * Removes a word from the WorkSpace if present.
     * @param word: Word object to remove.
     */
    public void removeWord(Word word) {
        words.remove(word);
    }

    /**
     * Completely clears WordBank of all Words.
     */
    public void clearWords() {
        words.clear();
    }
}

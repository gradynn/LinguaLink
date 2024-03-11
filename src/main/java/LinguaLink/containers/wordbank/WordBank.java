package LinguaLink.containers.wordbank;

import LinguaLink.components.word.Word;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WordBank {
    private static WordBank wordBank = null;
    private final ArrayList<Word> words;

    public WordBank() {
        words = new ArrayList<>();
    }

    public static WordBank getInstance() {
        if (wordBank == null) {
            wordBank = new WordBank();
        }
        return wordBank;
    }

    public List<Word> getWords() {
        return Collections.unmodifiableList(words);
    }

    public void addWord(Word word) {
        words.add(word);
    }

    public void removeWord(Word word) {
        words.remove(word);
    }

    public void clearWords() {
        words.clear();
    }
}

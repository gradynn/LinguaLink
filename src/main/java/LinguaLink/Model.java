package LinguaLink;

import LinguaLink.components.word.Word;
import LinguaLink.containers.wordbank.WordBank;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Model {
    private static Model model = null;
    private WordBank wordBank;
    private List<ModelObserver> observers = new ArrayList<>();

    private Model() {
        wordBank = new WordBank();
    }

    public static Model getInstance() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    public void addObserver(ModelObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(ModelObserver observer) {
        observers.remove(observer);
    }

    protected void notifyObservers() {
        for (ModelObserver observer : observers) {
            observer.update();
        }
    }

    // Example method that would trigger observer notification
    public void addWordToBank(Word word) {
        wordBank.addWord(word);
        notifyObservers();
    }

    public List<Word> getWordBankWords() {
        return wordBank.getWords();
    }

    public void clearWordBank() {
        wordBank.clearWords();
    }

}

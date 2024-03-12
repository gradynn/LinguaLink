package LinguaLink;

import LinguaLink.components.word.Word;
import LinguaLink.components.wordblock.WordBlock;
import LinguaLink.containers.wordbank.WordBank;
import LinguaLink.containers.workspace.WorkSpace;
import LinguaLink.logger.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Model {
    private static Model model = null;
    private WordBank wordBank;
    private WorkSpace workSpace;
    private List<ModelObserver> observers = new ArrayList<>();

    private Model() {
        wordBank = WordBank.getInstance();
        workSpace = WorkSpace.getInstance();
        Logger.info("New Model initialized.");
    }

    public static Model getInstance() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    public void resetInstance() {
        wordBank.clearWords();
        workSpace.clearWorkSpace();
        observers.clear();
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

    public void addWordToBank(Word word) {
        wordBank.addWord(word);
        notifyObservers();
    }

    public List<Word> getWordBankWords() {
        return wordBank.getWords();
    }

    public void clearWordBank() {
        wordBank.clearWords();
        notifyObservers();
    }

    public void moveWordToWorkSpace(Word toMove) {
        wordBank.removeWord(toMove);
        workSpace.addWord(toMove);
        notifyObservers();
    }

}

package LinguaLink;

import LinguaLink.components.connection.Connection;
import LinguaLink.components.word.Word;
import LinguaLink.components.wordblock.WordBlock;
import LinguaLink.containers.wordbank.WordBank;
import LinguaLink.containers.workspace.WorkSpace;
import LinguaLink.exceptions.NonExistentWordBlockException;
import LinguaLink.exceptions.NonExistentWordException;
import LinguaLink.logger.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Model {
    private static Model model = null;
    private WordBank wordBank;
    private WorkSpace workSpace;
    private List<ModelObserver> observers;

    /**
     * Constructs Singleton Publisher Model
     */
    private Model() {
        wordBank = WordBank.getInstance();
        workSpace = WorkSpace.getInstance();
        observers = new ArrayList<>();
        Logger.info("New Model initialized.");
    }

    /**
     * Returns singleton instance, or constructs new one if first call.
     * @return Singleton instance of Model
     */
    public static Model getInstance() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    /**
     * Resets singleton instance, for testing purposes.
     */
    public void resetInstance() {
        wordBank.clearWords();
        workSpace.clearWorkSpace();
        observers.clear();
    }

    /**
     * Adds an observer (subscriber) object if not already in observers.
     * @param observer: Object implementing ModelObserver class to
     */
    public void addObserver(ModelObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    /**
     * Removes an observer (subscriber) if present in observers.
     * @param observer: Object implementing ModelObserver class
     */
    public void removeObserver(ModelObserver observer) {
        observers.remove(observer);
    }

    /**
     * Keystone method for observer pattern. Triggers all subscribed ModelObserver's update methods.
     */
    protected void notifyObservers() {
        for (ModelObserver observer : observers) {
            observer.update();
        }
    }

    /**
     * Adds a new word to WordBank, start of word lifecycle
     * @param word: Properly instantiated Word object instance
     */
    public void addWordToBank(Word word) {
        wordBank.addWord(word);
        notifyObservers();
    }

    /**
     * Returns immutable list of currently inactive (in WordBank not WorkSpace) words.
     * @return immutable list of Words objects in WordBank
     */
    public List<Word> getWordBankWords() {
        return Collections.unmodifiableList(wordBank.getWords());
    }

    /**
     * Returns an immutable list of WordBlocks active in the Work Space.
     * @return Immutable List of WordBlock objects
     */
    public List<WordBlock> getWorkSpaceWordBlocks() {
        return Collections.unmodifiableList(workSpace.getWordBlocks());
    }

    /**
     * Clears WordBank of all words.
     */
    public void clearWordBank() {
        wordBank.clearWords();
        notifyObservers();
    }

    /**
     * Clears WorkSpace of all words.
     */
    public void clearWorkSpace() {
        workSpace.clearWorkSpace();
        notifyObservers();
    }

    /**
     * Moves a Word from the WordBank to the WorkSpaces
     * @param toMove a Word object to remove from the WordBank and add to the WorkSpace.
     * @throws NonExistentWordException
     */
    public WordBlock moveWordToWorkSpace(Word toMove, int x, int y) throws NonExistentWordException {
        if (!getWordBankWords().contains(toMove)) {
            throw new NonExistentWordException();
        }
        wordBank.removeWord(toMove);
        WordBlock addedWordBlock = workSpace.addWord(toMove, x, y);
        notifyObservers();
        return addedWordBlock;
    }

    /**
     * Moves active word from WorkSpace to WordBank
     * @param toMove WordBlock object to convert to Word and move to WorkSpace
     * @throws NonExistentWordBlockException
     */
    public void moveWordToWordBank(WordBlock toMove) throws NonExistentWordBlockException {
        if (!workSpace.getWordBlocks().contains(toMove)) {
            throw new NonExistentWordBlockException();
        }
        workSpace.removeWordBlock(toMove);
        Word extractedWord = toMove.getWord();
        wordBank.addWord(extractedWord);
        notifyObservers();
    }

    /**
     * Deletes a WordBlock from the WorkSpace.
     * @param toDelete WordBlock object to delete.
     */
    public void deleteWordBlock(WordBlock toDelete) {
        workSpace.removeWordBlock(toDelete);
        notifyObservers();
    }

    /**
     * Adds a new connection between two WordBlocks to the WorkSpace.
     * @param toAdd new connection object to add to the workspace.
     */
    public void addConnection(Connection toAdd) {
        workSpace.addConnection(toAdd);
        notifyObservers();
    }

    /**
     * Removes a specific connection from the workspace.
     * @param toDelete a Connection object to be deleted.
     */
    public void deleteConnection(Connection toDelete) {
        workSpace.removeConnection(toDelete);
        notifyObservers();
    }

    /**
     * Deletes a word from the Word Bank.
     * @param toDelete a Word to be deleted.
     */
    public void deleteWord(Word toDelete) {
        wordBank.removeWord(toDelete);
        notifyObservers();
    }

    /**
     * Gets a list of all active connection in the WorkSpace.
     * @return Unmodifiable list of Connections.
     */
    public List<Connection> getActiveConnections() {
        return workSpace.getConnections();
    }

    /**
     * Takes a new application state, retrieved from a file, and uses it to repopulate the current application state
     * with that loaded data.
     * @param newWordBank List of Word objects representing a new WordBank state.
     * @param newWorkSpace List of WordBlocks representing a new WorkSpace state.
     */
    public void loadFromFile(List<Word> newWordBank, List<WordBlock> newWorkSpace) {
        wordBank.clearWords();
        for (Word w : newWordBank) {
            wordBank.addWord(w);
        }

        workSpace.clearWorkSpace();
        for (WordBlock w : newWorkSpace) {
            workSpace.addWord(w.getWord(), w.getPosition().x, w.getPosition().y);
        }

        notifyObservers();
    }
}

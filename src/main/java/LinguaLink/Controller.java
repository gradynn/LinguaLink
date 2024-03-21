package LinguaLink;

import LinguaLink.components.connection.Connection;
import LinguaLink.components.wordblock.WordBlock;
import LinguaLink.exceptions.NonExistentWordBlockException;
import LinguaLink.exceptions.NonExistentWordException;
import LinguaLink.logger.Logger;
import LinguaLink.components.word.Word;

import java.awt.*;

public class Controller {
    private static Controller controller = null;
    private Model model;

    /**
     * Private constructor to prevent direct instantiation and enforce singleton pattern.
     */
    private Controller() {
        model = Model.getInstance();
        Logger.info("New Controller initialized.");
    }

    /**
     * Gets the single instance of the Controller class.
     * @return The singleton Controller instance.
     */
    public static Controller getInstance() {
        if (controller == null) {
            controller = new Controller();
        }
        return controller;
    }

    /**
     * Adds a word element to the word bank.
     * @param in The Word to add.
     */
    public void addWordBankElement(Word in) {
        model.addWordToBank(in);
        Logger.info(in.getPartOfSpeech() + " " + in.getWord() +  " added successfully to wordBank.");
    }

    /**
     * Adds a connection between two word blocks.
     * @param c The connection to add.
     */
    public void addConnection(Connection c) {
        model.addConnection(c);
        Logger.info(
                "Connection from " + c.getFrom().getWord().getWord() + " to " + c.getTo().getWord().getWord() + "."
        );
    }

    /**
     * Clears all words from the word bank.
     */
    public void clearWordBank() {
        model.clearWordBank();
        Logger.info("WordBank cleared.");
    }

    /**
     * Clears all word blocks and connections from the workspace.
     */
    public void clearWorkSpace() {
        model.clearWorkSpace();
        Logger.info("WorkSpace cleared.");
    }

    /**
     * Moves a word from the word bank to the workspace at a default position.
     * @param toMove The word to be moved.
     * @return The created WordBlock in the workspace.
     */
    public WordBlock moveWordToWorkSpace(Word toMove) {
        try {
            WordBlock createdWordBlock =  model.moveWordToWorkSpace(toMove, 0, 0);
            Logger.info("Word " + toMove.getWord() + " moved to work space.");
            return createdWordBlock;
        } catch (NonExistentWordException e) {
            Logger.error(
                    "Cannot move word to work space from word bank. Word " +
                            toMove.getWord() + " does not exist in word bank."
            );
            throw new RuntimeException(e);
        }
    }

    /**
     * Moves a word from the word bank to the workspace at a specified position.
     * @param toMove The word to be moved.
     * @param x The x-coordinate of the position.
     * @param y The y-coordinate of the position.
     * @return The created WordBlock in the workspace.
     */
    public WordBlock moveWordToWorkSpace(Word toMove, int x, int y) {
        try {
            WordBlock createdWordBlock =  model.moveWordToWorkSpace(toMove, x, y);
            Logger.info("Word " + toMove.getWord() + " moved to work space.");
            return createdWordBlock;
        } catch (NonExistentWordException e) {
            Logger.error(
                    "Cannot move word to work space from word bank. Word " +
                            toMove.getWord() + " does not exist in word bank."
            );
            throw new RuntimeException(e);
        }
    }

    /**
     * Moves a word block from the workspace back to the word bank.
     * @param toMove The word block to be moved.
     */
    public void moveWordToWordBank(WordBlock toMove) {
        try {
            model.moveWordToWordBank(toMove);
        } catch (NonExistentWordBlockException e) {
            throw new RuntimeException(e);
        }
        Logger.info("Word " + toMove.getWord().getWord() + " moved to word bank.");
    }

    /**
     * Sets the position of a word block in the workspace relative to its current position.
     * @param toSet The word block to set the position of.
     * @param dx The change in the x-coordinate.
     * @param dy The change in the y-coordinate.
     */
    public void setWordBlockPosition(WordBlock toSet, int dx, int dy) {
        Point prevPosition = toSet.getPosition();
        toSet.setPosition(prevPosition.x + dx, prevPosition.y + dy);
    }

    /**
     * Sets the absolute position of a word block in the workspace.
     * @param toSet The word block to set the position of.
     * @param x The x-coordinate of the position.
     * @param y The y-coordinate of the position.
     */
    public void setWordBlockPositionAbs(WordBlock toSet, int x, int y) {
        toSet.setPosition(x, y);
    }

    /**
     * Deletes a word block from the workspace.
     * @param toDelete The word block to delete.
     */
    public void deleteWordBlock(WordBlock toDelete) {
        model.deleteWordBlock(toDelete);
    }

    /**
     * Deletes a connection between two word blocks.
     * @param toDelete The connection to delete.
     */
    public void deleteConnection(Connection toDelete) {
        model.deleteConnection(toDelete);
    }
}

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

    private Controller() {
        model = Model.getInstance();
        Logger.info("New Controller initialized.");
    }


    public static Controller getInstance() {
        if (controller == null) {
            controller = new Controller();
        }
        return controller;
    }

    public void addWordBankElement(Word in) {
        model.addWordToBank(in);
        Logger.info(in.getPartOfSpeech() + " " + in.getWord() +  " added successfully to wordBank.");
    }

    public void addConnection(Connection c) {
        model.addConnection(c);
        Logger.info("Connection from " + c.getFrom().getWord().getWord() + " to " + c.getTo().getWord().getWord() + ".");
    }

    public void clearWordBank() {
        model.clearWordBank();
        Logger.info("WordBank cleared.");
    }

    public void clearWorkSpace() {
        model.clearWorkSpace();
        Logger.info("WorkSpace cleared.");
    }

    public WordBlock moveWordToWorkSpace(Word toMove) {
        try {
            WordBlock createdWordBlock =  model.moveWordToWorkSpace(toMove, 0, 0);
            Logger.info("Word " + toMove.getWord() + " moved to work space.");
            return createdWordBlock;
        } catch (NonExistentWordException e) {
            Logger.error("Cannot move word to work space from word bank. Word " + toMove.getWord() + " does not exist in word bank.");
            throw new RuntimeException(e);
        }
    }

    public WordBlock moveWordToWorkSpace(Word toMove, int x, int y) {
        try {
            WordBlock createdWordBlock =  model.moveWordToWorkSpace(toMove, x, y);
            Logger.info("Word " + toMove.getWord() + " moved to work space.");
            return createdWordBlock;
        } catch (NonExistentWordException e) {
            Logger.error("Cannot move word to work space from word bank. Word " + toMove.getWord() + " does not exist in word bank.");
            throw new RuntimeException(e);
        }
    }

    public void moveWordToWordBank(WordBlock toMove) {
        try {
            model.moveWordToWordBank(toMove);
        } catch (NonExistentWordBlockException e) {
			throw new RuntimeException(e);
		}
		Logger.info("Word " + toMove.getWord().getWord() + " moved to word bank.");
    }

    public void setWordBlockPosition(WordBlock toSet, int dx, int dy) {
        Point prevPosition = toSet.getPosition();
        toSet.setPosition(prevPosition.x + dx, prevPosition.y + dy);
    }

    public void setWordBlockPositionAbs(WordBlock toSet, int x, int y) {
        toSet.setPosition(x, y);
    }

    public void deleteWordBlock(WordBlock toDelete) {
        model.deleteWordBlock(toDelete);
    }

    public void deleteConnection(Connection toDelete) {
        model.deleteConnection(toDelete);
    }
}

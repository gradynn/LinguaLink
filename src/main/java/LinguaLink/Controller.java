package LinguaLink;

import LinguaLink.logger.Logger;
import LinguaLink.components.word.Word;

public class Controller {
    private static Controller controller = null;

    private Controller() {
        Logger.info("New Controller initialized.");
    }

    public static Controller getInstance() {
        if (controller == null) {
            controller = new Controller();
        }
        return controller;
    }

    public void addWordBankElement(Word in) {
        Model.getInstance().addWordToBank(in);
        Logger.info(in.getPartOfSpeech() + " " + in.getWord() +  " added successfully to wordBank.");
    }

    public void clearWordBank() {
        Model.getInstance().clearWordBank();
        Logger.info("WordBank cleared successfully.");
    }

    public void moveWordToWorkSpace(Word toMove) {
        Model.getInstance().moveWordToWorkSpace(toMove);
        Logger.info("Word " + toMove.getWord() + " moved to work space.");
    }
}

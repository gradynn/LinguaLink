package LinguaLink;

import LinguaLink.logger.Logger;
import LinguaLink.components.word.Word;

public class Controller {
    public static void addWordBankElement(Word in) {
        Model.getInstance().addWordToBank(in);
        Logger.info(in.getPartOfSpeech() + " " + in.getWord() +  " added successfully to wordBank.");
    }
}

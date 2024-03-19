package LinguaLink.components.connection;

import LinguaLink.components.word.PartOfSpeech;
import LinguaLink.components.wordblock.WordBlock;

import java.util.Arrays;
import java.util.List;

public class Connection {
    private final WordBlock from;
    private final WordBlock to;

    public Connection(WordBlock from, WordBlock to) {
        this.from = from;
        this.to = to;
    }

    public WordBlock getFrom() {
        return from;
    }

    public WordBlock getTo() {
        return to;
    }

    public boolean isValid() {
        PartOfSpeech fromPos = from.getWord().getPartOfSpeech();
        PartOfSpeech toPos = to.getWord().getPartOfSpeech();

        List<PartOfSpeech> places = Arrays.asList(PartOfSpeech.NOUN, PartOfSpeech.PRONOUN, PartOfSpeech.CONJUNCTION, PartOfSpeech.CONJUNCTION, PartOfSpeech.PREPOSITION, PartOfSpeech.ARTICLE);

        if (fromPos == PartOfSpeech.NOUN && toPos == PartOfSpeech.ADJECTIVE) {
            return false;
        } else if (fromPos == PartOfSpeech.ADJECTIVE && toPos == PartOfSpeech.VERB) {
            return false;
        } else if (fromPos == PartOfSpeech.ADVERB && toPos == PartOfSpeech.NOUN) {
            return false;
        } else if (fromPos == PartOfSpeech.PREPOSITION && toPos == PartOfSpeech.VERB) {
            return false;
        }  else if (fromPos == toPos && places.contains(fromPos)) {
            return false;
        }
        return true;
    }

    public boolean contains(WordBlock wordBlock) {
        return (from == wordBlock || to == wordBlock);
    }
}

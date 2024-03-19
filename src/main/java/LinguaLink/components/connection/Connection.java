package LinguaLink.components.connection;

import LinguaLink.components.wordblock.WordBlock;

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
        return false;
    }

    public boolean contains(WordBlock wordBlock) {
        return (from == wordBlock || to == wordBlock);
    }
}

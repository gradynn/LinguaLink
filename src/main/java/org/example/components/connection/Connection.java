package org.example.components.connection;

import org.example.components.word.Word;
import org.example.components.wordblock.WordBlock;

public class Connection implements IConnection {
    private final WordBlock from;
    private final WordBlock to;

    public Connection(WordBlock from, WordBlock to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public WordBlock getFrom() {
        return from;
    }

    @Override
    public WordBlock getTo() {
        return to;
    }

    @Override
    public boolean isValid() {
        return false;
    }
}

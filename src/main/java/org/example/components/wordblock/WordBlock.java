package org.example.components.wordblock;

import org.example.components.coordinate.Coordinate;
import org.example.components.word.Word;

public class WordBlock {
    private Coordinate position;
    private Word word;

    public Coordinate getPosition() {
        return new Coordinate(this.position.getX(), this.position.getY());
    }

    public Word getWord() {
        return new Word(this.word.getWord(), this.word.getPartOfSpeech());
    }

    public void setPosition(int x, int y) {
        this.position = new Coordinate(x, y);
    }
}

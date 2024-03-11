package LinguaLink.components.wordblock;

import LinguaLink.components.coordinate.Coordinate;
import LinguaLink.components.word.Word;

public class WordBlock {
    private Coordinate position;
    private Word word;

    public WordBlock(Coordinate p, Word w) {
        this.position = p;
        this.word = w;
    }

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

package LinguaLink.components.wordblock;

import LinguaLink.components.word.Word;
import java.awt.Point;

public class WordBlock {
    private Point position;
    private Word word;

    public WordBlock(Point p, Word w) {
        this.position = p;
        this.word = w;
    }

    public Point getPosition() {
        return new Point(position.x, position.y);
    }

    public Word getWord() {
        return new Word(this.word.getWord(), this.word.getPartOfSpeech());
    }

    public void setPosition(int x, int y) {
        this.position = new Point(x, y);
    }
}

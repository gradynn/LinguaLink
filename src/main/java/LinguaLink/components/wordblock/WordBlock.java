package LinguaLink.components.wordblock;

import LinguaLink.components.word.Word;
import java.awt.Point;

public class WordBlock {
    private Point position;
    private Word word;

    /**
     * Class Constructor
     * @param p: Point object representing WordBlock's desired location in the WorkSpace.
     * @param w: Word object representing a word in the WorkSpace.
     */
    public WordBlock(Point p, Word w) {
        this.position = p;
        this.word = w;
    }

    /**
     * Gets WordBlock's position in WorkSpace
     * @return Point object.
     */
    public Point getPosition() {
        return new Point(position.x, position.y);
    }

    /**
     * Gets WordBlocks underlying Word value.
     * @return Word object.
     */
    public Word getWord() {
        return new Word(this.word.getWord(), this.word.getPartOfSpeech());
    }

    /**
     * Update word block position.
     * @param x: int
     * @param y: int
     */
    public void setPosition(int x, int y) {
        this.position = new Point(x, y);
    }
}

package LinguaLink.components.word;

public class Word {
    public static Object PartOfSpeech;
    private final String word;
    private final PartOfSpeech pos;

    /**
     * Construct
     * @param word: string containing the word value.
     * @param pos: PartOfSpeech enum representing the words enum.
     */
    public Word(String word, PartOfSpeech pos) {
        this.word = word;
        this.pos = pos;
    }

    /**
     * Accessor for the string value of Word.
     * @return string
     */
    public String getWord() {
        return word;
    }

    /**
     * Accessor for part of speech of a word.
     * @return PartOfSpeech
     */
    public PartOfSpeech getPartOfSpeech() {
        return pos;
    }
}

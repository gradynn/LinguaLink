package LinguaLink.components.word;

public class Word {
    public static Object PartOfSpeech;
    private final String WORD;
    private final PartOfSpeech PART_OF_SPEECH;

    /**
     * Construct
     * @param word: string containing the word value.
     * @param pos: PartOfSpeech enum representing the words enum.
     */
    public Word(String word, PartOfSpeech pos) {
        this.WORD = word;
        this.PART_OF_SPEECH = pos;
    }

    /**
     * Accessor for the string value of Word.
     * @return string
     */
    public String getWord() {
        return WORD;
    }

    /**
     * Accessor for part of speech of a word.
     * @return PartOfSpeech
     */
    public PartOfSpeech getPartOfSpeech() {
        return PART_OF_SPEECH;
    }
}

package LinguaLink.components.word;

public class Word {
    public static Object PartOfSpeech;
    private String word;
    private PartOfSpeech pos;

    public Word(String word, PartOfSpeech pos) {
        this.word = word;
        this.pos = pos;
    }

    public String getWord() {
        return word;
    }

    public PartOfSpeech getPartOfSpeech() {
        return pos;
    }
}

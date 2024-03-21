package LinguaLink.components.word;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WordTest {

    @Test
    public void testWordConstructorAndGetters() {
        String testWord = "example";
        PartOfSpeech testPos = PartOfSpeech.NOUN;

        Word word = new Word(testWord, testPos);

        assertNotNull(word, "Word object should not be null");
        assertEquals(testWord, word.getWord(), "Word should be equal to " + testWord);
        assertEquals(testPos, word.getPartOfSpeech(), "PartOfSpeech should be equal to " + testPos);
    }

    @Test
    public void testWordPartOfSpeech() {
        Word noun = new Word("tree", PartOfSpeech.NOUN);
        Word verb = new Word("run", PartOfSpeech.VERB);
        Word adjective = new Word("fast", PartOfSpeech.ADJECTIVE);

        assertEquals(PartOfSpeech.NOUN, noun.getPartOfSpeech(), "Part of speech should be NOUN");
        assertEquals(PartOfSpeech.VERB, verb.getPartOfSpeech(), "Part of speech should be VERB");
        assertEquals(PartOfSpeech.ADJECTIVE, adjective.getPartOfSpeech(), "Part of speech should be ADJECTIVE");
    }

    @Test
    public void testWordEquality() {
        Word word1 = new Word("play", PartOfSpeech.VERB);
        Word word2 = new Word("play", PartOfSpeech.VERB);

        assertEquals(word1.getWord(), word2.getWord(), "Words should be equal");
        assertEquals(word1.getPartOfSpeech(), word2.getPartOfSpeech(), "Parts of Speech should be equal");
    }

    @Test
    public void testWordInequality() {
        Word word1 = new Word("good", PartOfSpeech.ADJECTIVE);
        Word word2 = new Word("well", PartOfSpeech.ADVERB);

        assertNotEquals(word1.getWord(), word2.getWord(), "Words should not be equal");
        assertNotEquals(word1.getPartOfSpeech(), word2.getPartOfSpeech(), "Parts of Speech should not be equal");
    }
}

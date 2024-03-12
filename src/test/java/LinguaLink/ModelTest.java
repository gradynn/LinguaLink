package LinguaLink;

import LinguaLink.components.word.PartOfSpeech;
import LinguaLink.components.word.Word;
import LinguaLink.containers.wordbank.WordBank;
import LinguaLink.containers.workspace.WorkSpace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ModelTest {
    private Model model;
    private WordBank wordBank;
    private WorkSpace workSpace;
    private ModelObserver observer;

    @BeforeEach
    void setUp() {
        wordBank = mock(WordBank.class);
        workSpace = mock(WorkSpace.class);
        observer = mock(ModelObserver.class);

        model = Model.getInstance();
        model.resetInstance();
    }

    @Test
    void testSingletonBehavior() {
        Model anotherInstance = Model.getInstance();
        assertSame(model, anotherInstance, "Model should return the same instance (singleton behavior)");
    }

    @Test
    void testAddObserver() {
        model.addObserver(observer);
        model.addWordToBank(new Word("test", PartOfSpeech.NOUN));
        verify(observer, times(1)).update();
    }

    @Test
    void testRemoveObserver() {
        model.addObserver(observer);
        model.removeObserver(observer);
        model.addWordToBank(new Word("test", PartOfSpeech.NOUN));
        verify(observer, times(0)).update(); // Verify observer is not notified
    }

}

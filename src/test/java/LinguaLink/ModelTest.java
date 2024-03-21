package LinguaLink;

import LinguaLink.Model;
import LinguaLink.components.connection.Connection;
import LinguaLink.components.word.PartOfSpeech;
import LinguaLink.components.word.Word;
import LinguaLink.components.wordblock.WordBlock;
import LinguaLink.containers.wordbank.WordBank;
import LinguaLink.containers.workspace.WorkSpace;
import LinguaLink.exceptions.NonExistentWordBlockException;
import LinguaLink.exceptions.NonExistentWordException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.awt.Point;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

class ModelTest {

    private Model model;

    @Mock
    private WordBank wordBank;

    @Mock
    private WorkSpace workSpace;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.initMocks(this);
        model = Model.getInstance(); // Retrieve the singleton instance

        // Inject mocks using reflection
        setPrivateField(model, "wordBank", wordBank);
        setPrivateField(model, "workSpace", workSpace);
    }

    private void setPrivateField(Object target, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    @Test
    void addWordToBank() {
        Word word = new Word("test", PartOfSpeech.NOUN);
        model.addWordToBank(word);
        verify(wordBank).addWord(word);
    }

    @Test
    void clearWordBank() {
        model.clearWordBank();
        verify(wordBank).clearWords();
    }

    @Test
    void clearWorkSpace() {
        model.clearWorkSpace();
        verify(workSpace).clearWorkSpace();
    }

    @Test
    void moveWordToWorkSpace() throws NonExistentWordException {
        Word word = new Word("test", PartOfSpeech.NOUN);
        when(wordBank.getWords()).thenReturn(Arrays.asList(word));
        model.moveWordToWorkSpace(word, 100, 200);
        verify(wordBank).removeWord(word);
        verify(workSpace).addWord(word, 100, 200);
    }

    @Test
    void moveWordToWordBank() throws NonExistentWordBlockException {
        Word word = new Word("test", PartOfSpeech.NOUN);
        WordBlock wordBlock = new WordBlock(new Point(100, 200), word);
        when(workSpace.getWordBlocks()).thenReturn(Arrays.asList(wordBlock));

        model.moveWordToWordBank(wordBlock);

        verify(workSpace).removeWordBlock(wordBlock);
        verify(wordBank).addWord(argThat(newWord -> newWord.getWord().equals(word.getWord()) && newWord.getPartOfSpeech() == word.getPartOfSpeech()));
    }

    @Test
    void deleteWordBlock() {
        WordBlock wordBlock = new WordBlock(new Point(100, 200), new Word("test", PartOfSpeech.NOUN));
        model.deleteWordBlock(wordBlock);
        verify(workSpace).removeWordBlock(wordBlock);
    }

    @Test
    void addConnection() {
        Connection connection = mock(Connection.class);
        model.addConnection(connection);
        verify(workSpace).addConnection(connection);
    }

    @Test
    void deleteConnection() {
        Connection connection = mock(Connection.class);
        model.deleteConnection(connection);
        verify(workSpace).removeConnection(connection);
    }

    @Test
    void getActiveConnections() {
        List<Connection> connections = Arrays.asList(mock(Connection.class));
        when(workSpace.getConnections()).thenReturn(connections);
        List<Connection> activeConnections = model.getActiveConnections();
        assert activeConnections.equals(connections);
    }

    @Test
    void loadFromFile() {
        List<Word> newWordBank = Arrays.asList(new Word("test", PartOfSpeech.NOUN));
        List<WordBlock> newWorkSpace = Arrays.asList(new WordBlock(new Point(100, 200), new Word("test", PartOfSpeech.NOUN)));
        model.loadFromFile(newWordBank, newWorkSpace);
        verify(wordBank).clearWords();
        verify(workSpace).clearWorkSpace();
        verify(wordBank).addWord(any(Word.class));
        verify(workSpace).addWord(any(Word.class), anyInt(), anyInt());
    }
}

package LinguaLink.containers.workSpace;

import LinguaLink.components.connection.Connection;
import LinguaLink.components.word.Word;
import LinguaLink.components.word.PartOfSpeech;
import LinguaLink.components.wordblock.WordBlock;
import LinguaLink.containers.workspace.WorkSpace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Point;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WorkSpaceTest {
	private WorkSpace workSpace;

	@BeforeEach
	void setUp() {
		workSpace = WorkSpace.getInstance();
		workSpace.clearWorkSpace(); // Ensure each test starts with a clean state
	}

	@Test
	void testSingletonInstance() {
		WorkSpace anotherInstance = WorkSpace.getInstance();
		assertSame(workSpace, anotherInstance, "WorkSpace should be a singleton");
	}

	@Test
	void testAddWordBlock() {
		Word word = new Word("test", PartOfSpeech.NOUN);
		WordBlock wordBlock = workSpace.addWord(word, 10, 20);
		assertNotNull(wordBlock, "addWord should return a new WordBlock");
		assertTrue(workSpace.getWordBlocks().contains(wordBlock), "WorkSpace should contain the added WordBlock");
	}

	@Test
	void testRemoveWordBlock() {
		Word word = new Word("test", PartOfSpeech.NOUN);
		WordBlock wordBlock = workSpace.addWord(word, 10, 20);
		workSpace.removeWordBlock(wordBlock);
		assertFalse(workSpace.getWordBlocks().contains(wordBlock), "WorkSpace should not contain the removed WordBlock");
	}

	@Test
	void testAddConnection() {
		WordBlock block1 = new WordBlock(new Point(0, 0), new Word("word1", PartOfSpeech.NOUN));
		WordBlock block2 = new WordBlock(new Point(100, 100), new Word("word2", PartOfSpeech.VERB));
		Connection connection = new Connection(block1, block2);
		workSpace.addConnection(connection);
		assertTrue(workSpace.getConnections().contains(connection), "WorkSpace should contain the added connection");
	}

	@Test
	void testRemoveConnection() {
		WordBlock block1 = new WordBlock(new Point(0, 0), new Word("word1", PartOfSpeech.NOUN));
		WordBlock block2 = new WordBlock(new Point(100, 100), new Word("word2", PartOfSpeech.VERB));
		Connection connection = new Connection(block1, block2);
		workSpace.addConnection(connection);
		workSpace.removeConnection(connection);
		assertFalse(workSpace.getConnections().contains(connection), "WorkSpace should not contain the removed connection");
	}

	@Test
	void testClearWorkSpace() {
		workSpace.addWord(new Word("test1", PartOfSpeech.NOUN), 10, 20);
		workSpace.addConnection(new Connection(new WordBlock(new Point(0, 0), new Word("word1", PartOfSpeech.NOUN)),
				new WordBlock(new Point(100, 100), new Word("word2", PartOfSpeech.VERB))));
		workSpace.clearWorkSpace();
		assertTrue(workSpace.getWordBlocks().isEmpty(), "WorkSpace should have no word blocks after clearing");
		assertTrue(workSpace.getConnections().isEmpty(), "WorkSpace should have no connections after clearing");
	}
}

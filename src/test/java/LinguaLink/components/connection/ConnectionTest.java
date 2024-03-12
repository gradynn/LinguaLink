package LinguaLink.components.connection;

import LinguaLink.components.connection.Connection;
import LinguaLink.components.wordblock.WordBlock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.*;

class ConnectionTest {

    private WordBlock from;
    private WordBlock to;
    private Connection connection;

    @BeforeEach
    void setUp() {
        // Mock the WordBlock objects
        from = mock(WordBlock.class);
        to = mock(WordBlock.class);

        // Initialize Connection with mocked WordBlock objects
        connection = new Connection(from, to);
    }

    @Test
    @DisplayName("Test constructor with valid WordBlocks")
    void testConstructorWithValidWordBlocks() {
        assertNotNull(connection, "Connection should be initialized");
    }

    @Test
    @DisplayName("Test getFrom() returns correct WordBlock")
    void testGetFrom() {
        assertEquals(from, connection.getFrom(), "getFrom() should return the correct WordBlock");
    }

    @Test
    @DisplayName("Test getTo() returns correct WordBlock")
    void testGetTo() {
        assertEquals(to, connection.getTo(), "getTo() should return the correct WordBlock");
    }

    @Test
    @DisplayName("Test isValid() returns false")
    void testIsValid() {
        assertFalse(connection.isValid(), "isValid() should return false");
    }
}

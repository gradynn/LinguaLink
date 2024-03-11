package LinguaLink.Coordinate;

import LinguaLink.components.coordinate.Coordinate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.*;

class CoordinateTest {
    private Coordinate coordinate;

    @BeforeEach
    void setUp() {
        coordinate = new Coordinate(10, 10);
    }

    @Test
    @DisplayName("Test constructor with valid inputs")
    void testConstructorWithValidWordBlocks() {
        assertNotNull(coordinate, "Connection should be initialized");
    }

    @Test
    @DisplayName("Test getX to return correct x")
    void testGetX() {
        assertEquals(10, coordinate.getX());
    }

    @Test
    @DisplayName("Test getY to return correct y")
    void testGetY() {
        assertEquals(10, coordinate.getY());
    }

    @Test
    @DisplayName("Test setX to correctly update x")
    void testSetX() {
        coordinate.setX(100);
        assertEquals(100, coordinate.getX());
    }

    @Test
    @DisplayName("Test setX to correctly update x")
    void testSetY() {
        coordinate.setY(100);
        assertEquals(100, coordinate.getY());
    }
}

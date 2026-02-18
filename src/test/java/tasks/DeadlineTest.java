package tasks;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit tests for the Deadline class.
 */
public class DeadlineTest {
    /**
     * Tests that the toString method returns the correct formatted string for a deadline.
     */
    @Test
    public void toString_correctFormat() {
        Deadline d = new Deadline("return book", "2023-12-01 1800");
        String expected = "[D][ ] return book (by: Dec 1 2023 6:00pm)";
        assertEquals(expected, d.toString());
    }

    /**
     * Tests that marking a deadline as done updates the string representation to show [X].
     */
    @Test
    public void markAsDone_toStringShowsX() {
        Deadline d = new Deadline("return book", "2023-12-01 1800");
        d.markAsDone();
        assertTrue(d.toString().contains("[X]"));
    }

    /**
     * Tests that constructing a Deadline with an invalid date format throws an exception.
     */
    @Test
    public void invalidDateFormat_throwsException() {
        assertThrows(Exception.class, () -> new Deadline("return book", "not-a-date"));
    }
}

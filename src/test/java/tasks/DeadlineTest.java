package tasks;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DeadlineTest {
    @Test
    public void toString_correctFormat() {
        Deadline d = new Deadline("return book", "2023-12-01 1800");
        String expected = "[D][ ] return book (by: Dec 1 2023 6:00pm)";
        assertEquals(expected, d.toString());
    }

    @Test
    public void markAsDone_toStringShowsX() {
        Deadline d = new Deadline("return book", "2023-12-01 1800");
        d.markAsDone();
        assertTrue(d.toString().contains("[X]"));
    }

    @Test
    public void invalidDateFormat_throwsException() {
        assertThrows(Exception.class, () -> new Deadline("return book", "not-a-date"));
    }
}

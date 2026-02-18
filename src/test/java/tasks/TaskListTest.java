package tasks;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit tests for the TaskList class.
 */
public class TaskListTest {
    /**
     * Tests that adding a task increases the size of the TaskList.
     */
    @Test
    public void addTask_increasesSize() {
        TaskList tasks = new TaskList();
        assertEquals(0, tasks.size());
        tasks.addTask(new Todo("read book"));
        assertEquals(1, tasks.size());
    }

    /**
     * Tests that removing a task returns the correct task and decreases the size.
     */
    @Test
    public void removeTask_returnsCorrectTaskAndDecreasesSize() {
        TaskList tasks = new TaskList();
        Todo todo = new Todo("read book");
        tasks.addTask(todo);
        Task removed = tasks.removeTask(0);
        assertEquals(todo, removed);
        assertEquals(0, tasks.size());
    }

    /**
     * Tests that getting a task with an out-of-bounds index throws an exception.
     */
    @Test
    public void getTask_outOfBounds_throwsException() {
        TaskList tasks = new TaskList();
        assertThrows(IndexOutOfBoundsException.class, () -> tasks.getTask(0));
    }
}

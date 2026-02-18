package tasks;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaskListTest {
    @Test
    public void addTask_increasesSize() {
        TaskList tasks = new TaskList();
        assertEquals(0, tasks.size());
        tasks.addTask(new Todo("read book"));
        assertEquals(1, tasks.size());
    }

    @Test
    public void removeTask_returnsCorrectTaskAndDecreasesSize() {
        TaskList tasks = new TaskList();
        Todo todo = new Todo("read book");
        tasks.addTask(todo);
        Task removed = tasks.removeTask(0);
        assertEquals(todo, removed);
        assertEquals(0, tasks.size());
    }

    @Test
    public void getTask_outOfBounds_throwsException() {
        TaskList tasks = new TaskList();
        assertThrows(IndexOutOfBoundsException.class, () -> tasks.getTask(0));
    }
}

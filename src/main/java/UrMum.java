import exceptions.UrmumException;
import storage.Storage;
import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.TaskList;
import tasks.Todo;
import ui.Parser;
import ui.Ui;
import view.UrMumBackend;

/**
 * Main class for the UrMum chatbot application.
 * Handles user interaction, command parsing, and task management.
 */
public class UrMum {
    public static void main(String[] args) {
        Ui ui = new Ui();
        UrMumBackend backend = new UrMumBackend();

        ui.showWelcome();

        while (true) {
            String input = ui.readCommand();
            String response = backend.getResponse(input);
            ui.showMessage(response);
            if (input.trim().equalsIgnoreCase("bye")) {
                ui.close();
                return;
            }
        }
    }
}

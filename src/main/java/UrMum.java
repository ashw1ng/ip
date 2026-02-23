import ui.Ui;
import view.UrMumBackend;
import exceptions.UrmumException;

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
            try {
                String response = backend.getResponse(input);
                ui.showMessage(response);
                if (input.trim().equalsIgnoreCase("bye")) {
                    ui.close();
                    return;
                }
            } catch (UrmumException e) {
                ui.showError(e.getMessage());
            }
        }
    }
}

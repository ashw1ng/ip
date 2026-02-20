package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.application.Platform;

/**
 * Controller for MainWindow. Provides the layout for the main window.
 */
public class MainWindow {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private UrMumBackend backend;

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    public void setBackend(UrMumBackend backend) {
        this.backend = backend;
        dialogContainer.getChildren().add(new Label(backend.getWelcome()));
    }

    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input.trim().isEmpty())
            return;
        dialogContainer.getChildren().add(new Label("You: " + input));
        String response = backend.getResponse(input);
        dialogContainer.getChildren().add(new Label("UrMum: " + response));
        userInput.clear();

        if (input.trim().equalsIgnoreCase("bye")) {
            Platform.exit();
        }
    }
}

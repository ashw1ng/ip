package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.application.Platform;
import javafx.scene.image.Image;

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
        dialogContainer.getChildren().add(DialogBox.getBotDialog(backend.getWelcome(), new Image("/images/urmum.png")));
    }

    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input.trim().isEmpty())
            return;
        dialogContainer.getChildren().add(DialogBox.getUserDialog(input, new Image("/images/user.png")));
        String response = backend.getResponse(input);
        dialogContainer.getChildren().add(DialogBox.getBotDialog(response, new Image("/images/urmum.png")));
        userInput.clear();

        if (input.trim().equalsIgnoreCase("bye")) {
            Platform.exit();
        }
    }
}

package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

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
        dialogContainer.getChildren().add(createDialog(backend.getWelcome(), "/images/urmum.png", false));
    }

    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input.trim().isEmpty())
            return;
        dialogContainer.getChildren().add(createDialog("You: " + input, "/images/user.png", true));
        String response = backend.getResponse(input);
        dialogContainer.getChildren().add(createDialog("UrMum: " + response, "/images/urmum.png", false));
        userInput.clear();

        if (input.trim().equalsIgnoreCase("bye")) {
            Platform.exit();
        }
    }

    private HBox createDialog(String text, String imagePath, boolean isUser) {
        Label label = new Label(text);
        label.setWrapText(true); // Enable text wrapping
        // label.setMaxWidth(250); // Set a reasonable max width for the label

        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(imagePath)));
        imageView.setFitWidth(80);
        imageView.setFitHeight(80);

        HBox hbox;
        if (isUser) {
            // User: label first, then image, right aligned
            hbox = new HBox(label, imageView);
            hbox.setAlignment(javafx.geometry.Pos.TOP_RIGHT);
        } else {
            // Bot: image first, then label, left aligned
            hbox = new HBox(imageView, label);
            hbox.setAlignment(javafx.geometry.Pos.TOP_LEFT);
        }
        hbox.setSpacing(10);
        hbox.setMaxWidth(Double.MAX_VALUE); // Allow HBox to expand
        return hbox;
    }
}

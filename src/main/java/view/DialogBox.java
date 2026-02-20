package view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.fxml.FXMLLoader;

public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    public DialogBox(String text, Image img, String styleClass) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        dialog.setText(text);
        dialog.getStyleClass().add(styleClass);
        displayPicture.setImage(img);
    }

    public static DialogBox getUserDialog(String text, Image img) {
        DialogBox db = new DialogBox(text, img, "user-bubble");
        db.setAlignment(javafx.geometry.Pos.TOP_RIGHT);
        db.getChildren().setAll(db.dialog, db.displayPicture);
        return db;
    }

    public static DialogBox getBotDialog(String text, Image img) {
        DialogBox db = new DialogBox(text, img, "bot-bubble");
        db.setAlignment(javafx.geometry.Pos.TOP_LEFT);
        db.getChildren().setAll(db.displayPicture, db.dialog);
        return db;
    }
}

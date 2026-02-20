import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import view.MainWindow;
import view.UrMumBackend;

/**
 * JavaFX GUI for the UrMum chatbot using FXML.
 */
public class UrMumApp extends Application {
    private UrMumBackend backend = new UrMumBackend();

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MainWindow.fxml"));
        Parent root = fxmlLoader.load();
        MainWindow controller = fxmlLoader.getController();
        controller.setBackend(backend);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("UrMum Chatbot");
        stage.show();
    }
}

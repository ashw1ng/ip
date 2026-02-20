import javafx.application.Application;

/**
 * Launcher class to workaround JavaFX classpath issues.
 */
public class UrMumLauncher {
    public static void main(String[] args) {
        Application.launch(UrMumApp.class, args);
    }
}

package ui;

import java.util.Scanner;

import tasks.TaskList;

/**
 * Handles all interactions with the user, including input and output.
 */
public class Ui {
    private Scanner scanner;

    /**
     * Constructs a Ui object and initializes the scanner for user input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Reads the next line of user input.
     * 
     * @return The user's input as a String.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays the welcome message to the user.
     */
    public void showWelcome() {
        System.out.println(" Hello! I'm UrMum, your personal task manager.");
        System.out.println(" What can I do for you?");
    }

    /**
     * Displays the goodbye message to the user.
     */
    public void showGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    /**
     * Displays an error message to the user.
     * 
     * @param message The error message to display.
     */
    public void showError(String message) {
        System.out.println(message);
    }

    /**
     * Displays the current list of tasks to the user.
     * 
     * @param tasks The TaskList containing the tasks to display.
     */
    public void showTaskList(TaskList tasks) {
        System.out.println(" Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(" " + (i + 1) + "." + tasks.getTask(i));
        }
    }

    /**
     * Closes the scanner used for user input.
     */
    public void close() {
        scanner.close();
    }
}

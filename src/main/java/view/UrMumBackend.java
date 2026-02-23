package view;

import storage.Storage;
import tasks.*;
import exceptions.UrmumException;

/**
 * Backend wrapper for the UrMum chatbot logic, for use with the GUI.
 */
public class UrMumBackend {
    private TaskList tasks;
    private Storage storage;

    public UrMumBackend() {
        tasks = new TaskList();
        storage = new Storage("data", "data/urMum.txt");
        storage.loadTasks(tasks.getTasks());
    }

    public String getWelcome() {
        return "Hello! I'm UrMum, your personal task manager.\nWhat can I do for you?";
    }

    /**
     * Processes user input and returns the chatbot's response.
     * 
     * @param input The user's input.
     * @return The chatbot's response.
     * @throws UrmumException If an error occurs during processing.
     */
    public String getResponse(String input) throws UrmumException {
        String command = ui.Parser.getCommand(input);
        String arguments = ui.Parser.getArguments(input);
        switch (command) {
            case "bye": {
                return "Bye. Hope to see you again soon!";
            }
            case "list": {
                return getTaskListString();
            }
            case "mark": {
                int idx;
                try {
                    idx = Integer.parseInt(arguments) - 1;
                } catch (NumberFormatException e) {
                    throw new UrmumException("Please enter a valid task number.");
                }
                if (idx < 0 || idx >= tasks.size()) {
                    throw new UrmumException("That task number doesn't exist. Please try again.");
                }
                tasks.getTask(idx).markAsDone();
                storage.saveTasks(tasks.getTasks());
                return "Nice! I've marked this task as done:\n" + tasks.getTask(idx);
            }
            case "unmark": {
                int idx;
                try {
                    idx = Integer.parseInt(arguments) - 1;
                } catch (NumberFormatException e) {
                    throw new UrmumException("Please enter a valid task number.");
                }
                if (idx < 0 || idx >= tasks.size()) {
                    throw new UrmumException("That task number doesn't exist. Please try again.");
                }
                tasks.getTask(idx).markAsNotDone();
                storage.saveTasks(tasks.getTasks());
                return "OK, I've marked this task as not done yet:\n" + tasks.getTask(idx);
            }
            case "delete": {
                int idx;
                try {
                    idx = Integer.parseInt(arguments) - 1;
                } catch (NumberFormatException e) {
                    throw new UrmumException("Please enter a valid task number.");
                }
                if (idx < 0 || idx >= tasks.size()) {
                    throw new UrmumException("That task number doesn't exist. Please try again.");
                }
                Task removed = tasks.removeTask(idx);
                storage.saveTasks(tasks.getTasks());
                return "Noted. I've removed this task:\n" + removed +
                        "\nNow you have " + tasks.size() + " tasks in the list.";
            }
            case "todo": {
                String desc = arguments.trim();
                if (desc.isEmpty()) {
                    throw new UrmumException("Oops! You need to provide a description for a todo.");
                }
                tasks.addTask(new Todo(desc));
                storage.saveTasks(tasks.getTasks());
                return "Got it. I've added this task:\n" + tasks.getTask(tasks.size() - 1) +
                        "\nNow you have " + tasks.size() + " tasks in the list.";
            }
            case "deadline": {
                String[] parts = arguments.split(" /by ", 2);
                String desc = parts[0].trim();
                String by = parts.length > 1 ? parts[1].trim() : "";
                if (desc.isEmpty()) {
                    throw new UrmumException("Please provide a description for the deadline.");
                }
                if (by.isEmpty()) {
                    throw new UrmumException("Please specify a /by date and time for the deadline.");
                }
                try {
                    tasks.addTask(new Deadline(desc, by));
                    storage.saveTasks(tasks.getTasks());
                    return "Got it. I've added this task:\n" + tasks.getTask(tasks.size() - 1) +
                            "\nNow you have " + tasks.size() + " tasks in the list.";
                } catch (Exception e) {
                    throw new UrmumException(
                            "Please enter the date and time in yyyy-MM-dd HHmm format, e.g., 2019-12-02 1800");
                }
            }
            case "event": {
                String[] parts = arguments.split(" /from ", 2);
                String desc = parts[0].trim();
                String from = "";
                String to = "";
                if (parts.length > 1) {
                    String[] timeParts = parts[1].split(" /to ", 2);
                    from = timeParts[0].trim();
                    if (timeParts.length > 1) {
                        to = timeParts[1].trim();
                    }
                }
                if (desc.isEmpty()) {
                    throw new UrmumException("Please provide a description for the event.");
                }
                if (from.isEmpty() || to.isEmpty()) {
                    throw new UrmumException("Please specify both /from and /to times for the event.");
                }
                try {
                    tasks.addTask(new Event(desc, from, to));
                    storage.saveTasks(tasks.getTasks());
                    return "Got it. I've added this task:\n" + tasks.getTask(tasks.size() - 1) +
                            "\nNow you have " + tasks.size() + " tasks in the list.";
                } catch (Exception e) {
                    throw new UrmumException(
                            "Please enter the date and time in yyyy-MM-dd HHmm format, e.g., 2026-02-20 1800");
                }
            }
            case "find": {
                String keyword = arguments.trim();
                if (keyword.isEmpty()) {
                    throw new UrmumException("Please provide a keyword to search for.");
                }
                TaskList matches = tasks.findTasks(keyword);
                if (matches.size() == 0) {
                    return "No matching tasks found.";
                } else {
                    StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
                    for (int i = 0; i < matches.size(); i++) {
                        sb.append(" ").append(i + 1).append(".").append(matches.getTask(i)).append("\n");
                    }
                    return sb.toString();
                }
            }
            default:
                throw new UrmumException("Sorry, I don't know what that means. Try another command!");
        }
    }

    private String getTaskListString() {
        if (tasks.size() == 0) {
            return "Your task list is empty!";
        }
        StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(" ").append(i + 1).append(".").append(tasks.getTask(i)).append("\n");
        }
        return sb.toString();
    }
}

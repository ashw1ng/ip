package storage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.Todo;

/**
 * Handles loading and saving tasks to and from the hard disk.
 */
public class Storage {
    private final String dataDir;
    private final String dataFile;

    /**
     * Constructs a Storage object with the specified directory and file.
     * @param dataDir The directory where the data file is stored.
     * @param dataFile The path to the data file.
     */
    public Storage(String dataDir, String dataFile) {
        this.dataDir = dataDir;
        this.dataFile = dataFile;
    }

    /**
     * Saves the list of tasks to the data file.
     * @param tasks The list of tasks to save.
     */
    public void saveTasks(ArrayList<Task> tasks) {
        try {
            Path dirPath = Paths.get(dataDir);
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(dataFile));
            for (Task t : tasks) {
                writer.write(taskToFileString(t));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    /**
     * Loads tasks from the data file into the provided list.
     * @param tasks The list to load tasks into.
     */
    public void loadTasks(ArrayList<Task> tasks) {
        Path filePath = Paths.get(dataFile);
        if (!Files.exists(filePath)) {
            return; // No file to load
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(dataFile));
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    Task t = parseTask(line);
                    if (t != null) {
                        tasks.add(t);
                    }
                } catch (Exception e) {
                    System.out.println("Warning: Skipping corrupted line: " + line);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
    }

    /**
     * Converts a Task object to a string suitable for saving to file.
     * @param t The task to convert.
     * @return The string representation of the task for file storage.
     */
    private static String taskToFileString(Task t) {
        String type = t instanceof Todo ? "T" : t instanceof Deadline ? "D" : t instanceof Event ? "E" : "?";
        String done = t.isDone() ? "1" : "0";
        if (t instanceof Todo) {
            return String.join(" | ", type, done, t.getDescription());
        } else if (t instanceof Deadline) {
            return String.join(" | ", type, done, t.getDescription(), ((Deadline) t).getBy().toString());
        } else if (t instanceof Event) {
            return String.join(" | ", type, done, t.getDescription(), ((Event) t).getFrom() + " to " + ((Event) t).getTo());
        }
        return "";
    }

    /**
     * Parses a line from the data file into a Task object.
     * @param line The line to parse.
     * @return The Task object, or null if the line is invalid.
     */
    private static Task parseTask(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) return null;
        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String desc = parts[2];
        switch (type) {
            case "T":
                Task t = new Todo(desc);
                if (isDone) t.markAsDone();
                return t;
            case "D":
                if (parts.length < 4) return null;
                Task d = new Deadline(desc, parts[3]);
                if (isDone) d.markAsDone();
                return d;
            case "E":
                if (parts.length < 4) return null;
                String[] fromTo = parts[3].split(" to ", 2);
                String from = fromTo.length > 0 ? fromTo[0] : "";
                String to = fromTo.length > 1 ? fromTo[1] : "";
                Task e = new Event(desc, from, to);
                if (isDone) e.markAsDone();
                return e;
            default:
                return null;
        }
    }
}

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


public class Storage {
    private final String dataDir;
    private final String dataFile;

    public Storage(String dataDir, String dataFile) {
        this.dataDir = dataDir;
        this.dataFile = dataFile;
    }

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

    private static String taskToFileString(Task t) {
        String type = t instanceof Todo ? "T" : t instanceof Deadline ? "D" : t instanceof Event ? "E" : "?";
        String done = t.isDone ? "1" : "0";
        if (t instanceof Todo) {
            return String.join(" | ", type, done, t.description);
        } else if (t instanceof Deadline) {
            return String.join(" | ", type, done, t.description, ((Deadline) t).by);
        } else if (t instanceof Event) {
            return String.join(" | ", type, done, t.description, ((Event) t).from + " to " + ((Event) t).to);
        }
        return "";
    }

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

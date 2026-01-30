import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class UrMum {
    private static final String DATA_DIR = "data";
    private static final String DATA_FILE = "data/urMum.txt";
    
    public static void main(String[] args) {
        String welcome = " Hello! I'm UrMum\n What can I do for you?";
        String goodbye = " Bye. Hope to see you again soon!";

        System.out.println(welcome);

        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();
        loadTasks(tasks);

        while (true) {
            String input = scanner.nextLine();
            try {
                if (input.equals("bye")) {
                    System.out.println(goodbye);
                    break;
                } else if (input.equals("list")) {
                    System.out.println(" Here are the tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println(" " + (i + 1) + "." + tasks.get(i));
                    }
                } else if (input.startsWith("mark ")) {
                    int idx = Integer.parseInt(input.substring(5)) - 1;
                    if (idx >= 0 && idx < tasks.size()) {
                        tasks.get(idx).markAsDone();
                        System.out.println(" Nice! I've marked this task as done:");
                        System.out.println("   " + tasks.get(idx));
                        saveTasks(tasks);
                    } else {
                        throw new DukeException("That task number doesn't exist. Please try again.");
                    }
                } else if (input.startsWith("unmark ")) {
                    int idx = Integer.parseInt(input.substring(7)) - 1;
                    if (idx >= 0 && idx < tasks.size()) {
                        tasks.get(idx).markAsNotDone();
                        System.out.println(" OK, I've marked this task as not done yet:");
                        System.out.println("   " + tasks.get(idx));
                        saveTasks(tasks);
                    } else {
                        throw new DukeException("That task number doesn't exist. Please try again.");
                    }
                } else if (input.startsWith("delete ")) {
                    int idx = Integer.parseInt(input.substring(7).trim()) - 1;
                    if (idx >= 0 && idx < tasks.size()) {
                        Task removed = tasks.remove(idx);
                        System.out.println(" Noted. I've removed this task:");
                        System.out.println("   " + removed);
                        System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
                        saveTasks(tasks);
                    } else {
                        throw new DukeException("That task number doesn't exist. Please try again.");
                    }
                } else if (input.startsWith("todo ")) {
                    String desc = input.length() > 4 ? input.substring(5).trim() : "";
                    if (desc.isEmpty()) {
                        throw new DukeException("Oops! You need to provide a description for a todo.");
                    }
                    tasks.add(new Todo(desc));
                    System.out.println(" Got it. I've added this task:");
                    System.out.println("   " + tasks.get(tasks.size() - 1));
                    System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
                    saveTasks(tasks);
                } else if (input.startsWith("deadline ")) {
                    String[] parts = input.substring(9).split(" /by ", 2);
                    String desc = parts[0].trim();
                    String by = parts.length > 1 ? parts[1].trim() : "";
                    if (desc.isEmpty()) {
                        throw new DukeException("Please provide a description for the deadline.");
                    }
                    if (by.isEmpty()) {
                        throw new DukeException("Please specify a /by time for the deadline.");
                    }
                    tasks.add(new Deadline(desc, by));
                    System.out.println(" Got it. I've added this task:");
                    System.out.println("   " + tasks.get(tasks.size() - 1));
                    System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
                    saveTasks(tasks);
                } else if (input.startsWith("event ")) {
                    String[] parts = input.substring(5).split(" /from ", 2);
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
                        throw new DukeException("Please provide a description for the event.");
                    }
                    if (from.isEmpty() || to.isEmpty()) {
                        throw new DukeException("Please specify both /from and /to times for the event.");
                    }
                    tasks.add(new Event(desc, from, to));
                    System.out.println(" Got it. I've added this task:");
                    System.out.println("   " + tasks.get(tasks.size() - 1));
                    System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
                    saveTasks(tasks);
                } else {
                    throw new DukeException("Sorry, I don't know what that means. Try another command!");
                }
            } catch (DukeException e) {
                System.out.println(e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid task number.");
            }
        }
        scanner.close();        
    }

    private static void saveTasks(ArrayList<Task> tasks) {
        try {
            Path dirPath = Paths.get(DATA_DIR);
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE));
            for (Task t : tasks) {
                writer.write(taskToFileString(t));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    private static void loadTasks(ArrayList<Task> tasks) {
        Path filePath = Paths.get(DATA_FILE);
        if (!Files.exists(filePath)) {
            return; // No file to load
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE));
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

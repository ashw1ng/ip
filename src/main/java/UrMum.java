import java.util.Scanner;
import java.util.ArrayList;

public class UrMum {
    private static final String DATA_DIR = "data";
    private static final String DATA_FILE = "data/urMum.txt";
    
    public static void main(String[] args) {
        String welcome = " Hello! I'm UrMum\n What can I do for you?";
        String goodbye = " Bye. Hope to see you again soon!";

        System.out.println(welcome);

        Scanner scanner = new Scanner(System.in);
        TaskList tasks = new TaskList();
        Storage storage = new Storage(DATA_DIR, DATA_FILE);
        storage.loadTasks(tasks.getTasks());

        while (true) {
            String input = scanner.nextLine();
            try {
                if (input.equals("bye")) {
                    System.out.println(goodbye);
                    break;
                } else if (input.equals("list")) {
                    System.out.println(" Here are the tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println(" " + (i + 1) + "." + tasks.getTask(i));
                    }
                } else if (input.startsWith("mark ")) {
                    int idx = Integer.parseInt(input.substring(5)) - 1;
                    if (idx >= 0 && idx < tasks.size()) {
                        tasks.getTask(idx).markAsDone();
                        System.out.println(" Nice! I've marked this task as done:");
                        System.out.println("   " + tasks.getTask(idx));
                        storage.saveTasks(tasks.getTasks());
                    } else {
                        throw new UrmumException("That task number doesn't exist. Please try again.");
                    }
                } else if (input.startsWith("unmark ")) {
                    int idx = Integer.parseInt(input.substring(7)) - 1;
                    if (idx >= 0 && idx < tasks.size()) {
                        tasks.getTask(idx).markAsNotDone();
                        System.out.println(" OK, I've marked this task as not done yet:");
                        System.out.println("   " + tasks.getTask(idx));
                        storage.saveTasks(tasks.getTasks());
                    } else {
                        throw new UrmumException("That task number doesn't exist. Please try again.");
                    }
                } else if (input.startsWith("delete ")) {
                    int idx = Integer.parseInt(input.substring(7).trim()) - 1;
                    if (idx >= 0 && idx < tasks.size()) {
                        Task removed = tasks.getTask(idx);
                        tasks.removeTask(idx);
                        System.out.println(" Noted. I've removed this task:");
                        System.out.println("   " + removed);
                        System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
                        storage.saveTasks(tasks.getTasks());
                    } else {
                        throw new UrmumException("That task number doesn't exist. Please try again.");
                    }
                } else if (input.startsWith("todo ")) {
                    String desc = input.length() > 4 ? input.substring(5).trim() : "";
                    if (desc.isEmpty()) {
                        throw new UrmumException("Oops! You need to provide a description for a todo.");
                    }
                    tasks.addTask(new Todo(desc));
                    System.out.println(" Got it. I've added this task:");
                    System.out.println("   " + tasks.getTask(tasks.size() - 1));
                    System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
                    storage.saveTasks(tasks.getTasks());
                } else if (input.startsWith("deadline ")) {
                    String[] parts = input.substring(9).split(" /by ", 2);
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
                        System.out.println(" Got it. I've added this task:");
                        System.out.println("   " + tasks.getTask(tasks.size() - 1));
                        System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
                        storage.saveTasks(tasks.getTasks());
                    } catch (Exception e) {
                        throw new UrmumException("Please enter the date and time in yyyy-MM-dd HHmm format, e.g., 2019-12-02 1800");
                    }
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
                        throw new UrmumException("Please provide a description for the event.");
                    }
                    if (from.isEmpty() || to.isEmpty()) {
                        throw new UrmumException("Please specify both /from and /to times for the event.");
                    }
                    tasks.addTask(new Event(desc, from, to));
                    System.out.println(" Got it. I've added this task:");
                    System.out.println("   " + tasks.getTask(tasks.size() - 1));
                    System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
                    storage.saveTasks(tasks.getTasks());
                } else {
                    throw new UrmumException("Sorry, I don't know what that means. Try another command!");
                }
            } catch (UrmumException e) {
                System.out.println(e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid task number.");
            }
        }
        scanner.close();
    }
}

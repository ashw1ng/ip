import java.util.Scanner;

public class UrMum {
    public static void main(String[] args) {
        String welcome = " Hello! I'm UrMum\n What can I do for you?";
        String goodbye = " Bye. Hope to see you again soon!";

        System.out.println(welcome);

        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int taskCount = 0;

        while (true) {
            String input = scanner.nextLine();
            if (input.equals("bye")) {
                System.out.println(goodbye);
                break;
            } else if (input.equals("list")) {
                System.out.println(" Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println(" " + (i + 1) + "." + tasks[i]);
                }
            } else if (input.startsWith("mark ")) {
                try {
                    int idx = Integer.parseInt(input.substring(5)) - 1;
                    if (idx >= 0 && idx < taskCount) {
                        tasks[idx].markAsDone();
                        System.out.println(" Nice! I've marked this task as done:");
                        System.out.println("   " + tasks[idx]);
                    } else {
                        System.out.println(" Invalid task number.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println(" Please enter a valid task number.");
                }
            } else if (input.startsWith("unmark ")) {
                try {
                    int idx = Integer.parseInt(input.substring(7)) - 1;
                    if (idx >= 0 && idx < taskCount) {
                        tasks[idx].markAsNotDone();
                        System.out.println(" OK, I've marked this task as not done yet:");
                        System.out.println("   " + tasks[idx]);
                    } else {
                        System.out.println(" Invalid task number.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println(" Please enter a valid task number.");
                }
            } else if (input.startsWith("todo ")) {
                String desc = input.substring(5).trim();
                tasks[taskCount] = new Todo(desc);
                taskCount++;
                System.out.println(" Got it. I've added this task:");
                System.out.println("   " + tasks[taskCount - 1]);
                System.out.println(" Now you have " + taskCount + " tasks in the list.");
            } else if (input.startsWith("deadline ")) {
                String[] parts = input.substring(9).split(" /by ", 2);
                String desc = parts[0].trim();
                String by = parts.length > 1 ? parts[1].trim() : "";
                tasks[taskCount] = new Deadline(desc, by);
                taskCount++;
                System.out.println(" Got it. I've added this task:");
                System.out.println("   " + tasks[taskCount - 1]);
                System.out.println(" Now you have " + taskCount + " tasks in the list.");
            } else if (input.startsWith("event ")) {
                String[] parts = input.substring(6).split(" /from | /to ", 3);
                String desc = parts[0].trim();
                String from = parts.length > 1 ? parts[1].trim() : "";
                String to = parts.length > 2 ? parts[2].trim() : "";
                tasks[taskCount] = new Event(desc, from, to);
                taskCount++;
                System.out.println(" Got it. I've added this task:");
                System.out.println("   " + tasks[taskCount - 1]);
                System.out.println(" Now you have " + taskCount + " tasks in the list.");
            } else {
                System.out.println("added: " + input);
            }
        }
        scanner.close();        
    }
}

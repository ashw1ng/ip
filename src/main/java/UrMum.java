import java.util.Scanner;

public class UrMum {
    public static void main(String[] args) {
        //String line = "____________________________________________________________";
        String welcome = " Hello! I'm UrMum\n What can I do for you?";
        String goodbye = " Bye. Hope to see you again soon!";

        System.out.println(welcome);

        Scanner scanner = new Scanner(System.in);
        String[] messages = new String[100];
        int msgCount = 0;

        while (true) {
            String input = scanner.nextLine();
            if (input.equals("bye")) {
                System.out.println(goodbye);
                break;
            } else if (input.equals("list")) {
                for (int i = 0; i < msgCount; i++) {
                    System.out.println((i + 1) + ". " + messages[i]);
                }
            } else {
                messages[msgCount] = input;
                msgCount++;
                System.out.println("added: " + input);
            }
        }
        scanner.close();        
    }
}

import java.util.Scanner;

public class UrMum {
    public static void main(String[] args) {
        //String line = "____________________________________________________________";
        String welcome = " Hello! I'm UrMum\n What can I do for you?";
        String goodbye = " Bye. Hope to see you again soon!";

        System.out.println(welcome);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("bye")) {
                System.out.println(goodbye);
                break;
            } else {
                System.out.println(" " + input);
            }
        }
        scanner.close();        
    }
}

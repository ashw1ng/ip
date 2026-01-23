public class UrMum {
    public static void main(String[] args) {
        String line = "____________________________________________________________";
        String greeting = " Hello! I'm UrMum\n What can I do for you?";
        String farewell = " Bye. Hope to see you again soon!";

        System.out.println(line);
        System.out.println(greeting);
        System.out.println(line);
        System.out.println(farewell);
        System.out.println(line);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("bye")) {
                System.out.println(line);
                System.out.println(farewell);
                System.out.println(line);
                break;
            } else {
                System.out.println(line);
                System.out.println(" " + input);
                System.out.println(line);
            }
        }
        scanner.close();        
    }
}

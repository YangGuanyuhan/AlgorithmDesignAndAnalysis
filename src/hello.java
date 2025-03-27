import java.util.Scanner;

public class hello {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
       // String command = in.nextLine();
         String command="echo   > abc";
        String[] commands = command.split(" ");
        //print commands
        System.out.println(commands.length);
        for (String s : commands) {
            System.out.println(s);
        }
    }
}
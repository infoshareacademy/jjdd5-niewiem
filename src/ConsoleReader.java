import java.util.Scanner;

public class ConsoleReader {
    private Scanner sc;

    public ConsoleReader() {
        this.sc = new Scanner(System.in);
    }

    public int readInt(){
        // check if input is actually int
        int input = sc.nextInt();
        sc.nextLine();
        return input;
    }

    public String readString(){
        return sc.nextLine();
    }
}

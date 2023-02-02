import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        //creating arraylist for matrix
        ArrayList<Symbol[]> listOfLists = new ArrayList<>();

        Game Run = new Game();

        FileReader file = new FileReader(args[0]);
        Scanner scanGrid = new Scanner(file);

        FileReader file2 = new FileReader(args[1]);
        Scanner scanCommand = new Scanner(file2);

        FileReader file3 = new FileReader("leaderboard.txt");
        Scanner scanPlayer = new Scanner(file3);

        Run.run(scanPlayer,scanCommand,scanGrid, listOfLists);// call run method and start the game

    }
}

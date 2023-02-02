

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    static String output = "";
    static String description = "";
    public static void main(String[] args) throws IOException {

        properties[] board = new properties[41];
        board[0] = null;
        int position;

        FileWriter writer = new FileWriter("monitoring.txt");
        PropertyJsonReader reader = new PropertyJsonReader(board);
        FileReader file = new FileReader(args[0]);
        Scanner commandFile = new Scanner(file);

        //create 2 players
        Players player1 = new Players("Player 1");
        Players player2 = new Players("Player 2");
        Players player = null;

        Process doProcess = new Process();
        while (commandFile.hasNext() && player1.getMoney()>0 && player2.getMoney()>0 && player1.isBankrupt() && player2.isBankrupt()) {

            String line = commandFile.nextLine();

            if (line.equals("show()")) {
               doProcess.show(player1,player2);
            }

            else {
                String[] lineArray = line.split(";");
                if (lineArray[0].equals("Player 2") ) player = player2;
                else if (lineArray[0].equals("Player 1") ) player = player1;

                if (!player.jail()) {
                    output+=player.getName()+"\t"+lineArray[1]+"\t"+"11"+"\t"+player1.getMoney()+"\t"+player2.getMoney()+"\t"+player.getName()+"\t"+"in jail (count="+ player.getCount()+")\n";
                    player.setCount(player.getCount()+1);
                    player.jail();
                    continue;
                }

                player.findPosition(Integer.parseInt(lineArray[1]));
                position = player.getPosition();

                doProcess.doSomething(position,player,player1,player2,board,Integer.parseInt(lineArray[1]));
            }
        }
        doProcess.show(player1,player2);
        writer.write(output);
        writer.close();
    }
}

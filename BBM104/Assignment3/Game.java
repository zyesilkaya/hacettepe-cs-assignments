import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Game {
    // in this class, I control the game by using run and rank methods

    public void run(Scanner scanPlayer, Scanner scanCommand, Scanner scGrid, ArrayList<Symbol[]> matrix) throws IOException {

        FileWriter writer = new FileWriter("monitoring.txt");
        MatrixControl matrixControl = new MatrixControl();

        //read gameGrid and create objects for every Symbol in grid
        while (scGrid.hasNext()){

            String[] lineArray = (scGrid.nextLine().replace("\n"," ").split(" "));
            Symbol[] jewelArray = new Symbol[lineArray.length];
            for (int i = 0 ; i < lineArray.length ; i++) {
                switch (lineArray[i]) {
                    case "T":
                        jewelArray[i] = new SymbolT(15,"T");
                        break;
                    case "D":
                        jewelArray[i] = new SymbolD(30, "D");
                        break;
                    case "S":
                        jewelArray[i] = new SymbolS(15, "S");
                        break;
                    case "W":
                        jewelArray[i] = new SymbolW(10, "W");
                        break;
                    case "/":
                        jewelArray[i] = new SlashSymbol(20, "/");
                        break;
                    case "-":
                        jewelArray[i] = new MinusSymbol(20, "-");
                        break;
                    case "+":
                        jewelArray[i] = new PlusSymbol(20, "+");
                        break;
                    case "\\":
                        jewelArray[i] = new BackslashSymbol(20, "\\");
                        break;
                    case "|":
                        jewelArray[i] = new VerticalSymbol(20, "|");
                        break;
                }
            }
            matrix.add(jewelArray);
        }
        writer.write("Game Grid:\n\n");
        matrixControl.viewMatrix(matrix,writer);

        //read command file and call search method for jewels if command is E end the game
        while (scanCommand.hasNext()){
            try{
                String[] line = scanCommand.nextLine().split(" ");
                int row = Integer.parseInt(line[0]);
                int column = Integer.parseInt(line[1]);
                writer.write("Select coordinate or enter E to end the game: ");
                writer.write(row +" "+column+"\n\n");
                matrix.get(row)[column].search(row,column,matrix,0);//call search method
                if (scanCommand.hasNext()){
                    // this is for printing matrix and score after every row and column input
                    matrixControl.gravity(matrix,matrix.size());
                    matrixControl.viewMatrix(matrix,writer);
                    writer.write("Score: "+Symbol.tempscore+" points\n");
                    Symbol.tempscore = 0;
                }
            }
            // if command gives an error in parsing, continues from here, ends the game and calls 'rank' method to find players rank
            catch (NumberFormatException e){
                writer.write("Select coordinate or enter E to end the game: E\n\n");
                rank(scanPlayer,scanCommand.nextLine(),writer);
            }

            catch (NullPointerException | IndexOutOfBoundsException a){
                writer.write("Please enter a valid coordinate\n");
            }

            writer.write("\n");
        }
        writer.write("Good Bye!");
        writer.close();
    }

    public void rank(Scanner scanPlayer,  String name, FileWriter writer) throws IOException {
        // this method calculates players rank by using binarySearch method and print his/her score,name and rank

        FileWriter writerLeaderboard = new FileWriter("leaderboard.txt",true);

        ArrayList<Player> playerList = new ArrayList<>();
        while (scanPlayer.hasNext()){
            String[] array = (scanPlayer.nextLine().replace("\n"," ").split(" "));
            playerList.add(new Player(Integer.parseInt(array[1]),array[0]));
        }
        Player player1 = new Player(Symbol.score,name);
        playerList.add(player1);
        Collections.sort(playerList);
        int rank = Collections.binarySearch(playerList,player1);
        writer.write("Total Score: "+Symbol.score+" points\n\n");
        writer.write("Enter Name: "+name+"\n\n");
        writer.write("Your rank is "+(rank+1)+"/"+playerList.size());

        if(rank==0) writer.write(",your score is "+(player1.getScore()-playerList.get(1).getScore())+ " points higher than "+playerList.get(1).getName()+"\n");
        else if(rank==(playerList.size())) System.out.println(",your score is "+(player1.getScore()-playerList.get(playerList.size()-1).getScore())+ " point lower than "+playerList.get(playerList.size()-1).getName());
        else {
            writer.write(",your score is "+(playerList.get(rank-1).getScore()-player1.getScore())+ " points lower than "+playerList.get(rank-1).getName());
            writer.write(" and "+(player1.getScore()-playerList.get(rank+1).getScore())+ " points higher than "+playerList.get(rank+1).getName()+"\n");
        }
        writerLeaderboard.write("\n"+ player1.getName() +" "+ player1.getScore());
        writerLeaderboard.close();
    }
}

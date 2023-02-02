import java.util.*;

public abstract class Symbol {
    //this class contains necessary methods and fields for every jewel for example search and move methods

    private int jewelScore;
    private String jewelName;

    public static int tempscore=0;
    public static int score;
    public boolean match = false;
    ArrayList<String> indexList = new ArrayList<>();

    public int getJewelScore() {
        return jewelScore;
    }

    public String getJewelName() {
        return jewelName;
    }

    public Symbol(int jewelScore, String jewelName) {
        this.jewelScore = jewelScore;
        this.jewelName = jewelName;
    }

    public abstract void search(int row, int column, ArrayList<Symbol[]> list, int count);

    public void move(int row, int column, ArrayList<Symbol[]> list,int count, int dx, int dy){
        //this method controls matches. I use recursion and with the help of count number it controls 3 by 3

        try{
            if (!match && list.get(row+dy)[column+dx].getJewelName().equals(list.get(row)[column].getJewelName()) && count<2){
                indexList.add(row + " " + column);
                indexList.add((row+dy) + " " + (column+dx));
                count++;
                move(row+dy,column+dx,list,count,dx,dy);

            }
            if(count == 2){

                this.match=true;
                indexList.remove(1);
                for(String s: indexList) {
                    int row1 = Integer.parseInt(s.split(" ")[0]);
                    int column1 = Integer.parseInt(s.split(" ")[1]);
                    tempscore +=  list.get(row1)[column1].getJewelScore();
                    list.get(row1)[column1] = null;
                }
                score+=tempscore;
            }
            indexList.clear();
        }
        catch (IndexOutOfBoundsException e){
        }
    }
}

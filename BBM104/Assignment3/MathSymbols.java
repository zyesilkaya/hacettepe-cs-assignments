import java.util.ArrayList;

public abstract class MathSymbols extends Symbol{


    public MathSymbols(int jewelScore, String jewelName) {
        super(jewelScore, jewelName);
    }

    @Override
    public void move(int row, int column, ArrayList<Symbol[]> list, int count, int dx, int dy) {
        // in math jewels, every math jewel can match any other math jewel so i override this method
        try{
            if (!match && (list.get(row+dy)[column+dx] instanceof MathSymbols) && count<2){
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

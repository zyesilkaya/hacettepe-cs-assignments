import java.util.ArrayList;


public class SymbolW extends Character{

    public SymbolW(int jewelScore, String jewelName) {
        super(jewelScore, jewelName);
    }

    @Override
    public void search(int row, int column, ArrayList<Symbol[]> list, int count) {
        //vertical
        move(row, column, list, count,0,-1);
        move(row, column, list, count,0,1);
        //horizontal
        move(row, column, list, count,-1,0);
        move(row, column, list, count,1,0);
        //left diagonal
        move(row, column, list, count,-1,-1);
        move(row, column, list, count,1,1);
        //right diagonal
        move(row, column, list, count,1,-1);
        move(row, column, list, count,-1,+1);
        match = false;

    }

    @Override
    public void move(int row, int column, ArrayList<Symbol[]> list, int count, int dx, int dy) {

        try{
            //TODO WmathW olursa patlamasın
            if (!match && ( list.get(row+2*dy)[column+2*dx].getJewelName().equals("W") || list.get(row+dy)[column+dx].getJewelName().equals("W")
                    && (list.get(row+dy)[column+dx] instanceof Character) && (list.get(row+2*dy)[column+2*dx] instanceof Character))){
                this.match=true;
                tempscore += list.get(row)[column].getJewelScore();
                tempscore += list.get(row+dy)[column+dx].getJewelScore();
                tempscore += list.get(row+2*dy)[column+2*dx].getJewelScore();
                list.get(row+dy)[column+dx]=null;
                list.get(row)[column]=null;
                list.get(row+2*dy)[column+2*dx]=null;
                score+=tempscore;

            }
            //TODO Wmathmath olursa da patlamasın
            else if(!match && list.get(row+dy)[column+dx].getJewelName().equals(list.get(row+2*dy)[column+2*dx].getJewelName())
                    && (list.get(row+dy)[column+dx] instanceof Character) && (list.get(row+2*dy)[column+2*dx] instanceof Character)){
                this.match=true;
                tempscore += list.get(row)[column].getJewelScore();
                tempscore += list.get(row+dy)[column+dx].getJewelScore();
                tempscore += list.get(row+2*dy)[column+2*dx].getJewelScore();
                list.get(row+dy)[column+dx]=null;
                list.get(row)[column]=null;
                list.get(row+2*dy)[column+2*dx]=null;
                score+=tempscore;
            }
        }
        catch (IndexOutOfBoundsException | NullPointerException e ){
        }
    }
}

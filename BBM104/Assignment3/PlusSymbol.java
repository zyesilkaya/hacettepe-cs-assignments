import java.util.ArrayList;

public class PlusSymbol extends MathSymbols {

    public PlusSymbol(int jewelScore, String jewelName) {
        super(jewelScore, jewelName);
    }

    @Override
    public void search(int row, int column,ArrayList<Symbol[]> list, int count) {
        //search for horizontal
        super.move(row, column, list, count,-1,0);
        super.move(row, column, list, count,1,0);
        //search for vertical
        super.move(row, column, list, count,0,-1);
        super.move(row, column, list, count,0,1);
        super.match = false;
    }
}

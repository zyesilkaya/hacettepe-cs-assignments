import java.util.ArrayList;

public class MinusSymbol extends MathSymbols {

    public MinusSymbol(int jewelScore, String jewelName) {
        super(jewelScore, jewelName);
    }

    @Override
    public void search(int row, int column, ArrayList<Symbol[]> list, int count) {
        //search for horizontal direction
        super.move(row, column, list, count,-1,0);
        super.move(row, column, list, count,1,0);
        super.match = false;
    }
}

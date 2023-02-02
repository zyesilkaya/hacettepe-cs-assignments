import java.util.ArrayList;

public class VerticalSymbol extends MathSymbols {

    public VerticalSymbol(int jewelScore, String jewelName) {
        super(jewelScore, jewelName);
    }

    @Override
    public void search(int row, int column, ArrayList<Symbol[]> list, int count ) {
        // search vertical direction
        super.move(row, column, list, count,0,-1);
        super.move(row, column,list, count,0,1);
        super.match = false;
    }
}

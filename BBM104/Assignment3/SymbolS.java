import java.util.ArrayList;
import java.util.Set;

public class SymbolS extends Character{

    public SymbolS(int jewelScore, String jewelName) {
        super(jewelScore, jewelName);
    }

    @Override
    public void search(int row, int column, ArrayList<Symbol[]> list, int count ) {
        //search for horizontal
        super.move(row, column,list, count,-1,0);
        super.move(row, column, list, count,1,0);
        super.match = false;
    }
}

import java.util.ArrayList;
import java.util.Set;

public class SymbolT extends Character{

    public SymbolT(int jewelScore, String jewelName) {
        super(jewelScore, jewelName);
    }

    @Override
    public void search(int row, int column, ArrayList<Symbol[]> list, int count) {
        //search for vertical
        super.move(row, column, list, count,0,-1);
        super.move(row, column, list, count,0,1);
        super.match = false;
    }
}

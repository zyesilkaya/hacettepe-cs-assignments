import java.util.ArrayList;
public class SymbolD extends Character{

    public SymbolD(int jewelScore, String jewelName) {
        super(jewelScore, jewelName);
    }

    @Override
    public void search(int row, int column, ArrayList<Symbol[]> list, int count) {
        //search for left diagonal
        super.move(row, column, list, count,-1,-1);
        super.move(row, column, list, count,1,1);
        //search for right diagonal
        super.move(row, column, list, count,1,-1);
        super.move(row, column, list, count,-1,1);
        super.match = false;
    }
}

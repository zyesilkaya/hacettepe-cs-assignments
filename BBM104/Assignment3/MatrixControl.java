import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class MatrixControl {

    public void gravity(ArrayList<Symbol[]> list, int size){
        for(Symbol[] row: list){
            int a = 0;
            for(Symbol i: row){
                if(a<size){
                    int b = list.indexOf(row);
                    if(b+1<list.size() && list.get(b+1)[a]==null && !(list.get(b)[a]==null)){
                        list.get(b+1)[a]=list.get(b)[a];
                        list.get(b)[a]=null;
                        gravity(list, size);
                    }
                }
                a++;
            }
        }
    }

    public void viewMatrix(ArrayList<Symbol[]> listOfLists, FileWriter writer) throws IOException {
        for(Symbol[] array: listOfLists){
            for(Symbol i:array){
                if(i == null ){
                    writer.write("  ");
                }
                else {
                    writer.write(i.getJewelName()+" ");
                }
            }
            writer.write("\n");}
        writer.write("\n");
    }
}

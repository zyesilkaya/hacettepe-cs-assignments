import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Kingdom {

    public ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
    public ArrayList<ArrayList<Integer>> undirected_adj;
    public static int size;

    // TODO: You should add appropriate instance variables.
    public void initializeKingdom(String filename) {
        // Read the txt file and fill your instance variables
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String inputString = "";
        while (scanner.hasNextLine()) {
            inputString += scanner.nextLine() + "\n";
        }

        String[] rows = inputString.split("\n");

        int numRows = rows.length;
        int numCols = rows[0].split(" ").length;

        size = numRows+1;

        undirected_adj = new ArrayList<>(numRows);

        for(int i = 0; i < numRows+1; i++){
            adj.add(new ArrayList<>());
            undirected_adj.add(new ArrayList<>());
        }

        for (int i = 1; i < numRows+1; i++) {
            String[] rowValues = rows[i-1].split(" ");

            for (int j = 1; j < numCols+1; j++) {
                if(Integer.parseInt(rowValues[j-1]) == 1){
                    adj.get(i).add(j);
                    if (i != j) { // add both directions for undirected graph
                        undirected_adj.get(j).add(i);//bura aslında hatalı da
                        undirected_adj.get(i).add(j);
                    }
                }
            }
        }

        // TODO: Your code here
    }

    public List<Colony> getColonies() {
        List<Colony> colonies = new ArrayList<>();
        // TODO: DON'T READ THE .TXT FILE HERE!
        // Identify the colonies using the given input file.

        marked = new boolean[undirected_adj.size()];

        for (int i=1;i<adj.size();i++)
        {
            if (!marked[i])
            {
                colony = new Colony();
                colony.cities.add(i);
                colony.roadNetwork.put(i ,(adj.get(i)));
                colonies.add(colony);
                dfs(i);

            }
        }
        return colonies;
    }

    private boolean marked[];
    private Colony colony;

    private void dfs(int v)
    {
        marked[v] = true;
        for (int w : undirected_adj.get(v))
            if (!marked[w]){
                colony.cities.add(w );
                colony.roadNetwork.put(w ,adj.get(w));
                dfs(w);
            }
    }

    ArrayList<Integer> addOne(ArrayList<Integer> arr){
        ArrayList<Integer> newArr = new ArrayList<>();
        for(int i=0;i<arr.size();i++) newArr.add(arr.get(i) + 1);
        return newArr;
    }

    public void printColonies(List<Colony> discoveredColonies) {
        // Print the given list of discovered colonies conforming to the given output format.
        System.out.println("Discovered colonies are: ");
        int num=1;
        for(Colony colony : discoveredColonies){
            Collections.sort(colony.cities);
            System.out.println("Colony "+ num +": "+Arrays.toString(colony.cities.toArray()));;
            num++;
        }
        // TODO: Your code here
    }
}

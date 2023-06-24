import java.util.*;

public class TrapLocator {

    private boolean[] marked;
    private int[] edgeTo;
    private Stack<Integer> cycle; // vertices on a cycle (if one exists)
    private boolean[] onStack; // vertices on recursive call stack
    private Colony c;

    public List<Colony> colonies;

    public TrapLocator(List<Colony> colonies) {
        this.colonies = colonies;
    }

    public List<List<Integer>> revealTraps() {

        List<List<Integer>> traps = new ArrayList<>();

        for(Colony colony : colonies){
            c = colony;

            onStack = new boolean[Kingdom.size + 1];
            edgeTo = new int[Kingdom.size + 1];
            marked = new boolean[Kingdom.size + 1];
            cycle = new Stack<Integer>();
            for (int i = 0; i < colony.cities.size(); i++){
                // olmz bu
                int v = colony.cities.get(i);
                if (!marked[v]) dfs(v);
            }

            ArrayList<Integer> arr = new ArrayList<>();
            while(cycle.size() > 1){
                arr.add(cycle.pop());
            }
            traps.add(arr);
            cycle.clear();
        }
        // Identify the time traps and save them into the traps variable and then return it.
        return traps;
    }

    private void dfs(int v)
    {
        onStack[v] = true;
        marked[v] = true;
        if (c.roadNetwork.get(v) == null) return;
        for (int w : c.roadNetwork.get(v))
            if (this.hasCycle()){
                return;
            }
            else if (!marked[w])
            { edgeTo[w] = v; dfs(w); }
            else if (onStack[w])
            {

                for (int x = v; x != w; x = edgeTo[x])
                    cycle.push(x);
                cycle.push(w);
                cycle.push(v);
            }
        onStack[v] = false;
    }
    public boolean hasCycle()
    { return !cycle.isEmpty(); }

    public void printTraps(List<List<Integer>> traps) {

        System.out.println("Danger exploration conclusions:");

        int num=1;
        for(List<Integer> arr : traps){
            if(arr.isEmpty()) System.out.println("Colony "+num+": Safe");
            else{
                Collections.sort(arr);
                System.out.println("Colony "+num+": Dangerous. Cities on the dangerous path: "+Arrays.toString(arr.toArray()));
            }

            num++;
        }
    }
}

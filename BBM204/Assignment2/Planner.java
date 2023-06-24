
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;

public class Planner {

    public final Task[] taskArray;
    public final Integer[] compatibility;
    public final Double[] maxWeight;
    public final ArrayList<Task> planDynamic;
    public final ArrayList<Task> planGreedy;

    public Planner(Task[] taskArray) {

        // Should be instantiated with an Task array
        // All the properties of this class should be initialized here

        this.taskArray = taskArray;
        this.compatibility = new Integer[taskArray.length];
        maxWeight = new Double[taskArray.length];

        this.planDynamic = new ArrayList<>();
        this.planGreedy = new ArrayList<>();

        calculateCompatibility();

        System.out.println("Calculating max array\n---------------------");
        calculateMaxWeight(taskArray.length-1);
        System.out.println();

    }

    /**
     * @param index of the {@link Task}
     * @return Returns the index of the last compatible {@link Task},
     * returns -1 if there are no compatible {@link Task}s.
     */
    public int binarySearch(int index) {
        // YOUR CODE HERE

        int l=0;

        int r=index-1;

        while (l<=r){

            int m = (r+l)/2;

            if(compareTimes(taskArray[m].getFinishTime(),taskArray[index].getStartTime())<= 0){
                l=m+1;
            }

            else r=m-1;
        }
        return l-1;
    }


    /**
     * {@link #compatibility} must be filled after calling this method
     */
    public void calculateCompatibility() {
        // YOUR CODE HERE
        //bu yanlış
        for(int i=0;i<taskArray.length;i++){
            compatibility[i] = binarySearch(i);
        }

    }


    /**
     * Uses {@link #taskArray} property
     * This function is for generating a plan using the dynamic programming approach.
     * @return Returns a list of planned tasks.
     */
    public ArrayList<Task> planDynamic() {

        System.out.println("Calculating the dynamic solution\n--------------------------------");

        solveDynamic(taskArray.length-1);

        System.out.println("\nDynamic Schedule\n----------------");

        for (Task task:planDynamic) System.out.println("At "+ task.getStartTime() + ", "+task.getName()+".");

        // YOUR CODE HERE
        return planDynamic;
    }

    /**
     * {@link #planDynamic} must be filled after calling this method
     */
    public void solveDynamic(int i) {
        // YOUR CODE HERE

        System.out.println("Called solveDynamic(" + i +")");

        if(i<=0 || compatibility[i]==-1){
            planDynamic.add(taskArray[i]);
            return;
        }

        else{

            // Case 1: task i is in the solution
            double include = taskArray[i].getWeight() + maxWeight[compatibility[i]];

            // Case 2: task i is not in the solution
            double exclude = maxWeight[i - 1];


            if (include > exclude) {// If task i is in the solution call this function for next compatible task
                solveDynamic(compatibility[i]);
                planDynamic.add(taskArray[i]);
            } else {
                solveDynamic(i - 1);
            }
        }

    }

    /**
     * {@link #maxWeight} must be filled after calling this method
     */
    /* This function calculates maximum weights and prints out whether it has been called before or not  */
    public Double calculateMaxWeight(int i) {
        // YOUR CODE HERE

        System.out.println("Called calculateMaxWeight(" + i + ")");

        if(i == -1){
            return 0.0;
        }

        // If maxWeight for task i has already been calculated, return it
        if (maxWeight[i] != null && i>0) {
            return maxWeight[i];
        }

        // Case 1: task i is in the solution
        double weightWithTaskI = taskArray[i].getWeight();

        weightWithTaskI += calculateMaxWeight(compatibility[i]);

        // Case 2: task i is not in the solution
        double weightWithoutTaskI = 0;

        weightWithoutTaskI = calculateMaxWeight(i-1);

        double maxWeightForTaskI = Math.max(weightWithTaskI, weightWithoutTaskI);
        maxWeight[i] = maxWeightForTaskI;
        return maxWeightForTaskI;

    }

    /**
     * {@link #planGreedy} must be filled after calling this method
     * Uses {@link #taskArray} property
     *
     * @return Returns a list of scheduled assignments
     */

    /*
     * This function is for generating a plan using the greedy approach.
     * */
    public ArrayList<Task> planGreedy() {
        // YOUR CODE HERE

        planGreedy.add(taskArray[0]);


        int lastIndex=-1;

        for(int i=1;i<taskArray.length;i++){
            if(compatibility[i] >= lastIndex && compatibility[i] != compatibility[i-1]){
                planGreedy.add(taskArray[i]);
                lastIndex = i;
            }
        }

        System.out.println("Greedy Schedule\n---------------");

        for (Task task:planGreedy) System.out.println("At "+ task.getStartTime() + ", "+task.getName()+".");

        return planGreedy;
    }

    public int compareTimes(String s1, String s2){
        LocalTime t1 = LocalTime.parse(s1);
        LocalTime t2 = LocalTime.parse(s2);

        return t1.compareTo(t2);
    }
}

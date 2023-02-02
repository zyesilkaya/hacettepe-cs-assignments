
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class calculation {
    //[0 name] tab [1 age] tab [2 daily calorie needs] tab [3 calories taken] tab [4 calories burned] tab [5 result] newline

    public static int count=0;
    //find dailyCalorieNeed for person 
    public static int dailyCalorieNeed(String gender,int weight,int height,int age){
        double dailyCal = 0;
        if(gender.equals("male")){
            dailyCal = 66+(13.75*(double)(weight))+(5*(double)(height))-(6.8*((double)age));
        }
        
        else if(gender.equals("female")){
             dailyCal = 665+(9.6*(double)(weight))+(1.7*(double)(height))-(4.7*((double)age));
        }
        return (int) Math.round(dailyCal);
    }
    //Find burned calories for given person and sport id
    public static void burnedCalories(String[] lineArr,ArrayList<People> peopleArray,ArrayList<Sport> sportArray,FileWriter writer, LinkedHashMap<String, People> mapForPrintPeople) throws IOException{
        //search for food whose id is given
        int sportIndex = 0;
        for(Sport sport:sportArray){   
            if(lineArr[1].equals(sport.sportId)){
               break;
            }
            sportIndex+=1;
        }
        //search for people whose id is given and add this person to hashmap
        for(People i:peopleArray){
            if(i.personId.equals(lineArr[0])){
                i.burnedCal += (int)(sportArray.get(sportIndex).cal*((double)Integer.parseInt(lineArr[2])/60));
                writer.write(i.personId+"\thas\tburned\t"+(int)(sportArray.get(sportIndex).cal*((double)Integer.parseInt(lineArr[2])/60))
                             +"kcal\tthanks\tto\t"+sportArray.get(sportIndex).sportName+"\n");
                
                if(!mapForPrintPeople.containsKey(i.personId)){
                    mapForPrintPeople.put(i.personId, i);
                }
                break;
            }
        }          
    }
    //Find taken calories for given person and food id
    public static void takenCalories(String[] lineArr,ArrayList<People> peopleArray,ArrayList<Food> foodArray,FileWriter writer,LinkedHashMap<String, People> mapForPrintPeople) throws IOException{
        //search for food whose id is given
        int foodIndex  = 0;
        for(Food foods:foodArray){
            if(foods.foodId.equals(lineArr[1])){  
                break;
            }
            foodIndex+=1;
        }
        //search for people whose id is given and add this person to hashmap
        for(People i:peopleArray){
            if(i.personId.equals(lineArr[0])){
                i.takenCal += foodArray.get(foodIndex).foodCal*(Integer.parseInt(lineArr[2]));
                
                writer.write(i.personId+"\thas\ttaken\t"+foodArray.get(foodIndex).foodCal*(Integer.parseInt(lineArr[2]))
                             +"kcal\tfrom\t"+foodArray.get(foodIndex).foodName+"\n");
                
                if(!mapForPrintPeople.containsKey(i.personId)){
                    mapForPrintPeople.put(i.personId, i);
                }
                break;
            }
        }
        
    }
}
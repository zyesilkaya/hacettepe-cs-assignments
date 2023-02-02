import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.LinkedHashMap;


public class Main {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // Create scanner reader for food sport and person file 
        File peopleFile = new File("people.txt");
        Scanner scanForPeople = new Scanner(peopleFile);
        File foodFile = new File("food.txt");
        Scanner scanForFood = new Scanner(foodFile);
        File sportFile = new File("sport.txt");
        Scanner scanForSport = new Scanner(sportFile);
        File inputFile = new File(args[0]);
        Scanner commands = new Scanner(inputFile);
        
        FileWriter writer = new FileWriter("monitoring.txt");
        //Create ArrayList to store objects
        ArrayList<People> peopleArray = new ArrayList<People>();
        ArrayList<Food> foodArray = new ArrayList<Food>();
        ArrayList<Sport> sportArray = new ArrayList<Sport>();
        LinkedHashMap<String, People> mapForPrintPeople = new LinkedHashMap<String, People>();
        int count = 0;
        //read People file and create new object for every person in file.Then store their informations 
        while(scanForPeople.hasNext()){
            
            String[] person = scanForPeople.nextLine().trim().split("\t",6);
            peopleArray.add(count,new People(person[0], person[1], person[2], Integer.parseInt(person[3]), Integer.parseInt(person[4]), Integer.parseInt(person[5]))); 
            //find dailyCalorieNeed for person
            peopleArray.get(count).dailyCal = calculation.dailyCalorieNeed(person[2], Integer.parseInt(person[3]), Integer.parseInt(person[4]), (2022-Integer.parseInt(person[5])));
            count+=1;
        }
        
        //read Food file and create new object for every food in file.Then store their informations 
        count = 0;
        while(scanForFood.hasNext()){
            
            String[] food = scanForFood.nextLine().trim().split("\t",3);
            foodArray.add(count, new Food(food[0], food[1], Integer.parseInt(food[2])));
            count+=1;
        }  
        
        //read SPort file and create new object for every sport in file.Then store their informations 
        count=0;
        while(scanForSport.hasNext()){
            
            String[] sport = scanForSport.nextLine().trim().split("\t",3);
            sportArray.add(count,new Sport(sport[0], sport[1], Integer.parseInt(sport[2])) );
            
            count+=1;
        }    
        //read every line in command file
        while(commands.hasNext()){
            String line = commands.nextLine();
            //Check if command equals printList and if it is true print the hashmap
            if(line.equals("printList")){ 
                mapForPrintPeople.forEach((key, value) -> {
                    try {
                        writer.write(value.name+"\t"+(value.age)+"\t"+value.dailyCal+"kcal\t"+value.takenCal+"kcal\t"                      
                                +value.burnedCal+"kcal\t"+value.result()+"\n");
                    } catch (IOException ex) {                    }
                });
                
            }
            //Check if command equals printWarn and if it is true print people whose calorie result is grater than or equal to zero
            //and if it is not exist print "there is no such person"
            else if(line.equals("printWarn")){

                int k = 0;
                int len = 0;
                if (mapForPrintPeople.isEmpty()) writer.write("there is no such person\n");
                
                for(People value:mapForPrintPeople.values()){ 
                    len++;
                    if(((value.takenCal-value.burnedCal)-value.dailyCal)>=0){
                        writer.write(value.name+"\t"+(value.age)+"\t"+value.dailyCal
                        +"\t"+value.takenCal+"kcal\t"+value.burnedCal+"kcal\t"+value.result()+"\n");
                        
                        k+=1;
                    }

                    if(len==mapForPrintPeople.size() && k==0){
                        writer.write("there\tis\tno\tsuch\tperson\n");                        
                    }
                    
                }
            }
             //Check if command equals print and if it is true print the person's informations whose id is given in parantheses
            else if(line.substring(0,5).equals("print")){
                String idForPrint = line.substring(6,11);
                for(People person:peopleArray){
                    if(person.personId.equals(idForPrint)){
                        
                        writer.write(person.name+"\t"+(person.age)+"\t"+person.dailyCal
                        +"\t"+person.takenCal+"kcal\t"+person.burnedCal+"kcal\t"+person.result()+"\n");
                        
                    }                    
                }
            }     
            //if line starts with person id check 1. index. If it starts with "20" call burnedCalories method.
            //else call takenCalories method.
            else{
                String[] lineArray = line.split("\t",3);
                if(lineArray[1].substring(0,2).equals("20")){                   
                    calculation.burnedCalories(lineArray,peopleArray,sportArray,writer,mapForPrintPeople);                    
                }
                
                else{                    
                    calculation.takenCalories(lineArray,peopleArray,foodArray,writer,mapForPrintPeople);                   
                }                
            }
            //This is for printing stars after every line.
            
            if(commands.hasNext()){
                writer.write("***************\n");}
            
        }
        writer.close();
        
    }
    
}

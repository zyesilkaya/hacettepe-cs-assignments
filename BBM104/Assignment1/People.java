
public class People {
    String personId;
    String name;
    String gender;
    int weight;
    int height;
    int age;
    int dailyCal=0;
    int takenCal=0;
    int burnedCal=0;
    int result=0;
    
    public People(String personId, String name, String gender, int weight, int height, int birthDate) {
        this.personId = personId;
        this.name = name;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.age = 2022-birthDate;
        
    }
    public String result(){
        result = (takenCal-burnedCal)-dailyCal;
        if(result>0) return "+"+Integer.toString(result)+"kcal";
        else return Integer.toString(result)+"kcal";
    }

}

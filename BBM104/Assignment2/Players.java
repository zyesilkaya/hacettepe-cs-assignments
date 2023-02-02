

import java.util.ArrayList;

public class Players {
    ArrayList<properties> owns= new ArrayList<>();
    public static int bankersMoney = 100000;
    private String name;
    private int money = 15000;
    private int count = 0;
    private int position = 1;
    private boolean bankrupt = true;
    private boolean parking = false;

    public boolean isParking() {
        return parking;
    }

    public void setParking(boolean parking) {
        this.parking = parking;
    }

    public boolean isBankrupt() {
        return bankrupt;
    }

    public void setBankrupt(boolean bankrupt) {
        this.bankrupt = bankrupt;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
    
    public Players(String name){
        this.name = name;   
    }

    public void findPosition(int x){
        if(getPosition() +x>40){
            setPosition((getPosition() +x)-40);
            setMoney(getMoney()+200);
            Players.bankersMoney-=200;
        }
        else {
            setPosition(getPosition() + x);}
        }
    
    //buy a property and add this in an arraylist
    public void ownIt(properties propertiesOwn, Players player){
        if(propertiesOwn.getCost()<=player.getMoney()){
            propertiesOwn.setOwner(player);
            owns.add(propertiesOwn);
        }
    }

    public int railroadNum(){
        int num = 0;
        for(properties prop: owns){
            if(prop instanceof Railroads){num+=1;}
        }
        return num;
    }

    public boolean jail(){
        if (count==0) return true;
        else if (count<=3) {
            return false;
        }
        count = 0;
        return true;
    }
    public void goBankrupt(){
        this.bankrupt = false;
        Main.output+=getName()+"\tgoes bankrupt\n";
    }
}

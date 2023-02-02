

public class PropertySquares {

    //this method controls the square is null nor not
    public void isNull(Players player, properties property, int dice, Players player1, Players player2, int position){

        if(property.getOwner() == null){
            nullOnes(player,property,player1,player2,dice,position);
        }

        else{
            if(property.getOwner()!=player){
                ownedOnes(player,property,dice, player1, player2,position);}
            else{Main.output+=player.getName()+"\t"+dice+"\t"+position+"\t"+player1.getMoney()+"\t"+player2.getMoney()+"\t"+player.getName()+"\t"+"has\t"+property.getName()+"\n";}
        }
    }

    //this method is for owning a null square
    public void nullOnes(Players player, properties property, Players player1, Players player2, int dice, int position){
        if(player.getMoney()>=property.getCost()){
            player.ownIt(property, player);
            player.setMoney(player.getMoney() - property.getCost());
            Players.bankersMoney+=property.getCost();
            Main.output+=player.getName()+"\t"+dice+"\t"+position+"\t"+player1.getMoney()+"\t"+player2.getMoney()+"\t"+player.getName()+"\t"+"bought\t"+property.getName()+"\n";
        }
        else{Main.output+=player.getName()+"\tgoes bankrupt";player.setBankrupt(false);}
    }

    //this method calculates rent for owned squares with using instaceof keyword
    public void ownedOnes(Players player, properties property, int dice, Players player1, Players player2, int position){
        int rent = 1;
        if (property instanceof Railroads) {
            rent = 25*(player.railroadNum());
        }

        else if (property instanceof Land) {
            if (property.getCost() > 0 && property.getCost() <= 2000) {
                rent = property.getCost() * 4 / 10;
            }
            if (property.getCost() > 2000 && property.getCost() <= 3000) {
                rent = property.getCost() * 3 / 10;
            }
            if (property.getCost() > 3000 && property.getCost() <= 4000) {
                rent = property.getCost() * 35 / 100;
            }
        }

        else if (property instanceof Company) {
            rent = 4 * dice;
        }

        if(player.getMoney()>=rent){
            player.setMoney(player.getMoney() - rent);
            property.getOwner().setMoney(property.getOwner().getMoney() + rent);
            Main.output+=player.getName()+"\t"+dice+"\t"+position+"\t"+player1.getMoney()+"\t"+player2.getMoney()+"\t"+player.getName()+"\t"+"paid rent for\t"+property.getName()+"\n";
        }
        else{
            Main.output+=player.getName()+"\t"+dice+"\t"+position+"\t"+player1.getMoney()+"\t"+player2.getMoney()+"\t"+player.getName()+"\tgoes bankrupt";
            player.setBankrupt(false);}
    }
}


public class CommunityCard extends Cards {
    private static int index = 1;
    String description = "";
    public Players otherPlayer(Players player, Players player1, Players player2){
        if(player.getName().equals(player1.getName())) {return player2;}
        else{return player1;}
    }

    public void communityCards(int position, Players player, Players player1, Players player2, properties[] board, int dice){

            if(index >11) index =1;

            if(index == 1){
                player.setMoney(player.getMoney()+200);
                Players.bankersMoney-=200;
                player.setPosition(1);
                Main.output+=player.getName() + "\t" + dice + "\t" + position + "\t" + player1.getMoney() + "\t" + player2.getMoney() + "\t" +player.getName()+"\t" + "draw Community Chest -advance to go\n";

            }
            else if(index == 2){
                player.setMoney(player.getMoney()+75);
                Players.bankersMoney-=75;
                Main.output+=player.getName() + "\t" + dice + "\t" + position + "\t" + player1.getMoney() + "\t" + player2.getMoney() + "\t" +player.getName()+"\t" + "Bank error in your favor - collect $75\n";

            }
            else if(index == 3){
                if(player.getMoney()>=50){
                    player.setMoney(player.getMoney()-50);
                    Players.bankersMoney+=50;
                    Main.output+=player.getName() + "\t" + dice + "\t" + position + "\t" + player1.getMoney() + "\t" + player2.getMoney() + "\t" +player.getName()+"\t" + "Doctor's fees - Pay $50\n";
                }
                else{ player.goBankrupt();}
            }

            else if(index == 4) {
                if (player.getMoney() >= 10) {
                    player.setMoney(player.getMoney() + 10);
                    otherPlayer(player,player1,player2).setMoney(otherPlayer(player,player1,player2).getMoney() - 10);
                    Main.output += player.getName() + "\t" + dice + "\t" + position + "\t" + player1.getMoney() + "\t" + player2.getMoney() + "\t" + player.getName() + "\t" + "It is your birthday Collect $10 from each player\n";
                }
                else{otherPlayer(player,player1,player2).goBankrupt();}
            }
            else if(index == 5){
                if(player.getMoney()>=50){
                    player.setMoney(player.getMoney()+50);
                    otherPlayer(player,player1,player2).setMoney(otherPlayer(player,player1,player2).getMoney()-50);
                    Main.output+=player.getName()+"\t"+dice+"\t"+position+"\t"+player1.getMoney()+"\t"+player2.getMoney()+"\t"+player.getName()+"\t" + "Grand Opera Night - collect $50 from every player for opening night seats\n";
                }
                else{otherPlayer(player,player1,player2).goBankrupt();}
            }
            else if(index == 6){
                player.setMoney(player.getMoney()+20);
                Players.bankersMoney-=20;
                Main.output+=player.getName()+"\t"+dice+"\t"+position+"\t"+player1.getMoney()+"\t"+player2.getMoney()+"\t"+player.getName()+"\t" + "Income Tax refund - collect $20\n";
            }

            else if(index == 7) {
                    player.setMoney(player.getMoney() + 100);
                    Players.bankersMoney -= 100;
                    Main.output += player.getName() + "\t" + dice + "\t" + position + "\t" + player1.getMoney() + "\t" + player2.getMoney() + "\t" + player.getName() + "\t" + "Life Insurance Matures - collect $100";
            }
            else if(index == 8) {
                if (player.getMoney() >= 100) {
                    player.setMoney(player.getMoney() - 100);
                    Players.bankersMoney += 100;
                    Main.output += player.getName() + "\t" + dice + "\t" + position + "\t" + player1.getMoney() + "\t" + player2.getMoney() + "\t" + player.getName() + "\t" + "Pay Hospital Fees of $100";
                }
                else{ player.goBankrupt();}
            }
            else if(index == 9) {
                if (player.getMoney() >= 50) {
                    player.setMoney(player.getMoney() - 50);
                    Players.bankersMoney += 50;
                    Main.output += player.getName() + "\t" + dice + "\t" + position + "\t" + player1.getMoney() + "\t" + player2.getMoney() + "\t" + player.getName() + "\t" + "Pay School Fees of $50\n";
                }
                else{player.goBankrupt();}
            }

            else if(index == 10) {
                    player.setMoney(player.getMoney() + 100);
                    Players.bankersMoney -= 100;
                    Main.output += player.getName() + "\t" + dice + "\t" + position + "\t" + player1.getMoney() + "\t" + player2.getMoney() + "\t" + player.getName() + "\t" + "You inherit $100\n";
            }
            else if(index == 11) {
                    player.setMoney(player.getMoney() + 50);
                    Players.bankersMoney -= 50;
                    Main.output += player.getName() + "\t" + dice + "\t" + position + "\t" + player1.getMoney() + "\t" + player2.getMoney() + "\t" + player.getName() + "\t" + "From sale of stock you get $50\n";
            }
        index++;
        }

}


public class ChanceCard extends Cards {
    private static int item = 1;
    PropertySquares propsq = new PropertySquares();
    CommunityCard cmc = new CommunityCard();
    Process doThings = new Process();
    Tax taxsq = new Tax();

    public void chanceCards(int position, Players player, Players player1, Players player2, properties[] board, int dice) {

            if (item > 6) item = 1;

            if (item == 1) {
                player.setMoney(player.getMoney() + 200);
                Players.bankersMoney -= 200;
                player.setPosition(1);
                Main.output+=player.getName()+"\t"+dice+"\t"+player.getPosition()+"\t"+player1.getMoney()+"\t"+player2.getMoney()+"\t"+player.getName()+"\t" + "advance to go (collect 200)\n";}

            else if (item == 2) {
                if(player.getPosition()>27){
                    player.setMoney(player.getMoney() + 200);
                    Players.bankersMoney -= 200;
                }
                player.setPosition(27);
                propsq.isNull(player,board[player.getPosition()],dice, player, player2, player.getPosition());
                Main.output+=player.getName()+"\t"+dice+"\t"+position+"\t"+player1.getMoney()+"\t"+player2.getMoney()+"\t"+player.getName()+"\t" + "advance to  Leicester Square\n";

            }
            else if (item == 3) {
                if(position==37){
                    player.setPosition(player.getPosition() - 3);
                    cmc.communityCards(position, player,player1, player2,board,dice);
                    }
                if(position==8){
                    player.setPosition(player.getPosition() - 3);
                    taxsq.tax(player);
                    Main.output+=player.getName()+"\t"+dice+"\t"+position+"\t"+player1.getMoney()+"\t"+player2.getMoney()+"\t"+player.getName()+"\t"+"paid Tax\n";
                }
                if(position==23){
                    player.setPosition(player.getPosition() - 3);
                    doThings.doSomething(player.getPosition(),player,player1,player2,board,dice);
                }
                }

            else if (item == 4) {
                if(player.getMoney()>=15) {
                    player.setMoney(player.getMoney() - 15);
                    Players.bankersMoney += 15;
                    Main.output += player.getName() + "\t" + dice + "\t" + position + "\t" + player1.getMoney() + "\t" + player2.getMoney() + "\t" + player.getName() + "\t" + "Pay poor tax of $15\n";
                }
                else{player.goBankrupt();}
            }

            else if (item == 5) {
                player.setMoney(player.getMoney() + 150);
                Players.bankersMoney -= 150;
                Main.output+=player.getName()+"\t"+dice+"\t"+position+"\t"+player1.getMoney()+"\t"+player2.getMoney()+"\t"+player.getName()+"\t" +"Your building loan matures - collect $150\n";
            }
            else if (item == 6) {
                player.setMoney(player.getMoney() + 100);
                Players.bankersMoney -= 100;
                Main.output+=player.getName()+"\t"+dice+"\t"+position+"\t"+player1.getMoney()+"\t"+player2.getMoney()+"\t"+player.getName()+"\t"+"You have won a crossword competition - collect $100\n";
            }
            item++;
    }
}


//this class is for doing things in board
public class Process {
    //this method is for show() command
    public void show(Players player1, Players player2){
	int count=0;
        Main.output+="\n-----------------------------------------------\n";
        Main.output+="Player 1 "+player1.getMoney()+"\thave:\t";
        for(properties owned: player1.owns){
            count++;
            Main.output+=owned.getName()+" ";
            if(count!=player1.owns.size()){
                Main.output+=",";
            }
        }
        Main.output+="\n";
        Main.output+="Player 2 "+player2.getMoney()+"\thave:\t";
	count=0;
        for(properties owned: player2.owns){
            count++;
            Main.output+=owned.getName()+" ";
            if(count!=player2.owns.size()){
                Main.output+=",";
            }
        }
        Main.output+="\nBankers Money "+ Players.bankersMoney+"\n";
        if(player1.getMoney()>player2.getMoney()) Main.output+="Winner "+player1.getName()+"\n-----------------------------------------------\n";
        else Main.output+="Winner "+player2.getName()+"\n-----------------------------------------------\n";
    }

    public void doSomething(int position, Players player, Players player1, Players player2, properties[] board, int dice){

        PropertySquares ownedSquare = new PropertySquares();
        Cards cardd = new Cards();
        Tax taxsq = new Tax();
        Jails jail = new Jails();

        if(player.isParking()){
            player.setPosition(21);
            player.setParking(false);
            Main.output+=player.getName()+"\t"+dice+"\t"+player.getPosition()+"\t"+player1.getMoney()+"\t"+player2.getMoney()+"\t"+player.getName()+"\t"+"wait for Free Parking\n";
        }

        else if(position==1){
            Main.output+=player.getName()+"\t"+dice+"\t"+position+"\t"+player1.getMoney()+"\t"+player2.getMoney()+"\t"+player.getName()+"\t"+"in GO square\t";
        }
        //tax squares
        else if (position == 5 || position == 39 && player.getMoney()>=100) {
            taxsq.tax(player);
            Main.output+=player.getName()+"\t"+dice+"\t"+position+"\t"+player1.getMoney()+"\t"+player2.getMoney()+"\t"+player.getName()+"\t"+"paid Tax\n";
        }
        //free parking
        else if(position==21) {
            player.setParking(true);
            Main.output+=player.getName()+"\t"+dice+"\t"+position+"\t"+player1.getMoney()+"\t"+player2.getMoney()+"\t"+player.getName()+"\t"+"is in Free Parking\n";
        }

        //go to jail
        else if (position == 11 || position == 31) {
            jail.jail(player);
            Main.output+=player.getName()+"\t"+dice+"\t"+position+"\t"+player1.getMoney()+"\t"+player2.getMoney()+"\t"+player.getName()+"\t"+"went to jail\n";
        }

        else if (board[position] == null){
            cardd.findCards(position, player,player1, player2,board,dice);
        }

        else {
            ownedSquare.isNull(player, board[player.getPosition()], dice, player1, player2, position);
        }
    }
}

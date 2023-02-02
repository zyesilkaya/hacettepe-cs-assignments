
public class Cards extends Board {
    //this method controls the card is a community or a chance card
    public void findCards(int position, Players player, Players player1, Players player2, properties[] board, int dice){
        ChanceCard chc = new ChanceCard();
        CommunityCard cmc = new CommunityCard();

        if (position == 3 || position == 18 || position == 34) {cmc.communityCards(position, player, player1, player2,board,dice);}

        else if(position==8 || position==23 || position==37){chc.chanceCards(position, player,player1, player2,board,dice);}
    }

}

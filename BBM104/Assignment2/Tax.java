
public class Tax extends Board {
    public void tax(Players player){
        player.setMoney(player.getMoney() - 100);
        Players.bankersMoney += 100;
    }
}

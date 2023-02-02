

public class Jails extends Board {
    public void jail(Players player){
        player.setPosition(11);
        player.setCount(player.getCount()+1);
        player.jail();
    }
}

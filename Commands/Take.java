package Commands;

import Game.Player;
import GameObjects.GameObject;
import GameObjects.Items.Item;

public class Take implements Command
{
    @Override
    public void run(GameObject target) 
    {
        Item _target = (Item) target;
        Player.addItem(_target);
    }
}

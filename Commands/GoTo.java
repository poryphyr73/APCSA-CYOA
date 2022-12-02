package Commands;

import Game.Node;
import Game.Player;
import GameObjects.GameObject;
import GameObjects.Items.Item;
import GameObjects.Mobs.Enemy;
import GameObjects.Mobs.Mob;
import GameObjects.Mobs.NPC;

public class GoTo implements Command
{
    @Override
    public void run(GameObject target) {
        if(target.getClass().equals(NPC.class))
        {
            NPC _target = (NPC) target;
            System.out.println(_target.getTalk());
        }
        else if(target.getClass().equals(Enemy.class))
        {
            Mob _opponent = (Mob) target;
            CommandManager.attack(_opponent);
        }
        else if(target.getClass().equals(Node.class))
        {
            Node _place = (Node) target;
            System.out.println(_place);
            for(Item i : _place.getItems())
            {
                Player.addItem(i);
                System.out.println("You found a(n) " + i.getName() + "!");
                _place.getItems().remove(i);
            }
        }
    }
}

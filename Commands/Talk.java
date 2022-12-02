package Commands;

import GameObjects.GameObject;
import GameObjects.Mobs.Mob;

public class Talk implements Command
{
    @Override
    public void run(GameObject target) 
    {
        Mob _target = (Mob) target;
        System.out.println(_target.getTalk());
    }
}

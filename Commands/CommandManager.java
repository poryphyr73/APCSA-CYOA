package Commands;

import Game.Node;
import GameObjects.Items.*;
import GameObjects.Mobs.*;
public class CommandManager
{
    private static Attack commandAttack = new Attack();
    private static GoTo commandGoTo = new GoTo();
    private static Take commandTake = new Take();
    private static Talk commandTalk = new Talk();

    public static void attack(Mob target)
    {
        commandAttack.run(target);
    }

    public static void goTo(Mob target)
    {
        commandGoTo.run(target);
    }

    public static void goTo(Node target)
    {
        commandGoTo.run(target);
    }

    public static void take(Item target)
    {
        commandTake.run(target);
    }

    public static void talk(Mob target)
    {
        commandTalk.run(target);
    }
}
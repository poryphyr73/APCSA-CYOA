package Commands;

import Game.Node;
import GameObjects.Items.*;
import GameObjects.Mobs.*;
public class CommandManager
{
    private static Attack commandAttack;
    private static Give commandGive;
    private static GoTo commandGoTo;
    private static Take commandTake;
    private static Talk commandTalk;
    private static Use commandUse;

    public CommandManager()
    {
        commandAttack = new Attack();
        commandGive = new Give();
        commandGoTo = new GoTo();
        commandTake = new Take();
        commandTalk = new Talk();
        commandUse = new Use();
    }

    public static void attack(Mob target)
    {
        commandAttack.run(target);
    }
    public static void give(Item target)
    {
        commandGive.run(target);
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

    public static void use(Item target)
    {
        commandUse.run(target);
    }
}
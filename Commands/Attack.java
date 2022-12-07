package Commands;

import java.util.Scanner;

import Game.Manager;
import Game.Player;
import GameObjects.GameObject;
import GameObjects.Mobs.Mob;

public class Attack implements Command
{
    private Scanner kb = new Scanner(System.in);
    @Override
    public void run(GameObject target) {
        Mob opponent = (Mob) target;
        System.out.println("------------");
        if(opponent.getDanger()) System.out.println("\n" + opponent.getTalk());
        System.out.println("You engage in combat with a " + opponent.getName() + "!");
        while(true)
        {
            System.out.println("What do you do?\n");
            while(true)
            {
                System.out.println("ATTACK: [1] | USE AN ITEM: [2]");
                int input = kb.nextInt();
                System.out.println();
                if(input != 1 && input != 2) 
                {
                    System.out.println("Invalid Command. Try Again.\n");
                    return;
                }
                else if(input == 1) 
                {
                    int damage = Player.getDamage();
                    opponent.setHP(opponent.getHP() - Player.getDamage());
                    System.out.println("The " + opponent.getName() + " takes " + damage + " damage!\n");
                }
                else Player.useItem(); //FIX THIS
                break;
            }
            if(opponent.getHP() <= 0) 
            {
                System.out.println("You won the battle!\n");
                if(!opponent.getTake().equals(null)) 
                {
                    if(!opponent.getTake().getId().equals("-1"))
                    {
                        Player.addItem(opponent.getTake());
                        System.out.println("You got a(n) " + opponent.getTake().getName() + "!");
                    }
                    Player.damage(-10 + Player.getHealth());
                    System.out.println("You were healed to full health!");
                    Manager.getMap().getNode().removeMob(opponent);
                }
                break;
            }
            System.out.println("The " + opponent.getName() + " attacks!\n");
            int dmg = opponent.getAtk();
            System.out.println("The " + opponent.getName() + " deals " + dmg + " (-" + Math.min(dmg, Player.getDefense()) + " from defense boosts) damage!\n");
            Player.damage(Math.min(dmg, dmg - Player.getDefense()));
            if(Player.getHealth() <= 0) 
            {
                break;
            }
        }
    }
}

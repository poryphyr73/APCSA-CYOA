package Game;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.text.html.HTMLDocument.RunElement;

import GameObjects.Items.Armor;
import GameObjects.Items.Consumable;
import GameObjects.Items.Item;
import GameObjects.Items.Weapon;

public class Player 
{
    private static String name;
    private static int maxHealth = 10;
    private static int health = maxHealth;
    private static int skillPoints = 10;
    private static int[] stats = new int[2];
    private static HashMap<Item, Integer> inventory = new HashMap<Item, Integer>();
    private static Weapon currentWeapon = new Weapon("Fists", "000", 0);
    private static Armor currentArmor = new Armor("Common Clothes", "000", 0);;
    private static Scanner kb = new Scanner(System.in);

    public static void setName(String _name)
    {
        name = _name;
    }

    public static String getName()
    {
        return name;
    }

    public static int getHealth()
    {
        return health;
    }

    public static int getSkillPoints()
    {
        return skillPoints;
    }

    public static int getStat(int index)
    {
        return stats[index];
    }

    public static void incrementStat(int index)
    {
        stats[index]++;
    }

    public static void decrementSP()
    {
        skillPoints--;
    }

    public static void addItem(Item i)
    {
        if(!inventory.keySet().contains(i)) inventory.put(i, 0);
        inventory.put(i, inventory.get(i) + 1);
    }

    public static int getDamage()
    {
        int damage = stats[0] + 1;
        if(!currentWeapon.equals(null)) damage += currentWeapon.getBuff() / 10;
        if(!currentArmor.equals(null)) damage += currentArmor.getBuff() / 10;
        return damage;
    }

    public static int getDefense()
    {
        int defense = stats[1];
        if(!currentWeapon.equals(null)) defense += currentWeapon.getBuff() % 10;
        if(!currentArmor.equals(null)) defense += currentArmor.getBuff() % 10;
        return defense;
    }

    public static void heal(int k)
    {
        health += k;
    }

    public static Weapon getCurrentWeapon()
    {
        return currentWeapon;
    }

    public static Armor getCurrentArmor()
    {
        return currentArmor;
    }

    public static void useItem()
    {
        int iteration = 1;
        if(Player.getInventory().keySet().size() == 0)
        {
            System.out.println("Your inventory is empty!");
            return;
        }
        for (Item current : Player.getInventory().keySet()) 
        {
            System.out.println("\t" + iteration + ": " + current + " (" + Player.getInventory().get(current) + " remaining)");
        }
        System.out.print("Input an item index: ");
        int i = kb.nextInt() - 1;

        if(i < 0 || i > inventory.keySet().size() || inventory.keySet().size() <= 0)
        {
            return;
        }
        Item cur = (Item) inventory.keySet().toArray()[i];
        if(cur.getClass().equals(Weapon.class))
        {
            if(!currentWeapon.equals(null)) addItem(cur);
            currentWeapon = (Weapon) cur;
        }
        else if(cur.getClass().equals(Armor.class))
        {
            if(!currentArmor.equals(null)) addItem(cur);
            currentArmor = (Armor) cur;
        }
        else if(cur.getClass().equals(Consumable.class))
        {
            int buff = cur.getBuff();
            for(int j = 3; j >= 0; j--)
            {
                stats[j] += buff % 10;
                buff /= 10;
            }
        }

        inventory.put(cur, inventory.get(cur) - 1);
        if(inventory.get(cur) <= 0)  inventory.remove(cur);
        System.out.println(cur + " used!");
    }

    public static HashMap<Item, Integer> getInventory()
    {
        return inventory;
    }
}

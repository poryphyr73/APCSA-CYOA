package Game;
import java.util.HashMap;

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
    private static int[] stats = new int[4];
    private static HashMap<Item, Integer> inventory = new HashMap<Item, Integer>();
    private static Weapon currentWeapon;
    private static Armor currentArmor;

    public Player()
    {
        health = maxHealth;
    }

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

    public static void useItem(int i)
    {
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
    }

    public static HashMap<Item, Integer> getInventory()
    {
        return inventory;
    }
}

package Game;
import java.util.HashMap;

import GameObjects.Items.Armor;
import GameObjects.Items.Consumable;
import GameObjects.Items.Item;
import GameObjects.Items.Weapon;

public class Player 
{
    private String name;
    private int health, maxHealth = 10;
    private int skillPoints = 10;
    private int[] stats = new int[4];
    private HashMap<Item, Integer> inventory = new HashMap<Item, Integer>();
    private Weapon currentWeapon;
    private Armor currentArmor;

    public Player()
    {
        health = maxHealth;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public int getHealth()
    {
        return health;
    }

    public int getSkillPoints()
    {
        return skillPoints;
    }

    public int getStat(int index)
    {
        return stats[index];
    }

    public void incrementStat(int index)
    {
        stats[index]++;
    }

    public void decrementSP()
    {
        skillPoints--;
    }

    public void addItem(Item i)
    {
        if(!inventory.keySet().contains(i)) inventory.put(i, 0);
        inventory.put(i, inventory.get(i) + 1);
    }

    public void useItem(int i)
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
}

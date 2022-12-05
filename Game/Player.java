package Game;
import java.util.HashMap;
import java.util.Scanner;

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

    
    /** Get the stat value at a given index
     * 
     * @param index The index of the array to search at. 0 returns the Player's strength, 1 returns the dexterity.
     * @return int The stat at the given index
     */
    public static int getStat(int index)
    {
        return stats[index];
    }

    /** Add one skill point to a stat at a given index of stats Array
     * 
     * @param index The index of stats[] to add a point to. 0 adds a point to strength, 1 adds a point to dexterity
     */
    public static void incrementStat(int index)
    {
        stats[index]++;
    }

    public static void decrementSP()
    {
        skillPoints--;
    }

    /** Add an Item to the player's inventory
     * Adds a new key for it if the player does not yet have one, increments the number of that Item type in the inventory otherwise
     * 
     * @param i The Item to add
     */
    public static void addItem(Item i)
    {
        if(!inventory.keySet().contains(i)) inventory.put(i, 0);
        inventory.put(i, inventory.get(i) + 1);
    }

    /** Gets the player's damage output
     * 
     * @return int The damage as a sum of the players strength and item strength buffs
     */
    public static int getDamage()
    {
        int damage = stats[0] + 1;
        if(!currentWeapon.equals(null)) damage += currentWeapon.getBuff() / 10;
        if(!currentArmor.equals(null)) damage += currentArmor.getBuff() / 10;
        return damage;
    }

    /** Get the player's defense from damage
     * 
     * @return int The damage from which the player can be defended from as a sum of the player's strength (dodge) and item dexterity buffs
     */
    public static int getDefense()
    {
        int defense = stats[1];
        if(!currentWeapon.equals(null)) defense += currentWeapon.getBuff() % 10;
        if(!currentArmor.equals(null)) defense += currentArmor.getBuff() % 10;
        return defense;
    }

    /** Damage the player. Can also be used to heal the player if k is negative
     * 
     * @param k The amount of damage to be dealt to the player
     */
    public static void damage(int k)
    {
        health -= k;
    }

    public static Weapon getCurrentWeapon()
    {
        return currentWeapon;
    }

    public static Armor getCurrentArmor()
    {
        return currentArmor;
    }

    /* "Use" an item from the player's inventory
     * 
     * if the Item is consumable, apply its buffs and delete it
     * if the Item is a weapon, swap it with the equipped item
     * if the Item is armor, swap it with the equipped item
     */
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

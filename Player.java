import java.util.HashMap;

public class Player 
{
    private String name;
    private int health, maxHealth = 10;
    private int skillPoints = 10;
    private int[] stats = new int[4];
    private HashMap<Item, Integer> inventory = new HashMap<Item, Integer>();

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
}

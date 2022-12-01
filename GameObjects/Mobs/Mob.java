package GameObjects.Mobs;
import GameObjects.GameObject;
import GameObjects.Items.*;
import java.util.Random;
import java.util.function.BiPredicate;

public class Mob extends GameObject
{
    private String name;
    private String id;
    private String talk;
    private Item take;
    private boolean danger;
    private int hp;
    private int atk1;
    private int atk2;

    private Random random;

    public Mob(String name, String id, String talk, Item take, boolean danger, int hp, int atk1, int atk2)
    {
        this.name = name;
        this.id = id;
        this.talk = talk;
        this.take = take;
        this.danger = danger;
        this.hp = hp;
        this.atk1 = atk1;
        this.atk2 = atk2;

        random = new Random();
    }

    public String getName()
    {
        return name;
    }

    public String getId()
    {
        return id;
    }

    public String getTalk()
    {
        return talk;
    }

    public String getTake()
    {
        String returnID = take.getId();
        take = null;
        return returnID;
    }

    public boolean getDanger()
    {
        return danger;
    }

    public int getHP()
    {
        return hp;
    }

    public int getAtk()
    {
        return random.nextInt(atk1, atk2);
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public void setTalk(String talk)
    {
        this.talk = talk;
    }

    public void setTake(Item take)
    {
        this.take = take;
    }

    public void setHP(int hp)
    {
        this.hp = hp;
    }

    public void setAtks(int atk1, int atk2)
    {
        this.atk1 = atk1;
        this.atk2 = atk2;
    }

    public String toString()
    {
        return "A " + name + " with ID=" + id;
    }
}

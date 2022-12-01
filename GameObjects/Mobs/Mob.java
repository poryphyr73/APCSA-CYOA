package GameObjects.Mobs;
import GameObjects.GameObject;
import GameObjects.Items.*;

public class Mob extends GameObject
{
    private String name;
    private int id;
    private String talk;
    private Item take;
    private int hp;
    private int atk1;
    private int atk2;

    public Mob(String name, int id, int hp, int atk1, int atk2)
    {
        this.name = name;
        this.id = id;
        this.hp = hp;
        this.atk1 = atk1;
        this.atk2 = atk2;
    }

    public String getName()
    {
        return name;
    }

    public int getId()
    {
        return id;
    }

    public int getHP()
    {
        return hp;
    }

    public int getAtk()
    {
        return atk1;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setId(int id)
    {
        this.id = id;
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
}

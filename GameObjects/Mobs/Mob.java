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
    private int atk;

    public Mob(String name, int id, int hp, int atk)
    {
        this.name = name;
        this.id = id;
        this.hp = hp;
        this.atk = atk;
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
        return atk;
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

    public void setAtk(int atk)
    {
        this.atk = atk;
    }
}

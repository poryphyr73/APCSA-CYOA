package GameObjects.Mobs;

public class Enemy extends Mob
{
    public Enemy(String name, int id, int hp, int atk1, int atk2)
    {
        super(name, id, hp, atk1, atk2);
    }

    public String getName()
    {
        return super.getName();
    }

    public int getId()
    {
        return super.getId();
    }

    public int getHP()
    {
        return super.getHP();
    }

    public int getAtk()
    {
        return super.getAtk();
    }

    public void setName(String name)
    {
        super.setName(name);
    }

    public void setId(int id)
    {
        super.setId(id);
    }

    public void setHP(int hp)
    {
        super.setHP(hp);
    }

    public void setAtks(int atk1, int atk2)
    {
        super.setAtks(atk1, atk2);
    }
}

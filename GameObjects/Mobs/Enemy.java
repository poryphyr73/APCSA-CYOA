package GameObjects.Mobs;

public class Enemy extends Mob
{
    public Enemy(String name, int id, int hp, int atk)
    {
        super(name, id, hp, atk);
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

    public void setAtk(int atk)
    {
        super.setAtk(atk);
    }
}

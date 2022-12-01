package GameObjects.Mobs;
import GameObjects.Items.*;;

public class Enemy extends Mob
{
    public Enemy(String name, String id, String talk, Item take, int hp, int atk1, int atk2)
    {
        super(name, id, talk, take, true, hp, atk1, atk2);
    }

    public String getName()
    {
        return super.getName();
    }

    public String getId()
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

    public void setId(String id)
    {
        super.setId(id);
    }

    public void setTalk(String talk)
    {
        super.setTalk(talk);
    }

    public void setTake(Item take)
    {
        super.setTake(take);
    }

    public void setHP(int hp)
    {
        super.setHP(hp);
    }

    public void setAtks(int atk1, int atk2)
    {
        super.setAtks(atk1, atk2);
    }

    public String toString()
    {
        return super.toString();
    }
}

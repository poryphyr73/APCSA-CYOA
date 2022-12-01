package GameObjects.Items;

public class Consumable extends Item
{
    public Consumable(String name, String id, int buff)
    {
        super(name, id, buff);
    }

    public String getName()
    {
        return super.getName();
    }

    public String getId()
    {
        return super.getId();
    }

    public int getBuff()
    {
        return super.getBuff();
    }

    public void setName(String name)
    {
        super.setName(name);
    }

    public void setId(String id)
    {
        super.setId(id);
    }

    public void setBuff(int buff)
    {
        super.setBuff(buff);
    }
}

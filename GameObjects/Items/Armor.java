package GameObjects.Items;

public class Armor extends Item
{
    public Armor(String name, int id, int buff)
    {
        super(name, id, buff);
    }

    public String getName()
    {
        return super.getName();
    }

    public int getId()
    {
        return super.getId();
    }

    public int getBuff()
    {
        return super.getBuff();
    }

    public void setName(String name)
    {
        super.setName(name);;
    }

    public void setId(int id)
    {
        super.setId(id);;
    }

    public void setBuff(int buff)
    {
        super.setBuff(buff);
    }
}

package GameObjects.Items;

import GameObjects.GameObject;

public class Item extends GameObject
{
    private String name;
    private int id;
    private int buff;

    public Item(String name, int id, int buff)
    {
        this.name = name;
        this.id = id;
        this.buff = buff;
    }

    public String getName()
    {
        return name;
    }

    public int getId()
    {
        return id;
    }

    public int getBuff()
    {
        return buff;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setBuff(int buff)
    {
        this.buff = buff;
    }
}

package GameObjects.Items;

import GameObjects.GameObject;

public class Item extends GameObject
{
    private String name;
    private String id;
    private int buff;

    public Item(String name, String id, int buff)
    {
        this.name = name;
        this.id = id;
        this.buff = buff;
    }

    public String getName()
    {
        return name;
    }

    public String getId()
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

    public void setId(String id)
    {
        this.id = id;
    }

    public void setBuff(int buff)
    {
        this.buff = buff;
    }

    public String toString()
    {
        return name;
    }
}

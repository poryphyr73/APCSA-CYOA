import java.util.HashMap;

import GameObjects.Mobs.*;
import GameObjects.Items.*;

import java.util.ArrayList;

public class Node 
{
    private String nodeName;
    private String nodeData;
    private HashMap<String, ArrayList<String>> choices = new HashMap<String, ArrayList<String>>();
    private ArrayList<Mob> mobs = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Node> rooms = new ArrayList<>();

    public Node()
    {
        nodeName = "n";
        nodeData = "";
    }

    public Node(String name, String data)
    {
        nodeName = name;
        nodeData = data;
    }

    public String getName()
    {
        return nodeName;
    }

    public String getData()
    {
        return nodeData;
    }

    public void addChoiceData(String key, String value)
    {
        if(!choices.containsKey(key)) choices.put(key, new ArrayList<String>());
        choices.get(key).add("-" + value);
    }

    public String getChoiceData()
    {
        String ret = "";
        for(String key : choices.keySet())
        {
            ret += key + ":\n";
            for(String value : choices.get(key))
            {
                ret += "\t" + value;
            }
            ret += "\n";
        }
        return ret;
    }

    public String[] getChoices()
    {
        ArrayList<String> ret = new ArrayList<String>();
        for(String key : choices.keySet())
        {
            for(String value : choices.get(key))
            {
                ret.add(key + " " + value);
            }
        }
        return ret.toArray(new String[ret.size()]);
    }

    public void removeChoices(String key, String value)
    {
        if(choices.keySet().contains(key) && choices.get(key).contains(value)) 
        {
            choices.get(key).remove(value);
            if(choices.get(key).size() == 0) choices.remove(key);
        }
        else System.out.println("ERROR");
    }

    public HashMap<String, ArrayList<String>> getChoicesHash()
    {
        return choices;
    }

    public void setName(String name)
    {
        nodeName = name;
    }

    public void setData(String data)
    {
        nodeData = data;
    }

    public String toString()
    {
        String ret = "";

        ret += "-----\n";
        ret += nodeName.toUpperCase();
        ret += "\n\n";
        ret += nodeData;

        return ret;
    }
}

package Game;
import java.util.HashMap;

import GameObjects.GameObject;
import GameObjects.Items.Item;
import GameObjects.Mobs.Mob;

import java.util.ArrayList;

public class Node extends GameObject
{
    private String nodeName;
    private String nodeData;
    private HashMap<String, ArrayList<String>> choices = new HashMap<String, ArrayList<String>>();
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Mob> mobs = new ArrayList<>();

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

    /** Add a valid command choice to the Node
     * 
     * @param key The key to insert into the choices HashMap
     * @param value The value to insert into the choices HashMap at 'key'
     */
    public void addChoiceData(String key, String value)
    {
        if(!choices.containsKey(key)) choices.put(key, new ArrayList<String>());
        choices.get(key).add("-" + value);
    }

    /** Get a readable command list for player help
     * 
     * @return A readable list of options for the player
     */
    public String getChoiceData()
    {
        String ret = "";
        for(String key : choices.keySet()) // iterate through the choices and add them to a return string
        {
            ret += key + ":\n";
            for(String value : choices.get(key))
            {
                int tagIndex = value.length();
                if(value.contains("#")) tagIndex = value.indexOf("#");
                ret += "\t" + value.substring(0, tagIndex);
            }
            ret += "\n";
        }
        ret += "use:\n";
        ret += "info:\n";
        return ret;
    }

    
    /** Get the raw command data for manipulating commands
     * 
     * @return String[] A String array of the command keywords
     */
    public String[] getChoices()
    {
        ArrayList<String> ret = new ArrayList<String>();
        for(String key : choices.keySet())
        {
            for(String value : choices.get(key))
            {
                int tagIndex = value.length();
                if(value.contains("#")) tagIndex = value.indexOf("#");
                ret.add(key + " " + value.substring(0, tagIndex));
            }
        }
        return ret.toArray(new String[ret.size()]);
    }

    
    /** Remove a command from the Node's valid commands
     * 
     * @param key The command key to be removed from
     * @param value The command paramter to be removed
     */
    public void removeChoices(String key, String value)
    {
        if(choices.keySet().contains(key) && choices.get(key).contains(value)) 
        {
            choices.get(key).remove(value);
            if(choices.get(key).size() == 0) choices.remove(key);
        }
        else System.out.println("ERROR");
    }

    
    /** Remove an item from the Node's inventory
     * 
     * @param i The Item to remove
     */
    public void removeItem(Item i)
    {
        if(items.contains(i)) items.remove(i);
    }

    
    /** Remove a Mob from the Node's inventory
     * 
     * @param m The Mob to be removed
     */
    public void removeMob(Mob m)
    {
        if(mobs.contains(m)) mobs.remove(m);
    }

    
    /** Get the full raw data HashMap of every choice at the Node
     * 
     * @return HashMap<String, ArrayList<String>> String keys representing command keys and String ArrayList values containing every valid parameter for the given key
     */
    public HashMap<String, ArrayList<String>> getChoicesHash()
    {
        return choices;
    }

    
    /** Set a new name for the Node
     * 
     * @param name The new name
     */
    public void setName(String name)
    {
        nodeName = name;
    }

    public void setData(String data)
    {
        nodeData = data;
    }

    public void addObject(Item g)
    {
        items.add(g);
    }

    public void addObject(Mob m)
    {
        mobs.add(m);
    }

    public ArrayList<Mob> getMobs()
    {
        return mobs;
    }

    public ArrayList<Item> getItems()
    {
        return items;
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

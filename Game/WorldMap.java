package Game;
import java.util.ArrayList;

public class WorldMap 
{
    private Node[][] mapGrid = new Node[8][8];
    private int x, y;

    public WorldMap(int startX, int startY)
    {
        x = startX;
        y = startY;

        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                mapGrid[i][j] = new Node();
            }
        }
    }

    
    /** 
     * @param x
     * @param y
     * @return Node
     */
    public Node getNode(int x, int y)
    {
        return mapGrid[x][y];
    }

    
    /** 
     * @return Node
     */
    public Node getNode()
    {
        return mapGrid[x][y];
    }

    
    /** 
     * @param x
     * @param y
     * @return String
     */
    public String getNodeName(int x, int y)
    {
        return mapGrid[x][y].getName();
    }

    
    /** 
     * @return String
     */
    public String getNodeName()
    {
        return mapGrid[x][y].getName().toUpperCase();
    }

    
    /** 
     * @param x
     * @param y
     * @return String
     */
    public String getNodeData(int x, int y)
    {
        return mapGrid[x][y].getData();
    }

    
    /** 
     * @return String
     */
    public String getNodeData()
    {
        return mapGrid[x][y].getData();
    }

    
    /** 
     * @param x
     * @param y
     * @return String
     */
    public String getNodeChoices(int x, int y)
    {
        return mapGrid[x][y].getChoiceData();
    }

    
    /** 
     * @return String
     */
    public String getNodeChoices()
    {
        return mapGrid[x][y].getChoiceData();
    }

    
    /** 
     * @return String
     */
    public String getSurroundingNodeStates()
    {
        String ret = "";
        if(y > 0 && !mapGrid[x][y - 1].getName().equals("n")) ret += "To your north, lies " + mapGrid[x][y - 1].getName() + "\n";
        if(x < 7 && !mapGrid[x + 1][y].getName().equals("n")) ret += "To your east, lies " + mapGrid[x + 1][y].getName() + "\n";
        if(y < 7 && !mapGrid[x][y + 1].getName().equals("n")) ret += "To your south, lies " + mapGrid[x][y + 1].getName() + "\n";
        if(x > 0 && !mapGrid[x - 1][y].getName().equals("n")) ret += "To your west, lies " + mapGrid[x - 1][y].getName() + "\n";
        return ret;
    }

    
    /** 
     * @return int
     */
    public int getActiveNodeIndex()
    {
        int k = 0;
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                if(mapGrid[j][i].equals(mapGrid[x][y])) return k;
                if(!mapGrid[j][i].getName().equals("n"))k++;
            }
        }
        return 0;
    }

    
    /** 
     * @return int
     */
    public int getPositionX()
    {
        return x;
    }

    
    /** 
     * @return int
     */
    public int getPositionY()
    {
        return y;
    }

    
    /** 
     * @param x
     * @param y
     */
    public void setPosition(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    
    /** 
     * @param x
     * @param y
     * @param cur
     */
    public void setNode(int x, int y, Node cur)
    {
        mapGrid[x][y] = cur;
    }

    
    /** 
     * @param x
     * @param y
     * @param name
     */
    public void setNodeName(int x, int y, String name)
    {
        mapGrid[x][y].setName(name);
    }

    
    /** Overwrite the data (description) of a specific Node
     * 
     * @param x The x position of the desired Node
     * @param y The y position of the desired Node
     * @param data The new data
     */
    public void setNodeData(int x, int y, String data)
    {
        mapGrid[x][y].setData(data);
    }

    /** Add a command to a specific Node's command list
     * 
     * @param x The x position of the desired Node
     * @param y The y position of the desired Node
     * @param key The command key to add to the Node
     * @param value The command value to add to the key
     */
    public void addChoiceData(int x, int y, String key, String value)
    {
        mapGrid[x][y].addChoiceData(key, value);
    }

    
    /** Remove a command from the current Node's command list
     * 
     * @param key The command key to remove from
     * @param value The command value to remove
     */
    public void addChoiceData(String key, String value)
    {
        mapGrid[x][y].addChoiceData(key, value);
    }

    
    /** Remove a command from a specific Node's command list
     * 
     * @param x The x position of the desired Node
     * @param y The y position of the desired Node
     * @param key The command key to remove from
     * @param value The command value to remove
     */
    public void removeChoiceData(int x, int y, String key, String value)
    {
        mapGrid[x][y].removeChoices(key, value);
    }

    
    /** Get an ArrayList of only the valid Nodes in the map
     * 
     * @return ArrayList<Node> The List of named Nodes
     */
    public ArrayList<Node> getNamedNodes()
    {
        ArrayList<Node> ret = new ArrayList<>();
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                if(!mapGrid[j][i].getName().equals("n")) ret.add(mapGrid[j][i]);
            }
        }
        return ret;
    }

    
    /** Move the player one space in a given direction
     * 
     * @param direction The direction in which to move the character
     */
    public void move(char direction)
    {
        char[] dirs = {'n', 'e', 's', 'w'};
        for(int i = 0; i < dirs.length; i++) 
        {
            if(dirs[i] == direction)
            {
                if(i % 2 == 0) y += i == 0 ? -1 : 1;
                if(i % 2 != 0) x += i == 1 ? 1 : -1;
            }
        }
        
    }

    // Add a choice to move in each direction from each Node where another valid Node exists
    public void initializeGlobalMovementChoices()
    {
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                if(j > 0 && !mapGrid[i][j - 1].getName().equals("n")) mapGrid[i][j].addChoiceData("travel", "north");
                if(i < 7 && !mapGrid[i + 1][j].getName().equals("n")) mapGrid[i][j].addChoiceData("travel", "east");
                if(j < 7 && !mapGrid[i][j + 1].getName().equals("n")) mapGrid[i][j].addChoiceData("travel", "south");
                if(i > 0 && !mapGrid[i - 1][j].getName().equals("n")) mapGrid[i][j].addChoiceData("travel", "west");
            }
        }
        
    }

    /** 
     * @return String A graphical map of the world using [] to represent a location, __ to represent empty space, and x to represent the player's location
     */
    public String toString()
    {
        String ret = "";
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                char positionChar;
                positionChar = (j == x && i == y) ? 'x' : ' ';
                ret += (mapGrid[j][i].getName().equals("n") ? "___" : "[" + positionChar + "]" );
                ret += "\t";
            }
            ret += "\n\n";
        }
        return ret;
    }
}

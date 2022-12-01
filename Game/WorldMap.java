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

    public Node getNode(int x, int y)
    {
        return mapGrid[x][y];
    }

    public Node getNode()
    {
        return mapGrid[x][y];
    }

    public String getNodeName(int x, int y)
    {
        return mapGrid[x][y].getName();
    }

    public String getNodeName()
    {
        return mapGrid[x][y].getName().toUpperCase();
    }

    public String getNodeData(int x, int y)
    {
        return mapGrid[x][y].getData();
    }

    public String getNodeData()
    {
        return mapGrid[x][y].getData();
    }

    public String getNodeChoices(int x, int y)
    {
        return mapGrid[x][y].getChoiceData();
    }

    public String getNodeChoices()
    {
        return mapGrid[x][y].getChoiceData();
    }

    public String getSurroundingNodeStates()
    {
        String ret = "";
        if(y > 0 && !mapGrid[x][y - 1].getName().equals("n")) ret += "To your north, lies " + mapGrid[x][y - 1].getName() + "\n";
        if(x < 7 && !mapGrid[x + 1][y].getName().equals("n")) ret += "To your east, lies " + mapGrid[x + 1][y].getName() + "\n";
        if(y < 7 && !mapGrid[x][y + 1].getName().equals("n")) ret += "To your south, lies " + mapGrid[x][y + 1].getName() + "\n";
        if(x > 0 && !mapGrid[x - 1][y].getName().equals("n")) ret += "To your west, lies " + mapGrid[x - 1][y].getName() + "\n";
        return ret;
    }

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

    public int getPositionX()
    {
        return x;
    }

    public int getPositionY()
    {
        return y;
    }

    public void setPosition(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public void setNode(int x, int y, Node cur)
    {
        mapGrid[x][y] = cur;
    }

    public void setNodeName(int x, int y, String name)
    {
        mapGrid[x][y].setName(name);
    }

    public void setNodeData(int x, int y, String data)
    {
        mapGrid[x][y].setData(data);
    }

    public void addChoiceData(int x, int y, String key, String value)
    {
        mapGrid[x][y].addChoiceData(key, value);
    }

    public void addChoiceData(String key, String value)
    {
        mapGrid[x][y].addChoiceData(key, value);
    }

    public void removeChoiceData(int x, int y, String key, String value)
    {
        mapGrid[x][y].removeChoices(key, value);
    }

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

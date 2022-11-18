public class WorldMap 
{
    private Node[][] mapGrid;

    public WorldMap()
    {
        mapGrid = new Node[8][8];
    }

    public Node getNode(int x, int y)
    {
        return mapGrid[x][y];
    }

    public boolean[] getSurroundingNodeStates(int x, int y)
    {
        Node cur = mapGrid[x][y];
        boolean[] ret = new boolean[4];
        ret[0] = y > 0 && mapGrid[x][y - 1] != null;
        ret[1] = x < 7 && mapGrid[x + 1][y] != null;
        ret[2] = y < 7 && mapGrid[x][y + 1] != null;
        ret[3] = x > 0 && mapGrid[x - 1][y] != null;
        return ret;
    }

    public void setNode(int x, int y, Node cur)
    {
        mapGrid[x][y] = cur;
    }
}

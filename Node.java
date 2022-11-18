import java.util.HashMap;
import java.util.jar.Attributes.Name;

public class Node 
{
    private String nodeName;
    private HashMap<String, Event> nodeEvents = new HashMap<String, Event>();

    public Node()
    {
        nodeName = "EMPTY";
    }

    public Node(String name)
    {

    }
}

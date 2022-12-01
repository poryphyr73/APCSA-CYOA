package Commands;
import GameObjects.*;

public interface Command 
{
    public void run(GameObject target);
}

import java.util.Arrays;
import java.util.Scanner;
import java.io.FileReader;
import java.io.IOException;

public class Manager 
{
    private static Scanner kb;
    private static WorldMap map;
    private static Player p;
    private static String input;

    //global variables
    private static boolean isAlive = true;

    private static void setup()
    {
        kb = new Scanner(System.in);
        map = new WorldMap(3, 0);
        p = new Player();
        
        map.initializeGlobalMovementChoices();
        loadStartingNodeData();
    }

    public static void main(String[] args) 
    {
        setup();
        printStartingMenu();
        getText();
        
        while(isAlive)
        {
            printCurrentNodeData();
            getInput();
        }
    }

    private static void printStartingMenu()
    {
        
    }

    private static void getText()
    {
        try {
            FileReader reader = new FileReader("WorldData/nodeRooms.txt");
            String line = "";
            int character;
 
            while ((character = reader.read()) != -1) {
                while((character = reader.read()) != ';') line += (char) character;
                if(line.contains("#"))
                {
                    System.out.println(line.substring(line.indexOf("#") + 1, line.indexOf("#") + 3));
                    line = "";
                }
            }
            reader.close();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printCurrentNodeData()
    {
        System.out.println(map.getNode());

        System.out.println(map.getSurroundingNodeStates());
    }

    private static void getInput()
    {
        input = kb.nextLine();
        input.toLowerCase();

        runNodeEvents();
    }

    private static void loadStartingNodeData()
    {

        //#region ROW1
        
        map.addChoiceData(3, 0, "go", "into the fort");
        map.addChoiceData(3, 0, "attack", "the guard");
        map.addChoiceData(3, 0, "talk", "to the guard");

        //#endregion
        //#region ROW2
        
        map.addChoiceData(0, 1, "approach", "the outpost");
        map.addChoiceData(0, 1, "approach", "the guard");
        map.addChoiceData(0, 1, "attack", "the guard");

        map.addChoiceData(2, 1, "fight", "the bandit");
        map.addChoiceData(2, 1, "talk", "to the bandit");
        map.addChoiceData(2, 1, "give", "the bandit your money");
        map.removeChoiceData(2, 1, "travel", "east");
        map.removeChoiceData(2, 1, "travel", "south");

        //DEBUG
        System.out.println(map);
    }

    private static void runNodeEvents()
    {
        if(input.equals("help")) 
        {
            System.out.println(map.getNode().getChoiceData());
            getInput();
            return;
        }
        if(!Arrays.asList(map.getNode().getChoices()).contains(input))
        {
            System.out.println("Invalid input! Please try again. (Hint: Use command \"help\" when promted for input for a list of commands)");
            getInput();
            return;
        }

        if(input.contains("travel"))
        {   
            map.move(input.charAt(input.indexOf('-') + 1));
            return;
        }
    }
}

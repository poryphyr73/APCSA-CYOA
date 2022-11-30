import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

import java.io.File;
import java.io.FileNotFoundException;


public class Manager 
{
    private static Scanner kb;
    private static WorldMap map;
    private static Player p;
    private static String input;

    private static void setup()
    {
        kb = new Scanner(System.in);
        map = new WorldMap(3, 0);
        p = new Player();
        
        initializeNodes();
    }

    public static void main(String[] args) 
    {
        setup();
        printStartingMenu();
        getCoordinates();
        
        while(p.getHealth() > 0)
        {
            printCurrentNodeData();
            getInput();
        }
    }

    private static void printStartingMenu()
    {
        System.out.println("Hello! Welcome to the world of **WORKING TITLE**");
        System.out.println("\nBefore we get started adventuring, how about you tell me a little bit about yourself?");
        System.out.print("First of all, what name should the characters in this world know you by? ");
        p.setName(kb.nextLine().toUpperCase());
        System.out.println("\nWell, it's wonderful to have you with us " + p.getName() + "!");
        System.out.println("Now lets get to know more about you and your experiences.");
        
        while(p.getSkillPoints() > 0)
        {
            System.out.println("\nPick a skill to add a point to: ");
            System.out.println("\n[ STRENGTH : " + p.getStat(0) + " (1) ]\t[ DEXTERITY : " + p.getStat(1) + " (2) ]");
            System.out.println("\n[ WISDOM : " + p.getStat(2) + " (3) ]\t[ CHARISMA : " + p.getStat(3) + " (4) ]");
            int i = kb.nextInt() - 1;
            if(i >= 0 && i <= 4)
            {
                p.incrementStat(i);
                p.decrementSP();
            }
            else System.out.println("\nInvalid input: Please try again!");
        }

        System.out.println("\nGreat! Now that you've fleshed yourself out a little bit, lets get you out and into the world! Good luck out there!");
        
        //drawBannerArt();
        kb.nextLine();
    }

    private static void initializeNodes()
    {
        HashMap<Integer, String> nameCoords = getCoordinates();
        HashMap<Integer, ArrayList<String>> commands = getCommands();
        ArrayList<String> descs = getLooks();
        for(Integer key : getCoordinates().keySet())
        {
            map.setNode(key / 10, key % 10, new Node(nameCoords.get(key), ""));
        }
        map.initializeGlobalMovementChoices();
        int i = 0;
        for(Node n : map.getNamedNodes())
        {
            n.setData(descs.get(i));
            for(String cur : commands.get(i))
            {
                System.out.println(cur);
                if(cur.contains("$")) n.removeChoices(cur.substring(1, cur.indexOf("-")), cur.substring(cur.indexOf("-")));
                else n.addChoiceData(cur.substring(0, cur.indexOf("-")), cur.substring(cur.indexOf("-") + 1));
            }
            i++;
        }
    }

    private static HashMap<Integer, String> getCoordinates()
    {
        HashMap<Integer, String> coords = new HashMap<Integer, String>();
        try {
            File worldData = new File("WorldData/nodeRooms.txt");
            Scanner reader = new Scanner(worldData);
            int coordinate = 0;
            String name = "";
 
            while (reader.hasNextLine()) {
                String c = "";
                c = reader.nextLine();

                if(c.contains("xy"))
                {
                    c = c.substring(c.indexOf(":") + 1, c.indexOf(";"));
                    coordinate = (c.charAt(0) - 48) * 10 + c.charAt(1) - 48;
                }

                if(c.contains("short"))
                {
                    name = c.substring(c.indexOf(":") + 1, c.indexOf(";"));
                }

                if(!name.equals("") && coordinate != 0)
                {
                    coords.put(coordinate, name);
                }
            }
            reader.close();
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return coords;
    }

    private static ArrayList<String> getLooks()
    {
        ArrayList<String> shorts = new ArrayList<String>();
        try {
            File worldData = new File("WorldData/nodeRooms.txt");
            Scanner reader = new Scanner(worldData);
            String desc = "";
 
            while (reader.hasNextLine()) {
                String c = "";
                
                c = reader.nextLine();
                if(c.contains("look"))
                {
                    desc+=c.substring(c.indexOf(":") + 1, c.indexOf(";")) + "\n";
                }
                else if(!desc.equals(""))
                {
                    shorts.add(desc);
                    desc = "";
                }
            }
            reader.close();
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return shorts;
    }

    private static HashMap<Integer, ArrayList<String>> getCommands()
    {
        HashMap<Integer, ArrayList<String>> commands = new HashMap<Integer, ArrayList<String>>();
        try {
            File worldData = new File("WorldData/nodeRooms.txt");
            Scanner reader = new Scanner(worldData);
            int i = 0;
            ArrayList<String> comList = new ArrayList<>();
 
            while (reader.hasNextLine()) {
                String c = reader.nextLine();
                
                if(c.contains("com"))
                {
                    comList.add(c.substring(c.indexOf(":") + 1, c.indexOf(";")));
                }
                else if(c.contains("doors"))
                {
                    ArrayList<String> a = new ArrayList<>();
                    for(int j = 0; j < comList.size(); j++) a.add(comList.get(j));
                    commands.put(i, a);
                    System.out.println(commands);
                    i++;
                    comList.clear();
                }
            }
            reader.close();
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return commands;
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

        //#region ROW2

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
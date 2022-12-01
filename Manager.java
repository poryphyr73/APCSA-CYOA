import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

import java.io.File;
import java.io.FileNotFoundException;

import GameObjects.*;
import GameObjects.Items.Armor;
import GameObjects.Items.Consumable;
import GameObjects.Items.Item;
import GameObjects.Items.Weapon;
import GameObjects.Mobs.Enemy;
import GameObjects.Mobs.Mob;
import GameObjects.Mobs.NPC;
import Commands.*;


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
        HashMap<Integer, ArrayList<GameObject>> objects = getObjects();
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
                if(cur.contains("$")) n.removeChoices(cur.substring(1, cur.indexOf("-")), cur.substring(cur.indexOf("-")));
                else n.addChoiceData(cur.substring(0, cur.indexOf("-")), cur.substring(cur.indexOf("-") + 1));
            }
            for(GameObject g : objects.get(i)) n.addObject(g);
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
                else if(c.contains("mobs"))
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

    private static HashMap<Integer, ArrayList<GameObject>> getObjects()
    {
        HashMap<Integer, ArrayList<GameObject>> objects = new HashMap<Integer, ArrayList<GameObject>>();
        try {
            File worldData = new File("WorldData/nodeRooms.txt");
            Scanner reader = new Scanner(worldData);
            int i = 0;
            ArrayList<GameObject> objList = new ArrayList<>();
 
            while (reader.hasNextLine()) {
                String c = reader.nextLine();
                
                if(c.contains("obj"))
                {
                    if(c.contains("m"))
                    {
                        Mob cur = getMobData(c.substring(c.indexOf("m") + 1, c.indexOf(";")));
                        objList.add(cur);
                    }
                    else if(c.contains("i"))
                    {
                        Item cur = getItemData(c.substring(c.indexOf("i") + 1, c.indexOf(";")));
                        objList.add(cur);
                    }
                }
                else if(c.contains("key"))
                {
                    ArrayList<GameObject> a = new ArrayList<>();
                    for(int j = 0; j < objList.size(); j++) a.add(objList.get(j));
                    objects.put(i, a);
                    i++;
                    objList.clear();
                }
            }
            reader.close();
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return objects;
    }

    private static Item getItemData(String id)
    {
        System.out.println("DEBUG");
        String type = "";
        String name = "";
        int buff = 0;

        try {
            File worldData = new File("WorldData/items.txt");
            Scanner reader = new Scanner(worldData);
            int i = 0;
 
            while (reader.hasNextLine()) {
                String c = reader.nextLine();
                
                if(c.contains("id") && c.contains(id))
                {
                    reader.nextLine();
                    type = c.substring(c.indexOf(":"), c.indexOf(";"));
                    reader.nextLine();
                    name = c.substring(c.indexOf(":"), c.indexOf(";"));
                    reader.nextLine();
                    buff = Integer.parseInt(c.substring(c.indexOf(":"), c.indexOf(";")).replaceAll("[^0-9]", ""));
                    break;
                }
            }
            reader.close();
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int idInt = Integer.parseInt(id.replaceAll("[^0-9]", ""));
        if(type.equals("w")) return new Weapon(name, idInt, buff);
        if(type.equals("a")) return new Armor(name, idInt, buff);
        return new Consumable(name, idInt, buff);
    }

    private static Mob getMobData(String id)
    {
        boolean danger = false;
        String name = "";
        String talk = "";
        Item take = null;
        int[] dmgRange = new int[2];
        int hp = 0;

        try {
            File worldData = new File("WorldData/items.txt");
            Scanner reader = new Scanner(worldData);
            int i = 0;
 
            while (reader.hasNextLine()) {
                String c = reader.nextLine();
                
                if(c.contains("id") && c.contains(id))
                {
                    reader.nextLine();
                    name = c.substring(c.indexOf(":"), c.indexOf(";"));
                    reader.nextLine();
                    talk = c.substring(c.indexOf(":"), c.indexOf(";"));
                    reader.nextLine();
                    danger = c.substring(c.indexOf(":"), c.indexOf(";")).contains("1");
                    reader.nextLine();
                    take = getItemData(c.substring(c.indexOf(":"), c.indexOf(";")));
                    reader.nextLine();
                    dmgRange[0] = Integer.parseInt(c.substring(c.indexOf(":"), c.indexOf("-")).replaceAll("[^0-9]", ""));
                    dmgRange[1] = Integer.parseInt(c.substring(c.indexOf("-"), c.indexOf(";")).replaceAll("[^0-9]", ""));
                    reader.nextLine();
                    hp =  Integer.parseInt(c.substring(c.indexOf(":"), c.indexOf(";")).replaceAll("[^0-9]", ""));
                    break;
                }
            }
            reader.close();
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int idInt = Integer.parseInt(id.replaceAll("[^0-9]", ""));
        if(danger) return new Enemy(name, idInt, hp, dmgRange[0], dmgRange[1]);
        return new NPC(name, idInt, hp, dmgRange[0], dmgRange[1]);
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
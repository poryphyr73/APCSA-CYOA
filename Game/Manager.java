package Game;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Scanner;

import GameObjects.Items.Item;
import GameObjects.Mobs.Mob;

import Commands.*;

public class Manager 
{
    private static Scanner kb;
    private static WorldMap map;
    private static String input;

    private static void setup()
    {
        kb = new Scanner(System.in);
        map = new WorldMap(3, 0);
        
        initializeNodes();
    }

    public static void playerDeath()
    {

    }

    private static void printStartingMenu()
    {
        System.out.println("Hello! Welcome to the world of **WORKING TITLE**");
        System.out.println("\nBefore we get started adventuring, how about you tell me a little bit about yourself?");
        System.out.print("First of all, what name should the characters in this world know you by? ");
        Player.setName(kb.nextLine().toUpperCase());
        System.out.println("\nWell, it's wonderful to have you with us " + Player.getName() + "!");
        System.out.println("Now lets get to know more about you and your experiences.");
        
        while(Player.getSkillPoints() > 0)
        {
            System.out.println("\nPick a skill to add a point to: ");
            System.out.println("\n[ STRENGTH : " + Player.getStat(0) + " (1) ]\t[ DEXTERITY : " + Player.getStat(1) + " (2) ]");
            System.out.println("\n[ WISDOM : " + Player.getStat(2) + " (3) ]\t[ CHARISMA : " + Player.getStat(3) + " (4) ]");
            int i = kb.nextInt() - 1;
            if(i >= 0 && i <= 4)
            {
                Player.incrementStat(i);
                Player.decrementSP();
            }
            else System.out.println("\nInvalid input: Please try again!");
        }

        System.out.println("\nGreat! Now that you've fleshed yourself out a little bit, lets get you out and into the world! Good luck out there!");
        
        //drawBannerArt();
        kb.nextLine();
    }

    private static void initializeNodes()
    {
        LinkedHashMap<Integer, String> nameCoords = Initializer.getCoordinates();
        LinkedHashMap<Integer, ArrayList<String>> commands = Initializer.getCommands();
        LinkedHashMap<Integer, ArrayList<Item>> items = Initializer.getItems();
        LinkedHashMap<Integer, ArrayList<Mob>> mobs = Initializer.getMobs();
        ArrayList<String> descs = Initializer.getLooks();
        
        for(Integer key : Initializer.getCoordinates().keySet())
        {
            map.setNode(key / 10, key % 10, new Node(nameCoords.get(key), ""));
        }
        map.initializeGlobalMovementChoices();
        int i = 0;
        System.out.println(map.getNamedNodes());
        for(Node n : map.getNamedNodes())
        {
            n.setData(descs.get(i));
            for(String cur : commands.get(i))
            {
                if(cur.contains("$")) n.removeChoices(cur.substring(1, cur.indexOf("-")), cur.substring(cur.indexOf("-")));
                else n.addChoiceData(cur.substring(0, cur.indexOf("-")), cur.substring(cur.indexOf("-") + 1));
            }
            for(Item item : items.get(i)) n.addObject(item);
            for(Mob mob : mobs.get(i)) n.addObject(mob);

            i++;
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

        if(input.contains("attack"))
        {
            for(String i : map.getNode().getChoicesHash().get("attack"))
            {
                for(Mob j : map.getNode().getMobs())
                {
                    if(i.substring(i.indexOf("#") + 1).equals(j.getId())) CommandManager.attack(j);
                }
            }
            return;
        }
        
        if(input.contains("take"))
        {
            Item toRemove = null;
            String strToRemove = "";
            for(String i : map.getNode().getChoicesHash().get("take"))
            {
                for(Item j : map.getNode().getItems())
                {
                    if(i.substring(i.indexOf("#") + 1).equals(j.getId()) && i.contains(input.substring(7))) 
                    {
                        CommandManager.take(j);
                        toRemove = j;
                        strToRemove = i;
                        break;
                    }
                }
            }
            map.getNode().removeChoices("take", strToRemove);
            map.getNode().removeItem(toRemove);
            System.out.println(Player.getInventory());
            return;
        }

        for(String i : map.getNode().getChoicesHash().get("talk"))
            {
                for(Mob j : map.getNode().getMobs())
                {
                    if(i.substring(i.indexOf("#") + 1).equals(j.getId())) CommandManager.talk(j);
                }
            }
            return;
    }

    public static void main(String[] args) 
    {
        setup();
        printStartingMenu();
        
        while(Player.getHealth() > 0)
        {
            for(Mob m : map.getNode().getMobs()) if(m.getDanger()) CommandManager.attack(m);
            printCurrentNodeData();
            getInput();
        }

        System.out.println("\n--------------\nGAME OVER :(\n--------------");
    }
}
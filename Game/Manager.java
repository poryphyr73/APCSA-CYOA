package Game;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

import GameObjects.*;
import GameObjects.Mobs.Mob;

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
        
        while(p.getHealth() > 0)
        {
            for(Mob m : map.getNode().getMobs())
            {
                if(m.getDanger()) CommandManager.attack(m);
            }
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
        HashMap<Integer, String> nameCoords = Initializer.getCoordinates();
        HashMap<Integer, ArrayList<String>> commands = Initializer.getCommands();
        HashMap<Integer, ArrayList<GameObject>> objects = Initializer.getObjects();
        ArrayList<String> descs = Initializer.getLooks();
        
        for(Integer key : Initializer.getCoordinates().keySet())
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
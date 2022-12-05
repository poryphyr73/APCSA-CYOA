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
    // Game System Objects
    private static Scanner kb;
    private static WorldMap map;
    private static String input;

    // Achievements
    private static boolean gotSalts;
    private static boolean gotLegendaryArmor;
    private static boolean gotLegendaryWeapon;

    // Win Variables
    private static boolean kingIsDead;
    private static boolean orcIsDead;
    private static boolean lordIsDead;

    // Set up the variables that the game needs to run
    private static void setup()
    {
        kb = new Scanner(System.in);
        map = new WorldMap(2, 3);
        
        initializeNodes();
    }

    // Print out ASCII art for the game title screen
    private static void printTitle()
    {
        System.out.println("   ____  _____   _____  _____            _   _ _____     ____  _      _____ _____          _____   _____ _    _ _____ ______  _____ ");
        System.out.println("  / __ \\|  __ \\ / ____|/ ____|     /\\   | \\ | |  __ \\   / __ \\| |    |_   _/ ____|   /\\   |  __ \\ / ____| |  | |_   _|  ____|/ ____|");
        System.out.println(" | |  | | |__) | |    | (___      /  \\  |  \\| | |  | | | |  | | |      | || |  __   /  \\  | |__) | |    | |__| | | | | |__  | (___  ");
        System.out.println(" | |  | |  _  /| |     \\___ \\    / /\\ \\ | . ` | |  | | | |  | | |      | || | |_ | / /\\ \\ |  _  /| |    |  __  | | | |  __|  \\___ \\ ");
        System.out.println(" | |__| | | \\ \\| |____ ____) |  / ____ \\| |\\  | |__| | | |__| | |____ _| || |__| |/ ____ \\| | \\ \\| |____| |  | |_| |_| |____ ____) |");
        System.out.println("  \\____/|_|  \\_\\\\_____|_____/  /_/    \\_\\_| \\_|_____/   \\____/|______|_____\\_____/_/    \\_\\_|  \\_\\\\_____|_|  |_|_____|______|_____/\n ");
    }

    // Show an introduction, set the player's name, and assign the Player stats
    private static void printStartingMenu()
    {
        printTitle();
        System.out.println("Hello! Welcome to the world of Orcs and Oligarchies!");
        System.out.println("\nBefore we get started adventuring, how about you tell me a little bit about yourself?");
        System.out.print("First of all, what name should the characters in this world know you by? ");
        Player.setName(kb.nextLine().toUpperCase());
        System.out.println("\nWell, it's wonderful to have you with us " + Player.getName() + "!");
        System.out.println("Now lets get to know more about you and your experiences.");
        
        while(Player.getSkillPoints() > 0)
        {
            System.out.println("\nPick a skill to add a point to: ");
            System.out.println("\n[ STRENGTH : " + Player.getStat(0) + " (1) ]\t[ DEXTERITY : " + Player.getStat(1) + " (2) ]");
            int i = kb.nextInt() - 1;
            if(i >= 0 && i <= 2)
            {
                Player.incrementStat(i);
                Player.decrementSP();
            }
            else System.out.println("\nInvalid input: Please try again!");
        }

        System.out.println("\nGreat! Now that you've fleshed yourself out a little bit, lets get you out and into the world! Good luck out there!");
        
        kb.nextLine();

        printBackstory();
    }

    // Print out the backstory of the player character
    private static void printBackstory()
    {
        System.out.println("You awaken in your parents old farmhouse. They have long since passed, but you still tend to the land as if it were your own.");
        System.out.println("Recently, however, there has been a takeover of the land by an orcish tribe in the southwest of the country.");
        System.out.println("They are notorious for pillaging and rioting.");
        System.out.println("You decide to take up adventuring to see if you can do some good in the world.");
        System.out.println("A good place to start might be the Seaside City in the northeast or the Capital in the Southeast.");
        System.out.println("Who knows where your adventures might take you!");
        System.out.println("-----------------\n");
    }

    // Fill the WorldMap map with Nodes and assign them appropriate from Initializer
    private static void initializeNodes()
    {
        // all the needed world data
        LinkedHashMap<Integer, String> nameCoords = Initializer.getCoordinates();
        LinkedHashMap<Integer, ArrayList<String>> commands = Initializer.getCommands();
        LinkedHashMap<Integer, ArrayList<Item>> items = Initializer.getItems();
        LinkedHashMap<Integer, ArrayList<Mob>> mobs = Initializer.getMobs();
        ArrayList<String> descs = Initializer.getLooks();
        
        for(Integer key : Initializer.getCoordinates().keySet())
        {
            map.setNode(key / 10, key % 10, new Node(nameCoords.get(key), "")); // initialize names at coordinates in WorldMap map
        }
        map.initializeGlobalMovementChoices(); // give movement commands to each Node based on what's around them
        int i = 0;
        for(Node n : map.getNamedNodes()) // get every named Node in the map WorldMap and give them commands, data, items, mobs
        {
            n.setData(descs.get(i));
            for(String cur : commands.get(i))
            {
                n.addChoiceData(cur.substring(0, cur.indexOf("-")), cur.substring(cur.indexOf("-") + 1));
            }
            for(Item item : items.get(i)) n.addObject(item);
            for(Mob mob : mobs.get(i)) n.addObject(mob);
            i++;
        }
    }

    // Get all the information for a current Node and the states of the Nodes around them
    private static void printCurrentNodeData()
    {
        System.out.println(map.getNode());

        System.out.println(map.getSurroundingNodeStates());
    }

    // Get the user's input in lowercase and run it for the Node
    private static void getInput()
    {
        input = kb.nextLine();
        input.toLowerCase();

        runNodeEvents();
    }

    /** 
     * @return WorldMap A copy of the WorldMap object current to the Manager
     */
    public static WorldMap getMap()
    {
        return map;
    }

    // Runs the commands system for the current node
    private static void runNodeEvents()
    {
        if(input.equals("help")) // give the player a help menu; list of available commands
        {
            System.out.println(map.getNode().getChoiceData());
            getInput();
            return;
        }
        if(input.equals("use")) // use an item in the player inventory. Command not native to node, called before the error condition
        {
            Player.useItem();
            return;
        }
        if(input.equals("info")) // get the player's current stats and equipment
        {
            System.out.println("Strength: " + Player.getStat(0) + ", Dexterity: " + Player.getStat(1));
            System.out.println("Weapon: " + Player.getCurrentWeapon() + ", Armor" + Player.getCurrentArmor());
            return;
        }
        if(!Arrays.asList(map.getNode().getChoices()).contains(input)) // show an error if the input is unexpected/invalid
        {
            System.out.println("Invalid input! Please try again. (Hint: Use command \"help\" when prompted for input for a list of commands)");
            getInput();
            return;
        }
        if(input.contains("travel")) // move to a neighbouring Node
        {   
            map.move(input.charAt(input.indexOf('-') + 1));
            return;
        }

        if(input.contains("attack")) // attack a Mob in the current Node
        {
            for(String i : map.getNode().getChoicesHash().get("attack"))
            {
                for(Mob j : map.getNode().getMobs())
                {
                    if(i.substring(i.indexOf("#") + 1).equals(j.getId())) 
                    {
                        CommandManager.attack(j);
                        break;
                    }
                }
            }
            return;
        }
        
        if(input.contains("take")) // take an Item from the current Node
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
            return;
        }

        for(String i : map.getNode().getChoicesHash().get("talkto"))
        {
            for(Mob j : map.getNode().getMobs())
            {
                if(i.substring(i.indexOf("#") + 1).equals(j.getId())) CommandManager.talk(j);
            }
        }
        return;
    }

    // Prints the win message for Orc death conditions
    private static void showWinScreen()
    {
        System.out.println("You defeated the orc king and saved the country from peril!");
    }

    // Prints the loss message for good NPC death conditions
    private static void showLossScreen()
    {
        System.out.println("You killed the country's only \"good\" rulers and doomed the country.");
    }
    
    // Prints the loss message for player death conditions
    public static void showDeathScreen()
    {
        System.out.println("You met a terrible fate in battle and perished!");

        if(kingIsDead && lordIsDead) 
        {
            showLossScreen();
            return;
        }
        if(orcIsDead) showWinScreen();
    }

    /** 
     * @return boolean The gamestate of win or loss based on if the player killed both good NPCs or the Evil one
     */
    private static void checkWinConditions()
    {
        boolean tempKing = true;
        boolean tempLord = true;
        boolean tempOrc = true;
        for(Mob checkKing : map.getNode(5, 7).getMobs()) if(checkKing.getId().equals("990")) tempKing = false;
        for(Mob checkLord : map.getNode(7, 1).getMobs()) if(checkLord.getId().equals("100")) tempLord = false;
        for(Mob checkOrc : map.getNode(0, 6).getMobs()) if(checkOrc.getId().equals("999")) tempOrc = false;
        kingIsDead = tempKing;
        lordIsDead = tempLord;
        orcIsDead = tempOrc;
    }

    // Set the achievement booleans according to their respective requirements
    private static void checkAchievementConditions()
    {
        for(Item item : Player.getInventory().keySet())
        {
            if(item.getId().equals("099")) gotLegendaryWeapon = true;
            if(item.getId().equals("999")) gotLegendaryArmor = true;
            if(item.getId().equals("103") && Player.getInventory().get(item) >= 3) gotSalts = true;
        }
    }

    // Shows achievement messages based on the true achievement booleans 
    private static void showAchievements()
    {
        if(gotSalts) System.out.println("You found all the salt in the world! You had a monopoly on salt and didnt eat it! (Salt is a trophy item)");
        if(gotLegendaryArmor) System.out.println("You found the legendary armour! You were looking pretty spiffy!");
        if(gotLegendaryWeapon) System.out.println("You found the legenday weapon! You were probably feeling pretty sharp!");
        if(gotSalts && gotLegendaryArmor && gotLegendaryWeapon) System.out.println("You got all of the achievements! Nice job Mr. Perfectionist!");
        else System.out.println("There's still more to do! Consider playing again!");
    }

    // Manages game progression on new Manager creation
    public Manager(String[] args) 
    {
        setup();
        printStartingMenu();
        
        while(true)
        {
            boolean hasEncountered = false;
            for(int i = 0; i < map.getNode().getMobs().size(); i++) if(map.getNode().getMobs().get(i).getDanger() && Player.getHealth() > 0) // run all hostile encounters
            {
                CommandManager.attack(map.getNode().getMobs().get(i));
                hasEncountered = true;
            }
            if(Player.getHealth() <= 0) break; // kill the player if his health is lower than one
            if(hasEncountered == false) // give the player free movement if not in a battle
            {
                printCurrentNodeData();
                getInput();
                checkAchievementConditions();
                checkWinConditions();
            }
        }
        // Show everything that happens post-mortem
        showDeathScreen();
        showAchievements();

        kb.close();
    }
}
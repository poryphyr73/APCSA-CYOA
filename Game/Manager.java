package Game;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Scanner;

import javax.swing.plaf.basic.BasicTreeUI.TreeIncrementAction;

import GameObjects.Items.Item;
import GameObjects.Mobs.Mob;

import Commands.*;

public class Manager 
{
    private static Scanner kb;
    private static WorldMap map;
    private static String input;

    //Achievements
    private static boolean gotSalts;
    private static boolean gotLegendaryArmor;
    private static boolean gotLegendaryWeapon;

    //Win Variables
    private static boolean kingIsDead;
    private static boolean orcIsDead;
    private static boolean lordIsDead;

    private static void setup()
    {
        kb = new Scanner(System.in);
        map = new WorldMap(2, 3);
        
        initializeNodes();
    }

    private static void printTitle()
    {
        System.out.println("   ____  _____   _____  _____            _   _ _____     ____  _      _____ _____          _____   _____ _    _ _____ ______  _____ ");
        System.out.println("  / __ \\|  __ \\ / ____|/ ____|     /\\   | \\ | |  __ \\   / __ \\| |    |_   _/ ____|   /\\   |  __ \\ / ____| |  | |_   _|  ____|/ ____|");
        System.out.println(" | |  | | |__) | |    | (___      /  \\  |  \\| | |  | | | |  | | |      | || |  __   /  \\  | |__) | |    | |__| | | | | |__  | (___  ");
        System.out.println(" | |  | |  _  /| |     \\___ \\    / /\\ \\ | . ` | |  | | | |  | | |      | || | |_ | / /\\ \\ |  _  /| |    |  __  | | | |  __|  \\___ \\ ");
        System.out.println(" | |__| | | \\ \\| |____ ____) |  / ____ \\| |\\  | |__| | | |__| | |____ _| || |__| |/ ____ \\| | \\ \\| |____| |  | |_| |_| |____ ____) |");
        System.out.println("  \\____/|_|  \\_\\\\_____|_____/  /_/    \\_\\_| \\_|_____/   \\____/|______|_____\\_____/_/    \\_\\_|  \\_\\\\_____|_|  |_|_____|______|_____/\n ");
    }

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
        
        //drawBannerArt();
        kb.nextLine();

        printBackstory();
    }

    private static void printBackstory()
    {
        System.out.println("You awaken in your parents old farmhouse. They have long since passed, but you still tend to the land as if it were your own.");
        System.out.println("Recently, however, there has been a takeover of the land by an orcish tribe in the southwest of the country.");
        System.out.println("They are notorious for pillaging and rioting.");
        System.out.println("You decide to take up adventuring to see if you can do some good in the world.");
        System.out.println("A good place to start might be the Seaside City in the northeast or the Capital in the Southeast.");
        System.out.println("Who knows where your adventures might take you!\n");
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
        for(Node n : map.getNamedNodes())
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

    public static WorldMap getMap()
    {
        return map;
    }

    private static void runNodeEvents()
    {
        
        if(input.equals("help")) 
        {
            System.out.println(map.getNode().getChoiceData());
            getInput();
            return;
        }
        if(input.equals("use"))
        {
            Player.useItem();
            return;
        }
        if(input.equals("info"))
        {
            System.out.println("Strength: " + Player.getStat(0) + ", Dexterity: " + Player.getStat(1));
            System.out.println("Weapon: " + Player.getCurrentWeapon() + ", Armor" + Player.getCurrentArmor());
            return;
        }
        if(!Arrays.asList(map.getNode().getChoices()).contains(input))
        {
            System.out.println("Invalid input! Please try again. (Hint: Use command \"help\" when prompted for input for a list of commands)");
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

    private static void showWinScreen()
    {
        System.out.println("You defeated the orc king and saved the country from peril!");
    }

    private static void showLossScreen()
    {
        System.out.println("You killed the country's only \"good\" rulers and doomed the country.");
    }

    public static void showDeathScreen()
    {
        System.out.println("You met a terrible fate in battle and perished!");
    }

    private static boolean checkWinConditions()
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
        if(orcIsDead) showWinScreen();
        if(lordIsDead && kingIsDead) showLossScreen();
        
        return false;
    }

    private static void checkAchievementConditions()
    {
        for(Item item : Player.getInventory().keySet())
        {
            if(item.getId().equals("099")) gotLegendaryWeapon = true;
            if(item.getId().equals("999")) gotLegendaryArmor = true;
            if(item.getId().equals("103") && Player.getInventory().get(item) == 3) gotSalts = true;
        }
    }

    private static void showAchievements()
    {
        if(gotSalts) System.out.println("You found all the salt in the world! You had a monopoly on salt and didnt eat it! (Salt is a trophy item)");
        if(gotLegendaryArmor) System.out.println("You found the legendary armour! You were looking pretty spiffy!");
        if(gotLegendaryWeapon) System.out.println("You found the legenday weapon! You were probably feeling pretty sharp!");
        if(gotSalts && gotLegendaryArmor && gotLegendaryWeapon) System.out.println("You got all of the achievements! Nice job Mr. Perfectionist!");
        else System.out.println("There's still more to do! Consider playing again!");
    }

    public Manager(String[] args) 
    {
        setup();
        printStartingMenu();
        
        while(true)
        {
            boolean hasEncountered = false;
            for(int i = 0; i < map.getNode().getMobs().size(); i++) if(map.getNode().getMobs().get(i).getDanger() && Player.getHealth() > 0) 
            {
                CommandManager.attack(map.getNode().getMobs().get(i));
                hasEncountered = true;
            }
            if(Player.getHealth() <= 0) break;
            if(hasEncountered == false)
            {
                printCurrentNodeData();
                getInput();
                checkAchievementConditions();
                checkWinConditions();
            }
        }
        showDeathScreen();
        showAchievements();
    }
}
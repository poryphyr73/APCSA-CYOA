import java.util.Arrays;
import java.util.Scanner;

import javax.swing.plaf.synth.Region;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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

        loadStartingNodes();
        map.initializeGlobalMovementChoices();
        loadStartingNodeData();
    }

    public static void main(String[] args) 
    {
        setup();
        printStartingMenu();
        
        while(1==1)
        {
            printCurrentNodeData();
            getInput();
        }
    }

    private static void printStartingMenu()
    {
        
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

    private static void loadStartingNodes()
    {
        //#region ROW1
        
        map.setNodeName(3, 0, "the Imperial Fort"); //XT?/

        //#endregion
        //#region ROW2
        
        map.setNodeName(0, 1, "the Northern Orcish Outpost"); //X

        map.setNodeName(2, 1, "a dirt road"); //X

        map.setNodeName(3, 1, "the Northville market"); //?

        map.setNodeName(4, 1, "the road to the City Port");

        map.setNodeName(5, 1, "the City Port");

        map.setNodeName(6, 1, "the bridge into Seaside City");

        map.setNodeName(7, 1, "Seaside City"); //?

        //#endregion
        //#region ROW3

        map.setNodeName(0, 2, "the Valley of Bandits"); //XT

        map.setNodeName(2, 2, "a dirt road");

        map.setNodeName(3, 2, "the Northville town square"); //?

        map.setNodeName(4, 2, "a dirt road"); //X

        map.setNodeName(5, 2, "a dirt road");

        //#endregion
        //#region ROW4

        map.setNodeName(0, 3, "the Monastery"); //?

        map.setNodeName(1, 3, "the mountain passage"); //X
        
        map.setNodeName(2, 3, "your parents farm"); //O

        map.setNodeName(3, 3, "the Northville orchard");

        map.setNodeName(4, 3, "a dirt road");

        map.setNodeName(5, 3, "a crossroads");

        map.setNodeName(6, 3, "the Easton mining settlement"); //?

        map.setNodeName(7, 3, "the Easton mines"); //XT

        //#endregion
        //#region ROW5

        map.setNodeName(1, 4, "a witch's tower"); //?

        map.setNodeName(2, 4, "a dirt road");

        map.setNodeName(3, 4, "the Haunted Bog"); //X

        map.setNodeName(5, 4, "Riverland Village");

        map.setNodeName(6, 4, "a wizard's tower"); //?

        //#endregion
        //#region ROW6

        map.setNodeName(1, 5, "the border to the Orcish Territory"); //X

        map.setNodeName(2, 5, "the road to the border");

        map.setNodeName(5, 5, "the troll's bridge"); //X

        //#endregion
        //#region ROW7

        map.setNodeName(0, 6, "the Orcish Capital");

        map.setNodeName(1, 6, "the Wasteland");
        
        map.setNodeName(3, 6, "the Sacred Temple");
        
        map.setNodeName(4, 6, "a broken bridge");

        map.setNodeName(5, 6, "Castle Town");

        //#endregion
        //#region ROW8

        map.setNodeName(5, 7, "the Imperial Capital");

        //#endregion
    }

    private static void loadStartingNodeData()
    {

        //#region ROW1
        
        map.setNodeData(3, 0, "You stand at the door of a large military fort that should be inhabited by militia of the local imperial city. " +
            "The trouble is, a local band of orcs from an encampment in the souther reaches of the state has taken control. " + 
            "A guard perched atop a tower yells down to you: " + "\n\n\t" +
            "\"WHO GOES THERE? TRESPASSERS BE WARNED, ALL UNAUTHORIZED PERSONS WILL BE KILLED UPON ENTRY\"\n\n" +
            "He is the only guard who can currently see you.");
        map.addChoiceData(3, 0, "go", "into the fort");
        map.addChoiceData(3, 0, "attack", "the guard");
        map.addChoiceData(3, 0, "talk", "to the guard");

        //#endregion
        //#region ROW2
        
        map.setNodeData(0, 0, "You stand outside of an outpost governed by the colony of orcs in the south-western part of the state. " + 
            "The place is filled to the brim with ugly green creatures weilding heavy weaponry like crossbows and maces, a bloodthirsty look in each of their eyes. " +
            "You stand just far south enough that the orcs cannot see you, but you can see their desolate camp of smog and mud, and a nearby orc smoking a pipe on a log. ");
        map.addChoiceData(0, 1, "approach", "the outpost");
        map.addChoiceData(0, 1, "approach", "the guard");
        map.addChoiceData(0, 1, "attack", "the guard");

        map.setNodeName(2, 1, "a dirt road"); //X
        map.setNodeData(2, 1, "A seemingly empty and boring section of road lays before you. " +
            "As you wander down the empty road, you hear a voice call out to you:\n\n\t" +
            "\"You there! Empty your pockets or pay with your life!\"\n\n" +
            "A bandit stands behind you with a sword drawn. He looks to have seen some things in his day of banditing. His threats seem serious.");
        map.addChoiceData(2, 1, "fight", "the bandit");
        map.addChoiceData(2, 1, "talk", "to the bandit");
        map.addChoiceData(2, 1, "give", "the bandit your money");
        map.removeChoiceData(2, 1, "travel", "east");
        map.removeChoiceData(2, 1, "travel", "south");

        map.setNodeName(3, 1, "the Northville market"); //?
        map.setNodeData(3, 1, "");

        map.setNodeName(4, 1, "the road to the City Port");
        map.setNodeData(4, 1, "");

        map.setNodeName(5, 1, "the City Port");
        map.setNodeData(5, 1, "");

        map.setNodeName(6, 1, "the bridge into Seaside City");
        map.setNodeData(6, 1, "");

        map.setNodeName(7, 1, "Seaside City"); //?
        map.setNodeData(7, 1, "");

        //#endregion
        //#region ROW3

        map.setNodeName(0, 2, "the Valley of Bandits"); //XT
        map.setNodeData(0, 2, "");

        map.setNodeName(2, 2, "a dirt road");
        map.setNodeData(2, 2, "");

        map.setNodeName(3, 2, "the Northville town square"); //?
        map.setNodeData(3, 2, "");

        map.setNodeName(4, 2, "a dirt road"); //X
        map.setNodeData(4, 2, "");

        map.setNodeName(5, 2, "a dirt road");
        map.setNodeData(5, 2, "");

        //#endregion
        //#region ROW4

        map.setNodeName(0, 3, "the Monastery"); //?
        map.setNodeData(0, 3, "");

        map.setNodeName(1, 3, "the mountain passage"); //X
        map.setNodeData(1, 3, "");
        
        map.setNodeName(2, 3, "your parents farm"); //O
        map.setNodeData(2, 3, "");

        map.setNodeName(3, 3, "the Northville orchard");
        map.setNodeData(3, 3, "");

        map.setNodeName(4, 3, "a dirt road");
        map.setNodeData(4, 3, "");

        map.setNodeName(5, 3, "a crossroads");
        map.setNodeData(5, 3, "");

        map.setNodeName(6, 3, "the Easton mining settlement"); //?
        map.setNodeData(6, 3, "");

        map.setNodeName(7, 3, "the Easton mines"); //XT
        map.setNodeData(7, 3, "");

        //#endregion
        //#region ROW5

        map.setNodeName(1, 4, "a witch's tower"); //?
        map.setNodeData(1, 4, "");

        map.setNodeName(2, 4, "a dirt road");
        map.setNodeData(2, 4, "");

        map.setNodeName(3, 4, "the Haunted Bog"); //X
        map.setNodeData(3, 4, "");

        map.setNodeName(5, 4, "Riverland Village");
        map.setNodeData(5, 4, "");

        map.setNodeName(6, 4, "a wizard's tower"); //?
        map.setNodeData(6, 4, "");

        //#endregion
        //#region ROW6

        map.setNodeName(1, 5, "the border to the Orcish Territory"); //X
        map.setNodeData(1, 5, "");

        map.setNodeName(2, 5, "the road to the border");
        map.setNodeData(2, 5, "");

        map.setNodeName(5, 5, "the troll's bridge"); //X
        map.setNodeData(5, 5, "");

        //#endregion
        //#region ROW7

        map.setNodeName(0, 6, "the Orcish Capital");
        map.setNodeData(0, 6, "");

        map.setNodeName(1, 6, "the Wasteland");
        map.setNodeData(1, 6, "");
        
        map.setNodeName(3, 6, "the Sacred Temple");
        map.setNodeData(3, 6, "");
        
        map.setNodeName(4, 6, "a broken bridge");
        map.setNodeData(4, 6, "");

        map.setNodeName(5, 6, "Castle Town");
        map.setNodeData(5, 6, "");

        //#endregion
        //#region ROW8

        map.setNodeName(5, 7, "the Imperial Capital");
        map.setNodeData(5, 7, "");

        //#endregion

        //use map.setNodeName and map.setNodeData

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

        //#region CALLING EXTRANEOUS METHODS
        int x = map.getPositionX();
        int y = map.getPositionY();

        if(y == 0)
        {
            if(x == 3) imperialFort();
        }
        if(y == 1)
        {
            if(x == 0) orcishOutpost();
            if(x == 2) northBanditRoad();
            if(x == 3) market();
            if(x == 4) road41();
            if(x == 5) port();
            if(x == 6) portBridge();
            if(x == 7) seaside();
        }
        if(y == 2)
        {
            if(x == 0) banditValley();
            if(x == 2) road22();
            if(x == 3) townSquare();
            if(x == 4) eastBanditRoad();
            if(x == 5) road52();
        }
        if(y == 3)
        {
            if(x == 0) monastery();
            if(x == 1) mountainPass();
            if(x == 2) home();
            if(x == 3) orchard();
            if(x == 4) road43();
            if(x == 5) crossroads();
            if(x == 6) miningTown();
            if(x == 7) mines();
        }
        if(y == 4)
        {
            if(x == 1) witchTower();
            if(x == 2) road24();
            if(x == 3) bog();
            if(x == 5) riverland();
            if(x == 6) wizardTower();
        }
        if(y == 5)
        {
            if(x == 1) border();
            if(x == 2) road25();
            if(x == 5) road55();
        }
        if(y == 6)
        {
            if(x == 0) orcCapital();
            if(x == 1) road16();
            if(x == 3) temple();
            if(x == 4) brokenBridge();
            if(x == 5) road56();
        }
        if(y == 7)
        {
            if(x == 5) castle();
        }
        //#endregion
    }

    private static void imperialFort()
    {
        boolean guardKilled;
        boolean fortRaided;
        boolean treasureTaken;
        boolean questTaken;
        boolean questFinished;
    }

    private static void orcishOutpost()
    {
        
    }

    private static void witchTower()
    {

    }

    private static void northBanditRoad()
    {

    }

    private static void market()
    {

    }

    private static void road41()
    {

    }

    private static void port(){}

    private static void portBridge(){}

    private static void seaside(){}

    private static void banditValley(){}

    private static void road22(){}

    private static void townSquare(){}

    private static void eastBanditRoad(){}

    private static void road52(){}

    private static void monastery(){}

    private static void mountainPass(){}

    private static void home(){}

    private static void orchard(){}

    private static void road43(){}

    private static void crossroads(){}

    private static void miningTown(){}
    
    private static void mines(){}

    private static void road24(){}

    private static void bog(){}

    private static void riverland(){}

    private static void wizardTower(){}

    private static void border(){}

    private static void road25(){}

    private static void road55(){}

    private static void orcCapital(){}

    private static void road16(){}

    private static void temple(){}

    private static void brokenBridge(){}
    
    private static void road56(){}

    private static void castle(){}
}

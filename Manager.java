import java.security.DrbgParameters.NextBytes;
import java.util.ArrayList;
import java.util.Scanner;

public class Manager 
{
    private Scanner kb = new Scanner(System.in);

    //player variables
    private String name;
    private int health, maxHealth = 10;
    private int[] stats = new int[4]; //str, dex, wis, cha
    private ArrayList<Item> inventory = new ArrayList<Item>();
    private int skillPoints = 10;

    //global variables
    private boolean isAlive = true;

        //farmHouse
        private boolean isIntroScene = true, takenFood, takenSword;

    public Manager() 
    {
        while(true)
        {
            characterSetup();

            System.out.println("Would you like to play again? (y/n)");
            if(getLowInput().equals("n")) break;
        }
    }

    private void characterSetup()
    {
        //insert a title with ascii art
        drawBannerArt();

        System.out.println("Hello! Welcome to the world of **WORKING TITLE**");
        System.out.println("\nBefore we get started adventuring, how about you tell me a little bit about yourself?");
        System.out.print("First of all, what name should the characters in this world know you by? ");
        name = kb.nextLine().toUpperCase();

        System.out.println("\nWell, it's wonderful to have you with us " + name + "!");
        System.out.println("Now lets get to know more about you and your experiences.");
        
        while(skillPoints > 0)
        {
            System.out.println("\nPick a skill to add a point to: ");

            System.out.println("\n[ STRENGTH : " + stats[0] + " (1) ]\t[ DEXTERITY : " + stats[1] + " (2) ]");
            System.out.println("\n[ WISDOM : " + stats[2] + " (3) ]\t[ CHARISMA : " + stats[3] + " (4) ]");
            int i = kb.nextInt() - 1;

            if(i >= 0 && i <= 4)
            {
                stats[i]++;
                skillPoints--;
            }
            else System.out.println("\nInvalid input: Please try again!");
        }

        System.out.println("\nGreat! Now that you've fleshed yourself out a little bit, lets get you out and into the world!");
        drawBannerArt();
        farmHouse();
    }

    private void farmHouse()
    {
        if(isIntroScene)
        {
            System.out.println("\t\"" + name.toLowerCase() + "?" + name + "!?\"");
            System.out.println("\nYou hear your father calling your name from the other room as you come to.");
            System.out.println("It's late morning and the smell of fried egg and fresh bread fills the air.");
            System.out.println("Your father walks into your room. As you look up at him, you notice he's dressed up in more than his usual farm clothes.");
            System.out.println("\n\t\"Your mother and I have to head out to sell at the market today. Would you be able to go run some errands while we're there?\"");

            System.out.println("\n\tWhat do you say? (Hint: commands are inputted as keyword target. For example you could type \"say yes\". Type help for a list of valid commands)");

            while(true)
            {
                String cmd = getLowInput();

                if(cmd.equals("help"))
                {
                    System.out.println("say [yes, no]");
                    return;
                }

                if(cmd.contains("say") || !(cmd.contains("yes") || cmd.contains("no"))) 
                {
                    System.out.println("\n\t\"Just answer my question please.\"");
                    return;
                }

                if(cmd.contains("yes")) System.out.println("\n\t\"Thank you, I appreciate it.\"");
                else System.out.println("\n\t\"Well I'm sorry I even asked. You're helping, get up.\"");
                break;
            }

            System.out.println("\n\t\"Your mother and I are leaving right now. Please get some breakfast and meet us there. Bring my fathers sword along with you, I need it sharpened\"");
            System.out.println("\nAs your father leaves the house, you climb out of bed and walk into the main room of the house");
            isIntroScene = false;
        }

        System.out.println("\nYou stand in the central room of your parents farmhouse. It always smells of freshly burnt wood and pastries.");
        System.out.println("What do you do?");

        while(true)
        {
            String cmd = getLowInput();

            if(cmd.equals("help"))
            {
                System.out.print("\ngo [outside], look [around");
                if(!takenSword) System.out.print(", for the sword");
                if(!takenFood) System.out.print(", for breakfast");
                System.out.println("]");
                return;
            }

            if(!(cmd.contains("go") || cmd.contains("look")))
            {
                printError();;
                return;
            }

            if(cmd.contains("around"))
            {
                System.out.println("\nYou stand in the central room of your parents farmhouse. It always smells of fire and fresh pastries.");
                return;
            }

            if(cmd.contains("for the sword") && !takenSword)
            {
                System.out.println("\nYou look around for your grandfathers sword and find it buried at the bottom of an old trunk.");
                System.out.println("YOU GOT \"YOUR GRANDFATHER'S SWORD\".");
                //getSword
                takenSword = true;
            }

            if(cmd.contains("for breakfast") && !takenFood)
            {
                System.out.println("\nYou check the kitchen for the food your father had mentioned. You come across a fresh baked scone and a bottle of milk. Filling!");
                System.out.println("YOU GOT \"SCONE\".");
                System.out.println("YOU GOT \"MILK\".");
            }

            if(cmd.contains("outside"))
            {
                break;
            }

            printError();
            return;
        }

        farm();
    }

    private void farm()
    {
        System.out.println("\nYou find yourself at your parents farm. You grew up here. It is home.");
        System.out.println("\nTo your north is the road that leads off the property.");
        System.out.println("To your east is the apple orchard that your parents helped to plant when you were just a young child.");
        System.out.println("To your south is the beginning of the road that leads down to the orcish country, a very dangerous place.");
        System.out.println("To your west is the road through the mountains. A monastery of monks is said to be hidden away in the mountains.");
        System.out.println("\nWhat do you do?");

        char dir = ' ';
        while(true)
        {
            String cmd = getLowInput();

            if(cmd.equals("help")) 
            {
                System.out.println("\ngo [north, east, south, west, inside], look [around]");
                return;
            }

            if(!(cmd.contains("go") || cmd.contains("look")))
            {
                printError();
                return;
            }

            if(cmd.contains("go"))
            {
                if(cmd.contains("north")) {dir = 'n'; break;}
                if(cmd.contains("east")) {dir ='e'; break;}
                if(cmd.contains("south")) {dir = 's'; break;}
                if(cmd.contains("west")) {dir ='w'; break;}
                if(cmd.contains("inside")) {dir = 'i'; break;}
            }
        }
    }

    private void drawBannerArt()
    {
        System.out.println("\n           /\\                                                 /\\");
        System.out.println(" _         )( ______________________   ______________________ )(         _");
        System.out.println("(_)///////(**)______________________> <______________________(**)\\\\\\\\\\\\\\(_)");
        System.out.println("           )(                                                 )(");
        System.out.println("           \\/                                                 \\/\n");
    }

    private String getLowInput()
    {
        String input = kb.nextLine();
        return input.toLowerCase();
    }

    private void printError()
    {
        System.out.println("Invalid command! Try again! (Need \"help\"?)");
    }
}

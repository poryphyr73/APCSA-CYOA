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

        //farm
        private boolean isIntroScene = true;

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
        farm();
    }

    private void farm()
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
                if(!getLowInput().contains("say")) 
                {
                    System.out.println("\n\t\"Answer me!\"");
                    return;
                }

                break;
            }

            isIntroScene = false;
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
}

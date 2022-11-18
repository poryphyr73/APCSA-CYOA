import java.util.Scanner;

public class Manager 
{
    private static Scanner kb;
    private static WorldMap map;

    public static void main(String[] args) 
    {
        setup();
        System.out.println(map);
    }

    public static void displayInformation(String info)
    {
        System.out.println(info);
    }

    private static void takeInput()
    {

    }

    private static void setup()
    {
        kb = new Scanner(System.in);
        map = new WorldMap();
    }
}

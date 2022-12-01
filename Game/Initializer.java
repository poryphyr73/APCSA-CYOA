package Game;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import GameObjects.GameObject;
import GameObjects.Items.Armor;
import GameObjects.Items.Consumable;
import GameObjects.Items.Item;
import GameObjects.Items.Weapon;
import GameObjects.Mobs.Enemy;
import GameObjects.Mobs.Mob;
import GameObjects.Mobs.NPC;

public class Initializer 
{
    public static HashMap<Integer, String> getCoordinates()
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

    public static ArrayList<String> getLooks()
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

    public static HashMap<Integer, ArrayList<String>> getCommands()
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
                else if(c.contains("obj"))
                {
                    ArrayList<String> a = new ArrayList<>();
                    for(int j = 0; j < comList.size(); j++) a.add(comList.get(j));
                    commands.put(i, a);
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

    public static HashMap<Integer, ArrayList<GameObject>> getObjects()
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

    public static Item getItemData(String id)
    {
        String type = "";
        String name = "";
        int buff = 0;

        try {
            File worldData = new File("WorldData/items.txt");
            Scanner reader = new Scanner(worldData);
 
            while (reader.hasNextLine()) {
                String c = reader.nextLine();
                
                if(c.contains("id") && c.contains(id))
                {
                    c = reader.nextLine();
                    type = c.substring(c.indexOf(":") + 1, c.indexOf(";"));
                    c = reader.nextLine();
                    name = c.substring(c.indexOf(":") + 1, c.indexOf(";"));
                    c = reader.nextLine();
                    buff = Integer.parseInt(c.substring(c.indexOf(":"), c.indexOf(";")).replaceAll("[^0-9]", ""));
                    break;
                }
            }
            reader.close();
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(type.equals("w")) return new Weapon(name, id, buff);
        if(type.equals("a")) return new Armor(name, id, buff);
        return new Consumable(name, id, buff);
    }

    public static Mob getMobData(String id)
    {
        boolean danger = false;
        String name = "";
        String talk = "";
        Item take = null;
        int[] dmgRange = new int[2];
        int hp = 0;

        try {
            File worldData = new File("WorldData/mobs.txt");
            Scanner reader = new Scanner(worldData);
 
            while (reader.hasNextLine()) {
                String c = reader.nextLine();
                if(c.contains(id))
                {
                    c = reader.nextLine();
                    name = c.substring(c.indexOf(":") + 1, c.indexOf(";"));
                    c = reader.nextLine();
                    talk = c.substring(c.indexOf(":") + 1, c.indexOf(";"));
                    c = reader.nextLine();
                    danger = c.substring(c.indexOf(":"), c.indexOf(";")).contains("1");
                    c = reader.nextLine();
                    take = getItemData(c.substring(c.indexOf(":") + 1, c.indexOf(";")));
                    c = reader.nextLine();
                    dmgRange[0] = Integer.parseInt(c.substring(c.indexOf(":"), c.indexOf("-")).replaceAll("[^0-9]", ""));
                    dmgRange[1] = Integer.parseInt(c.substring(c.indexOf("-"), c.indexOf(";")).replaceAll("[^0-9]", ""));
                    c = reader.nextLine();
                    hp =  Integer.parseInt(c.substring(c.indexOf(":"), c.indexOf(";")).replaceAll("[^0-9]", ""));
                    break;
                }
            }
            reader.close();
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(danger) return new Enemy(name, id, talk, take, hp, dmgRange[0], dmgRange[1]);
        return new NPC(name, id, talk, take, hp, dmgRange[0], dmgRange[1]);
    }
}

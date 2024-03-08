package de.mybukkit.mycommands.helper;

import de.mybukkit.mycommands.mycommands;
import java.util.ArrayList;
import net.minecraft.entity.player.EntityPlayerMP;


public class HomePoint {

    public static ArrayList<HomePoint> homes = new ArrayList<>();
    private static final SaveFile homesSaveFile = new SaveFile("homes.txt",
        mycommands.getSaveDir() + "/mycommands/homes/");
    public String UUID;
    public Location location;
    public String homename;


    public HomePoint(EntityPlayerMP player, String name) {
        this.UUID = player.getUniqueID().toString();
        this.homename = name;
        this.location = new Location(player);
    }


    public HomePoint(EntityPlayerMP player, String name, Location location) {
        this.UUID = player.getUniqueID().toString();
        this.homename = name;
        this.location = location;
    }


    public HomePoint(String info) {
        try {
            this.UUID = info.substring(0, info.indexOf(","));
            this.homename = info.substring(info.indexOf(",") + 1, info.indexOf("("));

            String locationInfo = info.substring(info.indexOf("(") + 1, info.indexOf(")"));
            this.location = new Location(locationInfo);
        } catch (Exception e) {

            System.err.println("Exception on attemping to rebuild WarpPoint from String.");
            this.UUID = "Error";
            this.homename = "Error";
            this.location = new Location(0, 0, 0, 0);
        }
    }


    private HomePoint(EntityPlayerMP player, String name, Object dummy) {
        this.UUID = player.getUniqueID().toString();
        this.homename = name;
    }

    public static HomePoint getHome(EntityPlayerMP player, String name) {
        for (int i = 0; i < homes.size(); i++) {
            if (player.getUniqueID().toString().equals(homes.get(i).UUID)
                && name.equalsIgnoreCase(homes.get(i).homename)) {
                return homes.get(i);
            }
        }

        return null;
    }

    public static String gethomePoints(EntityPlayerMP player) {
        String targets = "";
        for (int i = 0; i < homes.size(); i++) {
            if (player.getUniqueID().toString().equals(homes.get(i).UUID)) {
                targets = targets + ", " + homes.get(i).homename;
            }
        }
        if (targets.length() > 2) {
            targets = targets.substring(2);
        }
        return targets;
    }

    public static String[] gethomePointsString(EntityPlayerMP player) {
        String[] targets = new String[getHomecounts(player) - 1];
        int count = 0;
        for (int i = 0; i < homes.size(); i++) {
            if (player.getUniqueID().toString().equals(homes.get(i).UUID)) {

                targets[count] = homes.get(i).homename;
                count++;
            }
        }

        return targets;
    }

    public static int getHomecounts(EntityPlayerMP player) {
        int targets = 1;
        for (int i = 0; i < homes.size(); i++) {
            if (player.getUniqueID().toString().equals(homes.get(i).UUID)) {
                targets++;
            }
        }

        return targets;
    }

    public static void setHome(EntityPlayerMP player, String name) {
        Location location = new Location(player);

        HomePoint target = new HomePoint(player, name, location);

        for (int i = 0; i < homes.size(); i++) {
            if (player.getUniqueID().toString().equals(homes.get(i).UUID)
                && name.equalsIgnoreCase(homes.get(i).homename)) {
                homes.remove(i);
            }
        }

        homes.add(target);

        saveAll();
    }

    public static void loadAll() {
        homesSaveFile.load();
        homes.clear();
        for (String info : homesSaveFile.data) {
            homes.add(new HomePoint(info));
        }
    }

    public static void saveAll() {
        homesSaveFile.clear();
        for (HomePoint home : homes) {
            homesSaveFile.data.add(home.toString());
        }
        homesSaveFile.save(false);
    }

    public static boolean delHomePoint(EntityPlayerMP player, String name) {
        for (int i = 0; i < homes.size(); i++) {
            if (player.getUniqueID().toString().equals(homes.get(i).UUID)
                && name.equalsIgnoreCase(homes.get(i).homename)) {
                homes.remove(i);

                saveAll();
                return true;
            }
        }

        return false;
    }

    public String toString() {
        if (this.location == null) {
            return "";
        }
        return this.UUID + "," + this.homename + "(" + this.location + ")";
    }

    public boolean equals(Object o) {
        if (o instanceof HomePoint) {
            return this.homename.equals(((HomePoint) o).homename);
        }
        return false;
    }

    public int hashCode() {
        return this.homename.hashCode();
    }
}

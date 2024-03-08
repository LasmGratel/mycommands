package de.mybukkit.mycommands.helper;

import de.mybukkit.mycommands.mycommands;
import java.util.ArrayList;
import net.minecraft.entity.player.EntityPlayerMP;


public class WarpPoint {

    public static ArrayList<WarpPoint> warpPoints = new ArrayList<>();
    private static final SaveFile warpPointsSaveFile = new SaveFile("warpPoints.txt",
        mycommands.getSaveDir() + "/mycommands/warps/");
    public String name;
    public Location location;


    public WarpPoint(EntityPlayerMP player, String name) {
        this.name = name;
        this.location = new Location(player);
    }


    public WarpPoint(String name, Location location) {
        this.name = name;
        this.location = location;
    }


    public WarpPoint(String info) {
        try {
            this.name = info.substring(0, info.indexOf("("));
            String locationInfo = info.substring(info.indexOf("(") + 1, info.indexOf(")"));
            this.location = new Location(locationInfo);
        } catch (Exception e) {

            System.err.println("Exception on attemping to rebuild WarpPoint from String.");
            this.name = "Error";
            this.location = new Location(0, 0, 0, 0);
        }
    }


    private WarpPoint(String name, Object dummy) {
        this.name = name;
    }

    public static WarpPoint getWarpPoint(String name) {
        WarpPoint target = new WarpPoint(name, null);
        if (warpPoints.contains(target)) {
            return warpPoints.get(warpPoints.indexOf(target));
        }
        return null;
    }

    public static String getWarpPoints() {
        String targets = "";
        for (WarpPoint wp : warpPoints) {
            targets = targets + ", " + wp.name;
        }
        if (targets.length() > 2) {
            targets = targets.substring(2);
        }
        return targets;
    }

    public static void setWarpPoint(EntityPlayerMP player, String name) {
        Location location = new Location(player);
        WarpPoint warpPoint = new WarpPoint(name, location);

      warpPoints.remove(warpPoint);
        warpPoints.add(warpPoint);

        saveAll();
    }

    public static boolean delWarpPoint(String name) {
        WarpPoint warpPoint = new WarpPoint(name, null);
        if (warpPoints.contains(warpPoint)) {

            warpPoints.remove(warpPoint);
            saveAll();
            return true;
        }
        return false;
    }

    public static void loadAll() {
        warpPointsSaveFile.load();
        warpPoints.clear();
        for (String info : warpPointsSaveFile.data) {
            warpPoints.add(new WarpPoint(info));
        }
    }

    public static void saveAll() {
        warpPointsSaveFile.clear();
        for (WarpPoint warpPoint : warpPoints) {
            warpPointsSaveFile.data.add(warpPoint.toString());
        }
        warpPointsSaveFile.save(false);
    }

    public String toString() {
        if (this.location == null) {
            return "";
        }
        return this.name + "(" + this.location + ")";
    }

    public boolean equals(Object o) {
        if (o instanceof WarpPoint) {
            return this.name.equals(((WarpPoint) o).name);
        }
        return false;
    }

    public int hashCode() {
        return this.name.hashCode();
    }
}

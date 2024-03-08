package de.mybukkit.mycommands.helper;

import java.util.HashMap;
import net.minecraft.entity.player.EntityPlayerMP;


public class Teleport {

    public static HashMap<EntityPlayerMP, Location> playerBackMap = new HashMap<>();


    public static void warp(EntityPlayerMP player, Location loc, boolean exact) {
        playerBackMap.put(player, new Location(player));
        int dimension = loc.dimension;

        if (dimension != player.dimension) {
            player.travelToDimension(dimension);
        }
        if (!exact) {

            player.setPositionAndUpdate(loc.x + 0.5D, loc.y, loc.z + 0.5D);
        } else {

            player.setPositionAndUpdate(loc.posX, loc.posY, loc.posZ);
        }
    }


    public static void warp(EntityPlayerMP player, EntityPlayerMP target) {
        Location loc = new Location(target);
        warp(player, loc, false);
    }


    public static void jump(EntityPlayerMP player) {
        Location loc = new Location(player, "jump");
        warp(player, loc, false);
    }


    public static boolean goBack(EntityPlayerMP player) {
        if (playerBackMap.containsKey(player)) {

            Location loc = playerBackMap.get(player);
            warp(player, loc, true);
            return true;
        }

        return false;
    }
}

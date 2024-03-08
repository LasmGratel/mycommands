package de.mybukkit.mycommands.commands;

import de.mybukkit.mycommands.helper.Location;
import de.mybukkit.mycommands.helper.MyCommandBase;
import de.mybukkit.mycommands.helper.Teleport;
import net.minecraft.entity.player.EntityPlayerMP;


public class CommandSpawn
    extends MyCommandBase {

    public CommandSpawn(boolean op) {
        this.name = "spawn";
        this.usage = " : teleport to server's spawn location";
        this.opOnly = op;
    }


    public void processPlayer(EntityPlayerMP player, String[] args) {
        int x = player.getServerForPlayer().getWorldInfo().getSpawnX();
        int y = player.getServerForPlayer().getWorldInfo().getSpawnY();
        int z = player.getServerForPlayer().getWorldInfo().getSpawnZ();
        int dim = player.getServerForPlayer().getWorldInfo().getVanillaDimension();
        Location spawn = new Location(x, y, z, dim);
        Teleport.warp(player, spawn, true);
    }
}

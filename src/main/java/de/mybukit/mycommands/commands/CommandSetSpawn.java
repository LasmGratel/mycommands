package de.mybukkit.mycommands.commands;

import de.mybukkit.mycommands.helper.Location;
import de.mybukkit.mycommands.helper.MyCommandBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class CommandSetSpawn
    extends MyCommandBase {

    public CommandSetSpawn(boolean op) {
        this.name = "setspawn";
        this.usage = " : Set the server's spawn location.";
        this.opOnly = op;
    }


    public void processPlayer(EntityPlayerMP player, String[] args) {
        Location loc = new Location(player);
        player.worldObj.getWorldInfo().setSpawnPosition(loc.x, loc.y, loc.z);
        player.addChatMessage(new ChatComponentText("§aspawn set."));
    }
}

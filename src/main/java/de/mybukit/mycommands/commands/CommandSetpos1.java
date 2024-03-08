package de.mybukkit.mycommands.commands;

import de.mybukkit.mycommands.helper.Location;
import de.mybukkit.mycommands.helper.MyCommandBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class CommandSetpos1
    extends MyCommandBase {

    public static int x1;
    public static int y1;
    public static int z1;

    public CommandSetpos1(boolean op) {
        this.name = "pos1";
        this.usage = " : Set pos1 location.";
        this.opOnly = op;
    }


    public void processPlayer(EntityPlayerMP player, String[] args) {
        Location loc = new Location(player);
        x1 = loc.x;
        y1 = loc.y;
        z1 = loc.z;
        player.addChatMessage(new ChatComponentText("§apos1 set."));
    }
}

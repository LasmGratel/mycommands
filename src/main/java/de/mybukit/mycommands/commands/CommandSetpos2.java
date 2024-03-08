package de.mybukkit.mycommands.commands;

import de.mybukkit.mycommands.helper.Location;
import de.mybukkit.mycommands.helper.MyCommandBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class CommandSetpos2
    extends MyCommandBase {

    public static int x2;
    public static int y2;
    public static int z2;

    public CommandSetpos2(boolean op) {
        this.name = "pos2";
        this.usage = " : Set pos2 location.";
        this.opOnly = op;
    }


    public void processPlayer(EntityPlayerMP player, String[] args) {
        Location loc = new Location(player);
        x2 = loc.x;
        y2 = loc.y;
        z2 = loc.z;
        player.addChatMessage(new ChatComponentText("§apos2 set."));
    }
}

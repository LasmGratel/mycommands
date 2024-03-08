package de.mybukkit.mycommands.commands;

import de.mybukkit.mycommands.helper.MyCommandBase;
import de.mybukkit.mycommands.helper.Teleport;
import de.mybukkit.mycommands.helper.WarpPoint;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;


public class CommandWarp
    extends MyCommandBase {

    public CommandWarp(boolean op) {
        this.name = "warp";
        this.usage = " <WarpPointName>: Warp you to a location or a player.";
        this.opOnly = op;
    }


    public void processPlayer(EntityPlayerMP player, String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length != 1) {

            player.addChatMessage(new ChatComponentText(
                "§aWarpPointNames: §b" + WarpPoint.getWarpPoints()));

        } else {

            String warpPointName = par2ArrayOfStr[0];
            WarpPoint warpPoint = WarpPoint.getWarpPoint(warpPointName);

            if (warpPoint != null) {

                WarpPoint.getWarpPoint(warpPointName);
                Teleport.warp(player, warpPoint.location, false);
                player.addChatMessage(new ChatComponentText(
                    "§b" + warpPointName + "§a" + " has been warped."));
            } else {
                player.addChatMessage(new ChatComponentText(
                    "§aWarpPointNames: §b" + WarpPoint.getWarpPoints()));
            }
        }
    }
}

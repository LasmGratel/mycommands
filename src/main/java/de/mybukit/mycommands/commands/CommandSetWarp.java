package de.mybukkit.mycommands.commands;

import de.mybukkit.mycommands.helper.MyCommandBase;
import de.mybukkit.mycommands.helper.WarpPoint;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;


public class CommandSetWarp
    extends MyCommandBase {

    public CommandSetWarp(boolean op) {
        this.name = "setwarp";
        this.usage = " : [warpname]";
        this.opOnly = op;
    }


    public void processPlayer(EntityPlayerMP player, String[] args) {
        if (args.length >= 1 && args.length <= 1) {

            if (args.length == 1) {

                String warpPointName = args[0];
                WarpPoint warpPoint = WarpPoint.getWarpPoint(warpPointName);

                if (warpPoint == null) {

                    WarpPoint.setWarpPoint(player, warpPointName);
                    player.addChatMessage(new ChatComponentText(
                        "§b" + warpPointName + "§a" + " has been set."));
                } else {

                    player.addChatMessage(
                        new ChatComponentText("§dWarppoint already exists"));
                }
            }
        }
    }
}

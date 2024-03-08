package de.mybukkit.mycommands.commands;

import de.mybukkit.mycommands.helper.MyCommandBase;
import de.mybukkit.mycommands.helper.WarpPoint;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;


public class CommandDelWarp
    extends MyCommandBase {

    public CommandDelWarp(boolean op) {
        this.name = "delwarp";
        this.usage = "Delete Warp <Warppoint name>";
        this.opOnly = op;
    }


    public void processPlayer(EntityPlayerMP player, String[] args) {
        if (args.length >= 1 && args.length <= 1) {

            if (args.length == 1) {

                String warpPointName = args[0];
                WarpPoint warpPoint = WarpPoint.getWarpPoint(warpPointName);

                if (warpPoint == null) {

                    player.addChatMessage(new ChatComponentText(
                        "§b" + warpPointName + "§4" + " does not exist."));
                } else {

                    WarpPoint.delWarpPoint(warpPointName);
                    player.addChatMessage(new ChatComponentText(
                        "§b" + warpPointName + "§7" + " has been deleted."));
                }
            }
        }
    }
}

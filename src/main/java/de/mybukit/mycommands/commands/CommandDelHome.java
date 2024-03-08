package de.mybukkit.mycommands.commands;

import de.mybukkit.mycommands.helper.HomePoint;
import de.mybukkit.mycommands.helper.MyCommandBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;


public class CommandDelHome
    extends MyCommandBase {

    public CommandDelHome(boolean op) {
        this.name = "delhome";
        this.usage = "Delete Home <Homepoint name>";
        this.opOnly = op;
    }


    public void processPlayer(EntityPlayerMP player, String[] args) {
        if (args.length == 1) {

          String homePointName = args[0];
          HomePoint warpPoint = HomePoint.getHome(player, homePointName);

          if (warpPoint == null) {

              player.addChatMessage(new ChatComponentText(
                  "§b" + homePointName + "§4" + " does not exist."));
          } else {

              HomePoint.delHomePoint(player, homePointName);
              player.addChatMessage(new ChatComponentText(
                  "§b" + homePointName + "§7" + " has been deleted."));
          }
        }
    }
}

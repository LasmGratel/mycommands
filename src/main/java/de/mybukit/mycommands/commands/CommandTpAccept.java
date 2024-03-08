package de.mybukkit.mycommands.commands;

import de.mybukkit.mycommands.helper.Location;
import de.mybukkit.mycommands.helper.MyCommandBase;
import de.mybukkit.mycommands.helper.PlayerManager;
import de.mybukkit.mycommands.helper.Teleport;
import java.util.List;
import net.minecraft.command.ICommand;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class CommandTpAccept
    extends MyCommandBase
    implements ICommand {

    public CommandTpAccept(boolean op) {
        this.name = "tpaccept";
        this.usage = " : Accept Tp.";
        this.opOnly = op;
    }

    public void processPlayer(EntityPlayerMP player, String[] args) {
        if (PlayerManager.TeleportRequests.pending(player.getUniqueID())) {
            List<EntityPlayerMP> playerlist = PlayerManager.getplayerList();
            Boolean playerFound = Boolean.valueOf(false);
            for (int i = 0; i < playerlist.size(); i++) {
                if (playerlist.get(i).getUniqueID()
                    .equals(PlayerManager.TeleportRequests.fromWho(player.getUniqueID()))) {
                    playerFound = Boolean.valueOf(true);
                    EntityPlayerMP teleporter = playerlist.get(i);
                    EntityPlayerMP teleportTo = player;

                    teleporter.addChatMessage(
                        new ChatComponentText("gotaccepted"));
                    teleportTo.addChatMessage(
                        new ChatComponentText("youaccepted"));
                    Teleport.warp(teleporter, new Location(teleportTo), true);
                }
            }
            if (!playerFound.booleanValue()) {
                player.addChatMessage(new ChatComponentText("notonline"));
            }
            PlayerManager.TeleportRequests.remove(player.getUniqueID());
        } else {
            player.addChatMessage(new ChatComponentText("nonetoaccept"));
        }
    }
}

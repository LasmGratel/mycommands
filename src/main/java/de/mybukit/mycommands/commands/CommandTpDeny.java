package de.mybukkit.mycommands.commands;

import de.mybukkit.mycommands.helper.MyCommandBase;
import de.mybukkit.mycommands.helper.PlayerManager;
import java.util.List;
import net.minecraft.command.ICommand;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;


public class CommandTpDeny
    extends MyCommandBase
    implements ICommand {

    public CommandTpDeny(boolean op) {
        this.name = "tpdeny";
        this.usage = " : Denied Tp.";
        this.opOnly = op;
    }

    public void processPlayer(EntityPlayerMP player, String[] args) {
        if (PlayerManager.TeleportRequests.pending(player.getUniqueID())) {
            player.addChatMessage(new ChatComponentText("youdenied"));
            PlayerManager.TeleportRequests.remove(player.getUniqueID());
            List<EntityPlayerMP> playerlist = PlayerManager.getplayerList();
            for (int i = 0; i < playerlist.size(); i++) {
                if (playerlist.get(i).getUniqueID()
                    .equals(PlayerManager.TeleportRequests.fromWho(player.getUniqueID()))) {
                    playerlist.get(i).addChatMessage(
                        new ChatComponentText("gotdenied"));
                }
            }
        } else {
            player.addChatMessage(new ChatComponentText("nonetodeny"));
        }
    }
}

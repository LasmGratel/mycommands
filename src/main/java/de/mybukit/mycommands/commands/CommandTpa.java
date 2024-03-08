package de.mybukkit.mycommands.commands;

import de.mybukkit.mycommands.helper.MyCommandBase;
import de.mybukkit.mycommands.helper.PlayerManager;
import de.mybukkit.mycommands.helper.SimpleScheduler;
import java.util.List;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.event.ClickEvent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;


public class CommandTpa
    extends MyCommandBase {

    private EntityPlayerMP sender;

    public CommandTpa(boolean op) {
        this.name = "tpa";
        this.usage = "requestet player";
        this.opOnly = op;
    }


    public void processPlayer(EntityPlayerMP player, String[] args) {
        if (args.length > 0) {
            EntityPlayerMP requestedPlayer = PlayerManager.getPlayer(args[0], true);

            PlayerManager.TeleportRequests.remove(requestedPlayer.getUniqueID());
            PlayerManager.TeleportRequests.add(requestedPlayer.getUniqueID(), player.getUniqueID());
            SimpleScheduler.schedule(Long.valueOf(650L), () -> {
                if (PlayerManager.TeleportRequests.pending(requestedPlayer.getUniqueID())) {
                    PlayerManager.TeleportRequests.remove(requestedPlayer.getUniqueID());

                    requestedPlayer.addChatMessage(
                        new ChatComponentText("§cTeleport request expired."));

                    player.addChatMessage(
                        new ChatComponentText("§cTeleport request expired."));
                }
            });

            requestedPlayer.addChatMessage(new ChatComponentText(
                "§6Tpa request from §f" + player.getDisplayName()));
            ChatComponentText chatComponentText1 = new ChatComponentText("§a[Accept] ");
            chatComponentText1.getChatStyle()
                .setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaccept"));
            ChatComponentText chatComponentText2 = new ChatComponentText("§c [Deny]");
            chatComponentText2.getChatStyle()
                .setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpdeny"));
            requestedPlayer.addChatMessage(
                chatComponentText1.appendSibling(chatComponentText2));
        } else {

            player.addChatMessage(new ChatComponentText("§cPlayer not Online"));
        }
    }


    public List addTabCompletionOptions(ICommandSender sender, String[] player) {
        return (player.length != 1 && player.length != 2) ? null
            : getListOfStringsMatchingLastWord(player,
                MinecraftServer.getServer().getAllUsernames());
    }
}

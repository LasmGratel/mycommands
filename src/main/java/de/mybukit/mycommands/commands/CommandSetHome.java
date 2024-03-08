package de.mybukkit.mycommands.commands;

import de.mybukkit.mycommands.helper.HomePoint;
import de.mybukkit.mycommands.helper.MyCommandBase;
import de.mybukkit.mycommands.helper.PlayerManager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;


public class CommandSetHome
    extends MyCommandBase {

    private static int maxHomes;

    public CommandSetHome(boolean op, int homes) {
        this.name = "sethome";
        this.usage = " : set Home location.";
        this.opOnly = op;
        CommandSetHome.maxHomes = homes;
    }


    public void processPlayer(EntityPlayerMP player, String[] args) {
        int homes = HomePoint.getHomecounts(player);

        if (homes <= maxHomes || player.canCommandSenderUseCommand(2, getCommandName())) {
            if (args.length == 0) {

                HomePoint home = HomePoint.getHome(player, "home");
                if (home == null) {
                    HomePoint.setHome(player, "home");
                    player.addChatMessage(
                        new ChatComponentText("§aset Home §6home§a."));
                } else {

                    player.addChatMessage(new ChatComponentText(
                        "§dargument missmatch, /sethome <HomeName> for Home"));
                }

            } else if (args.length == 1) {

                HomePoint.setHome(player, args[0]);
                player.addChatMessage(
                    new ChatComponentText("§aset Home §6" + args[0] + "§a" + "."));
            } else {
                player.addChatMessage(new ChatComponentText(
                    "§dargument missmatch, /sethome <HomeName> for Home"));
            }
        } else {

            player.addChatMessage(new ChatComponentText(
                "§cYou have max of Homes reached, delete one"));
        }
    }
}

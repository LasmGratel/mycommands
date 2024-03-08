package de.mybukkit.mycommands.commands;

import de.mybukkit.mycommands.helper.HomePoint;
import de.mybukkit.mycommands.helper.MyCommandBase;
import de.mybukkit.mycommands.helper.PlayerManager;
import de.mybukkit.mycommands.helper.Teleport;
import java.util.List;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;


public class CommandHome
    extends MyCommandBase {

    public CommandHome(boolean op) {
        this.name = "home";
        this.usage = " : Go home.";
        this.opOnly = op;
    }


    public void processPlayer(EntityPlayerMP player, String[] args) {
        if (args.length == 0) {

            HomePoint home = HomePoint.getHome(player, "home");
            if (home != null) {
                Teleport.warp(player, home.location, false);
            } else {
                player.addChatMessage(new ChatComponentText(
                    "§aYour HomePointNames: §b" + HomePoint.gethomePoints(player)));
            }

        } else if (args.length == 1) {

            HomePoint home = HomePoint.getHome(player, args[0]);
            if (home != null) {

                Teleport.warp(player, home.location, false);
                player.addChatMessage(new ChatComponentText("§aWarped home."));
            } else {

                player.addChatMessage(new ChatComponentText("Home not Exisit"));
                if (!HomePoint.gethomePoints(player).equals("")) {
                    player.addChatMessage(new ChatComponentText(
                        "§aYour HomePointNames: §b" + HomePoint.gethomePoints(player)));
                } else {

                    player.addChatMessage(
                        new ChatComponentText("§4You are homeless!"));
                }
            }
        }
    }


    public List addTabCompletionOptions(ICommandSender sender, String[] name) {
        return (name.length != 1 && name.length != 2) ? null
            : getListOfStringsMatchingLastWord(name, HomePoint.gethomePointsString(
                PlayerManager.getPlayer(sender.getCommandSenderName())));
    }
}

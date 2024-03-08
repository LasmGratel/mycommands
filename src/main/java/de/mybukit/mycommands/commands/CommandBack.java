package de.mybukkit.mycommands.commands;

import de.mybukkit.mycommands.helper.MyCommandBase;
import de.mybukkit.mycommands.helper.Teleport;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;


public class CommandBack
    extends MyCommandBase {

    public CommandBack(boolean op) {
        this.opOnly = op;
    }


    @Override
    public String getCommandName() {
        return "back";
    }


    public String getCommandUsage(ICommandSender p_71518_1_) {
        return "/" + getCommandName() + " : Warp back to your previous location.";
    }


    public void processPlayer(EntityPlayerMP player, String[] args) {
        if (Teleport.goBack(player)) {

            player.addChatMessage(
                new ChatComponentText("§aWarped back to previous location."));
        } else {

            player.addChatMessage(
                new ChatComponentText("§4You have not warped anywhere!"));
        }
    }
}

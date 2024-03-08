package de.mybukkit.mycommands.helper;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;


public class MyCommandBase
    extends CommandBase {

    public static String trueName;
    public String name;
    public String usage;
    public boolean opOnly = false;

    public void processConsole(String[] args) {
    }

    public void processPlayer(EntityPlayerMP player, String[] args) {
    }

    public List getCommandAliases() {
        ArrayList<String> name = new ArrayList<>();
        return name;
    }


    public void processCommand(ICommandSender sender, String[] args) {
        if (sender instanceof EntityPlayerMP) {
            processPlayer((EntityPlayerMP) sender, args);
        } else {
            processConsole(args);
        }
    }


    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        if (this.opOnly) {
            return sender.canCommandSenderUseCommand(3, getCommandName());
        }
        return sender.canCommandSenderUseCommand(0, getCommandName());
    }


    public List addTabCompletionOptions(ICommandSender sender, String[] args) {
        return null;
    }


    public void argumentMismatch(ICommandSender sender) {
        sender.addChatMessage(new ChatComponentText("§4Argument mismatch, try:"));
    }


    public boolean isUsernameIndex(String[] astring, int i) {
        return false;
    }


    public String getCommandName() {
        return this.name;
    }


    public String getCommandUsage(ICommandSender sender) {
        //return "mycommands." + getCommandName() + ".usage";
        return "/" + this.name + " " + this.usage;
    }
}

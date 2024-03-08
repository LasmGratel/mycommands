package de.mybukkit.mycommands.commands;

import de.mybukkit.mycommands.helper.MyCommandBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;


public class CommandDay
    extends MyCommandBase {

    public CommandDay(boolean op) {
        this.name = "day";
        this.usage = "Change for day";
        this.opOnly = op;
    }


    public void processPlayer(EntityPlayerMP player, String[] args) {
        player.addChatMessage(new ChatComponentText("Day"));
        player.worldObj.setWorldTime(1000L);
    }
}

package de.mybukkit.mycommands.commands;

import de.mybukkit.mycommands.helper.MyCommandBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class CommandNight
    extends MyCommandBase {

    public CommandNight(boolean op) {
        this.name = "night";
        this.usage = "Change for night";
        this.opOnly = op;
    }


    public void processPlayer(EntityPlayerMP player, String[] args) {
        player.addChatMessage(new ChatComponentText("Night"));
        player.worldObj.setWorldTime(13500L);
    }
}

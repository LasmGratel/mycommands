package de.mybukkit.mycommands.commands;

import de.mybukkit.mycommands.helper.MyCommandBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.storage.WorldInfo;


public class CommandSun
    extends MyCommandBase {

    public CommandSun(boolean op) {
        this.name = "sun";

        this.usage = "Active the sun";
        this.opOnly = op;
    }


    public void processPlayer(EntityPlayerMP player, String[] args) {
        player.addChatMessage(new ChatComponentText("Sun"));
        WorldInfo info = player.worldObj.getWorldInfo();
        info.setRaining(false);
        info.setThundering(false);

        player.worldObj.updateWeatherBody();
    }
}

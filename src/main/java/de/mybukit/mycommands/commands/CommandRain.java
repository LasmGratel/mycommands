package de.mybukkit.mycommands.commands;

import de.mybukkit.mycommands.helper.MyCommandBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.storage.WorldInfo;

public class CommandRain
    extends MyCommandBase {

    private EntityPlayerMP sender;

    public CommandRain(boolean op) {
        this.name = "rain";
        this.usage = "Active the rain";
        this.opOnly = op;
    }


    public void processPlayer(EntityPlayerMP player, String[] args) {
        player.addChatMessage(new ChatComponentText("Rain"));
        WorldInfo info = player.worldObj.getWorldInfo();
        info.setRaining(true);
        info.setThundering(false);
        player.worldObj.updateWeatherBody();
    }
}

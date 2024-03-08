package de.mybukkit.mycommands.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import de.mybukkit.mycommands.commands.CommandFly;
import de.mybukkit.mycommands.commands.CommandGod;
import de.mybukkit.mycommands.helper.Location;
import de.mybukkit.mycommands.helper.Teleport;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;


public class MYEventHandler {

    private World world;

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent e) {
        if (!e.entity.worldObj.isRemote && e.entity instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP) e.entity;

            if (!player.capabilities.allowFlying && CommandFly.mustFlying(player)) {
                player.capabilities.allowFlying = true;
                player.sendPlayerAbilities();
                player.addChatMessage(new ChatComponentText("Fly Active"));
            }

            if (!player.capabilities.disableDamage && CommandGod.mustbeGod(player)) {
                player.capabilities.disableDamage = true;
                player.sendPlayerAbilities();
                player.addChatMessage(new ChatComponentText("God Active"));
            }
        }
    }

    @SubscribeEvent
    public void onlivingDeathEvent(LivingDeathEvent e) {
        if (!e.entity.worldObj.isRemote && e.entity instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP) e.entity;
            Location location = new Location(player);
            Teleport.playerBackMap.put(player, location);
        }
    }
}

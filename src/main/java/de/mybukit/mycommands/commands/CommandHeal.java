package de.mybukkit.mycommands.commands;

import de.mybukkit.mycommands.helper.MyCommandBase;
import de.mybukkit.mycommands.helper.PlayerManager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;


public class CommandHeal
    extends MyCommandBase {

    public CommandHeal(boolean op) {
        this.name = "heal";
        this.usage = "heal or heal <Player>";
        this.opOnly = op;
    }


    public void processPlayer(EntityPlayerMP player, String[] astring) {
        if (astring.length == 0) {

            player.setHealth(player.getMaxHealth());
            player.getFoodStats().addStats(20, 2.0F);
            String txt = "You are healed ";
            player.addChatMessage(new ChatComponentText(txt));
        } else {
            EntityPlayerMP target = PlayerManager.getPlayer(astring[0], true);

            if (target == null) {
                target = PlayerManager.getPossiblePlayer(astring[0]);
            }

            if (target != null) {
                target.setHealth(target.getMaxHealth());
                target.getFoodStats().addStats(20, 2.0F);
                String txt = "You were healed ";
                target.addChatMessage(new ChatComponentText(txt));
            } else {

                player.addChatMessage(
                    new ChatComponentText("§aPlayer is Offline "));
            }
        }
    }
}

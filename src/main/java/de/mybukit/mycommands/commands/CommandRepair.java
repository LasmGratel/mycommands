package de.mybukkit.mycommands.commands;

import de.mybukkit.mycommands.helper.MyCommandBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;


public class CommandRepair
    extends MyCommandBase {

    public CommandRepair(boolean op) {
        this.name = "repair";
        this.usage = " : repair the Item in Hand";
        this.opOnly = op;
    }


    public void processPlayer(EntityPlayerMP player, String[] args) {
        ItemStack item = player.getHeldItem();

        if (item != null) {
            item.setItemDamage(0);
        }
    }
}

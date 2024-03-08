package de.mybukkit.mycommands.commands;

import cpw.mods.fml.common.registry.GameRegistry;
import de.mybukkit.mycommands.helper.MyCommandBase;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;


public class CommandSetBlock
    extends MyCommandBase {

    public CommandSetBlock(boolean op) {
        this.name = "set";
        this.usage = " : Set block.";
        this.opOnly = op;
    }


    public void processPlayer(EntityPlayerMP player, String[] args) {
        if (args.length < 1 || args.length > 2) {
            player.addChatMessage(
                new ChatComponentText("§c2 argument arg1=modid,arg2=block name"));
        } else if (args.length == 1) {
            Block block = Block.getBlockFromName(args[0]);
            setblock(player, block);
        } else {

            Block block = GameRegistry.findBlock(args[0], args[1]);
            setblock(player, block);
        }
    }


    public void setblock(EntityPlayerMP player, Block block) {
        int x1;
        int x2;
        int y1;
        int y2;
        int z1;
        int z2;
        if (CommandSetpos1.x1 < CommandSetpos2.x2) {
            x1 = CommandSetpos1.x1;
            x2 = CommandSetpos2.x2;
        } else {

            x1 = CommandSetpos2.x2;
            x2 = CommandSetpos1.x1;
        }
        if (CommandSetpos1.z1 < CommandSetpos2.z2) {
            z1 = CommandSetpos1.z1;
            z2 = CommandSetpos2.z2;
        } else {

            z1 = CommandSetpos2.z2;
            z2 = CommandSetpos1.z1;
        }
        if (CommandSetpos1.y1 < CommandSetpos2.y2) {
            y1 = CommandSetpos1.y1;
            y2 = CommandSetpos2.y2;
        } else {

            y1 = CommandSetpos2.y2;
            y2 = CommandSetpos1.y1;
        }
        if (y1 != 0 && y2 != 0) {
            for (int i = y1; i <= y2; i++) {
                for (int z = z1; z <= z2; z++) {
                    for (int x = x1; x <= x2; x++) {
                        player.worldObj.setBlock(x, i, z, block);
                    }
                }
            }
            player.addChatMessage(new ChatComponentText("§aset" + block));
        } else {
            player.addChatMessage(
                new ChatComponentText("§cpos1 oder pos2 nicht gestezt"));
        }
    }
}

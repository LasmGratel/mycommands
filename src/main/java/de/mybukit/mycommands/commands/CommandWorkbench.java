package de.mybukkit.mycommands.commands;

import de.mybukkit.mycommands.helper.MyCommandBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S2DPacketOpenWindow;

public class CommandWorkbench extends MyCommandBase {

    private EntityPlayerMP ePlayer;

    public CommandWorkbench(boolean op) {
        this.name = "wb";
        this.usage = "workbench";
        this.opOnly = op;
    }

    public void processPlayer(EntityPlayerMP player, String[] args) {
        this.ePlayer = player;

        this.ePlayer.getNextWindowId();
        sendPacket(new S2DPacketOpenWindow(this.ePlayer.currentWindowId, 1, "Workbench", 9,
            true));
        this.ePlayer.openContainer = new ContainerWorkbench(this.ePlayer.inventory,
            this.ePlayer.worldObj, 0, 0, 0) {
            public void onCraftMatrixChanged(IInventory par1IInventory) {
                this.craftResult.setInventorySlotContents(0, CraftingManager.getInstance()
                    .findMatchingRecipe(this.craftMatrix, CommandWorkbench.this.ePlayer.worldObj));
            }


            public void onContainerClosed(EntityPlayer par1EntityPlayer) {
                super.onContainerClosed(par1EntityPlayer);

                if (!CommandWorkbench.this.ePlayer.worldObj.isRemote) {
                    for (int var2 = 0; var2 < 9; var2++) {
                        ItemStack var3 = this.craftMatrix.getStackInSlotOnClosing(var2);

                        if (var3 != null) {
                            par1EntityPlayer.dropPlayerItemWithRandomChoice(var3, true);
                        }
                    }
                }
            }


            public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
                return true;
            }
        };
        this.ePlayer.openContainer.windowId = this.ePlayer.currentWindowId;
        this.ePlayer.openContainer.addCraftingToCrafters(this.ePlayer);
    }

    public void sendPacket(Packet packet) {
        this.ePlayer.playerNetServerHandler.sendPacket(packet);
    }
}

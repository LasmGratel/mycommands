package de.mybukkit.mycommands.helper;

import com.mojang.authlib.GameProfile;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.network.play.server.S2DPacketOpenWindow;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;


public class Player {

    protected Players player;
    private EntityPlayerMP ePlayer;

    public Player(EntityPlayer player) {
        if (player != null) {
            this.ePlayer = (EntityPlayerMP) player;
        }
    }


    public Player(UUID uuid) {
        if (uuid != null)
            ;
    }


    public Player() {
    }


    public Player(ICommandSender sender) {
        this((EntityPlayer) sender);
    }

    public String getPlayerName() {
        return this.ePlayer.getDisplayName();
    }

    public void kick(String reason) {
        this.ePlayer.playerNetServerHandler.kickPlayerFromServer(reason);
    }

    public void sendPacket(Packet packet) {
        this.ePlayer.playerNetServerHandler.sendPacket(packet);
    }

    public WorldSettings.GameType getGameMode() {
        return this.ePlayer.theItemInWorldManager.getGameType();
    }

    public void setGameMode(WorldSettings.GameType type) {
        this.ePlayer.theItemInWorldManager.setGameType(type);
        sendPacket(new S2BPacketChangeGameState(3, type.getID()));
    }

    public void exec(String command) {
        MinecraftServer.getServer().getCommandManager()
            .executeCommand(this.ePlayer, command);
    }

    public double getPosX() {
        return this.ePlayer.posX;
    }

    public double getPosY() {
        return this.ePlayer.posY;
    }

    public double getPosZ() {
        return this.ePlayer.posZ;
    }

    public boolean isDead() {
        return this.ePlayer.isDead;
    }

    public EntityPlayerMP getPlayer() {
        return this.ePlayer;
    }

    public ItemStack getStackFromInventory(int slot) {
        return this.ePlayer.inventory.mainInventory[slot];
    }

    public boolean setItemStackInSlot(ItemStack item) {
        return this.ePlayer.inventory.addItemStackToInventory(item.copy());
    }

    public int getEmptyInventorySlot() {
        for (int i = 0; i < this.ePlayer.inventory.mainInventory.length; i++) {
            if (this.ePlayer.inventory.mainInventory[i] == null) {
                return i;
            }
        }
        return -1;
    }

    public void giveItem(ItemStack item) {
        EntityItem i = this.ePlayer.dropPlayerItemWithRandomChoice(item, false);
        if (i == null) {
            sendErrorMessage("EnityItem is null");
        }
        i.delayBeforeCanPickup = 0;
        i.func_145797_a(this.ePlayer.getCommandSenderName());
    }


    public void suicide() {
        this.ePlayer.setDead();
    }

    public World getWorld() {
        return this.ePlayer.worldObj;
    }


    public GameProfile getProfile() {
        return (this.ePlayer != null) ? this.ePlayer.getGameProfile()
            : MinecraftServer.getServer().func_152358_ax().func_152655_a(getPlayerName());
    }

    public void setHealth(float val) {
        this.ePlayer.setHealth(val);
    }

    public String getNickname() {
        return (this.player.getNickName() != null || this.player.getNickName() != "")
            ? this.player.getNickName() : this.ePlayer.getCommandSenderName();
    }

    public void enableNickname() {
        this.player.enableNickname(!isNicknameEnabled());
    }

    public boolean isInventoryFull() {
        for (int i = 0; i < this.ePlayer.inventory.mainInventory.length; i++) {
            if (this.ePlayer.inventory.mainInventory[i] == null) {
                return false;
            }
        }
        return true;
    }

    public boolean isNicknameEnabled() {
        return this.player.isNicknameEnabled();
    }

    public void refreshDisplayName() {
        this.ePlayer.refreshDisplayName();
    }

    public void updateTime() {
        this.player.setLastUpdate((new Date()).getTime());
    }

    public String getGroup() {
        return null;
    }

    public void sendMessage(String message, Object... args) {
        sendMessage(String.format(message, args));
    }

    public void sendMessage(List<String> lines) {
        for (String line : lines) {
            sendMessage(line);
        }
    }

    public void sendMessage(String msg) {
        this.ePlayer.addChatMessage(new ChatComponentText(msg));
    }

    public boolean sendMessageTo(Player target, String msg) {
        if (target == null) {
            return false;
        }
        sendMessage("[me -> %s] %s", getPlayerName(), msg);
        target.sendMessage("[%s -> me] %s", getPlayerName(), msg);

        return true;
    }


    public void spawn() {
    }


    public InetSocketAddress getAddress() {
        if (this.ePlayer.isClientWorld()) {
            return null;
        }
        SocketAddress addr = this.ePlayer.playerNetServerHandler.netManager.getSocketAddress();
        if (addr instanceof InetSocketAddress) {
            return (InetSocketAddress) addr;
        }
        return null;
    }


    public boolean isFlying() {
        return this.ePlayer.capabilities.isFlying;
    }

    public boolean canFly() {
        return this.ePlayer.capabilities.allowFlying;
    }


    public void fly(float speed) {
        this.ePlayer.capabilities.allowFlying = !isFlying();
        if (!this.ePlayer.capabilities.allowFlying) {
            this.ePlayer.capabilities.isFlying = false;
        }
        this.ePlayer.sendPlayerAbilities();
    }

    public boolean isInvincible() {
        return this.ePlayer.capabilities.disableDamage;
    }

    public boolean isVanished() {
        return this.ePlayer.isInvisible();
    }

    public void vanish() {
        this.ePlayer.setInvisible(!isVanished());
    }

    public void god() {
        this.ePlayer.capabilities.disableDamage = !isInvincible();
        this.ePlayer.sendPlayerAbilities();
    }

    public void setFire(int i) {
        this.ePlayer.setFire(i);
    }

    public void removePotionEffect(int id) {
        this.ePlayer.removePotionEffect(id);
    }

    public void addPotionEffect(PotionEffect potionEffect) {
        this.ePlayer.addPotionEffect(potionEffect);
    }

    public UUID getUniqueID() {
        return this.ePlayer.getUniqueID();
    }

    public void sendErrorMessage(String message, Object... args) {
        sendMessage(EnumChatFormatting.RED + message, args);
    }

    public void sendSuccessMessage(String message, Object... args) {
        sendMessage(EnumChatFormatting.GREEN + message, args);
    }

    public void sendErrorMessage(String message) {
        sendMessage(EnumChatFormatting.RED + message);
    }

    public void sendSuccessMessage(String message) {
        sendMessage(EnumChatFormatting.GREEN + message);
    }

    public int getBalance() {
        return this.player.getBalance();
    }

    public void setHunger(int value) {
        this.ePlayer.getFoodStats().addStats(value, 5.0F);
    }

    public float getFoodLevel() {
        return this.ePlayer.getFoodStats().getFoodLevel();
    }

    public float getSaturationLevel() {
        return this.ePlayer.getFoodStats().getSaturationLevel();
    }

    public int getXP() {
        return this.ePlayer.experienceLevel;
    }

    public void setXP(int value) {
        this.ePlayer.addExperienceLevel(value);
    }

    public void openWorkbench() {
        this.ePlayer.getNextWindowId();
        sendPacket(new S2DPacketOpenWindow(this.ePlayer.currentWindowId, 1, "Workbench", 9,
            true));
        this.ePlayer.openContainer = new ContainerWorkbench(this.ePlayer.inventory,
            this.ePlayer.worldObj, 0, 0, 0) {
            public void onCraftMatrixChanged(IInventory par1IInventory) {
                this.craftResult.setInventorySlotContents(0, CraftingManager.getInstance()
                    .findMatchingRecipe(this.craftMatrix, Player.this.ePlayer.worldObj));
            }


            public void onContainerClosed(EntityPlayer par1EntityPlayer) {
                super.onContainerClosed(par1EntityPlayer);

                if (!Player.this.ePlayer.worldObj.isRemote) {
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

    public void openEnderchest() {
        if (this.ePlayer.openContainer != this.ePlayer.inventoryContainer) {
            this.ePlayer.closeScreen();
        }
        this.ePlayer.getNextWindowId();
        InventoryEnderChest chest = this.ePlayer.getInventoryEnderChest();
        sendPacket(new S2DPacketOpenWindow(this.ePlayer.currentWindowId, 0,
            chest.getInventoryName(), chest.getSizeInventory(), true));
        this.ePlayer.openContainer = new ContainerChest(
            this.ePlayer.inventory, chest);
        this.ePlayer.openContainer.windowId = this.ePlayer.currentWindowId;
        this.ePlayer.openContainer.addCraftingToCrafters(this.ePlayer);
    }

    public ItemStack getEquipedItem() {
        return this.ePlayer.getHeldItem();
    }

    public void setEquipedItemDurability(int value) {
        this.ePlayer.getHeldItem().setItemDamage(value);
    }

    public void clearInventory() {
        for (int i = 0; i < 36; i++) {
            this.ePlayer.inventory.setInventorySlotContents(i, null);
        }
    }
}

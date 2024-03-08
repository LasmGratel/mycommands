package de.mybukkit.mycommands.commands;

import de.mybukkit.mycommands.helper.MyCommandBase;
import de.mybukkit.mycommands.helper.PlayerManager;
import de.mybukkit.mycommands.helper.SaveFile;
import de.mybukkit.mycommands.mycommands;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;


public class CommandFly
    extends MyCommandBase {

    private static final SaveFile _flyingSaveFile = new SaveFile("fly.txt",
        mycommands.getSaveDir() + "/mycommands/commands/");
    private static final ArrayList<String> _usersFlying = new ArrayList<>();

    public CommandFly(boolean op) {
        this.name = "fly";
        this.opOnly = op;
        this.usage = " [<PlayerName>]: You toggle your fly capability or for other player's fly capability";
    }

    public static void loadConfig() {
        _flyingSaveFile.load();
        _usersFlying.clear();

        for (String info : _flyingSaveFile.data) {
            _usersFlying.add(info);
        }
    }

    public static void saveConfig() {
        _flyingSaveFile.clear();
        for (String username : _usersFlying) {
            _flyingSaveFile.data.add(username);
        }
        _flyingSaveFile.save(false);
    }

    public static boolean mustFlying(EntityPlayerMP player) {
        return _usersFlying.contains(player.getDisplayName());
    }

    public void toggleFlying(EntityPlayerMP player) {
        if (player.capabilities.allowFlying) {
            player.capabilities.allowFlying = false;
            player.sendPlayerAbilities();

            while (_usersFlying.contains(player.getDisplayName())) {
                _usersFlying.remove(player.getDisplayName());
            }
            saveConfig();

            player.addChatMessage(new ChatComponentText("§aFly Mode Deaktiviert"));
        } else {
            player.capabilities.allowFlying = true;
            player.sendPlayerAbilities();
            _usersFlying.add(player.getDisplayName());
            saveConfig();
            player.addChatMessage(new ChatComponentText("§aFly Mode Aktiviert"));
        }
    }


    public void processPlayer(EntityPlayerMP player, String[] astring) {
        if (astring.length == 0) {

            toggleFlying(player);
        } else {

            EntityPlayerMP target = PlayerManager.getPlayer(astring[0], true);

            if (target == null) {
                target = PlayerManager.getPossiblePlayer(astring[0]);
            }

            if (target != null) {
                if (target.capabilities.allowFlying) {
                    toggleFlying(target);
                    target.addChatMessage(
                        new ChatComponentText("§aFly Mode Aktiviert "));
                } else {
                    toggleFlying(target);
                    target.addChatMessage(
                        new ChatComponentText("§aFly Mode Deaktiviert "));
                }
            } else {
                player.addChatMessage(
                    new ChatComponentText("§aPlayer is Offline "));
            }

        }
    }


    public List addTabCompletionOptions(ICommandSender sender, String[] player) {
        return (player.length != 1 && player.length != 2) ? null
            : getListOfStringsMatchingLastWord(player,
                MinecraftServer.getServer().getAllUsernames());
    }
}

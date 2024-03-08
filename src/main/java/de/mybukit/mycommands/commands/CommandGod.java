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


public class CommandGod
    extends MyCommandBase {

    private static final SaveFile _godSaveFile = new SaveFile("god.txt",
        mycommands.getSaveDir() + "/mycommands/commands/");
    private static final ArrayList<String> _usersgod = new ArrayList<>();

    public CommandGod(boolean op) {
        this.name = "god";
        this.opOnly = op;
        this.usage = " [<PlayerName>]: You toggle your God capability or for other player's God capability";
    }

    public static void loadConfig() {
        _godSaveFile.load();
        _usersgod.clear();

        for (String info : _godSaveFile.data) {
            _usersgod.add(info);
        }
    }

    public static boolean mustbeGod(EntityPlayerMP player) {
        return _usersgod.contains(player.getDisplayName());
    }

    public static void saveConfig() {
        _godSaveFile.clear();
        for (String username : _usersgod) {
            _godSaveFile.data.add(username);
        }
        _godSaveFile.save(false);
    }

    public void togglegod(EntityPlayerMP player) {
        if (player.capabilities.disableDamage) {
            player.capabilities.disableDamage = false;
            player.setHealth(player.getMaxHealth());
            player.getFoodStats().addStats(20, 2.0F);

            player.sendPlayerAbilities();

            while (_usersgod.contains(player.getDisplayName())) {
                _usersgod.remove(player.getDisplayName());
            }
            saveConfig();

            player.addChatMessage(new ChatComponentText("§aGod Mode Deaktiviert"));
        } else {
            player.capabilities.disableDamage = true;
            player.setHealth(player.getMaxHealth());
            player.getFoodStats().addStats(20, 2.0F);

            player.sendPlayerAbilities();
            _usersgod.add(player.getDisplayName());
            saveConfig();
            player.addChatMessage(new ChatComponentText("§aGod Mode Aktiviert"));
        }
    }


    public String getCommandName() {
        return "god";
    }


    public void processPlayer(EntityPlayerMP player, String[] astring) {
        if (astring.length == 0) {

            togglegod(player);
        } else {

            EntityPlayerMP target = PlayerManager.getPlayer(astring[0], true);

            if (target == null) {
                target = PlayerManager.getPossiblePlayer(astring[0]);
            }

            if (target != null) {
                if (target.capabilities.disableDamage) {
                    togglegod(target);
                    target.addChatMessage(
                        new ChatComponentText("§aGod Mode Deaktiviert "));
                } else {
                    togglegod(target);
                    target.addChatMessage(
                        new ChatComponentText("§aGod Mode Aktiviert"));
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

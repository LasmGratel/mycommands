package de.mybukkit.mycommands.commands;

import com.google.common.collect.Lists;
import de.mybukkit.mycommands.helper.PlayerManager;
import java.util.List;
import net.minecraft.command.CommandGameMode;
import net.minecraft.command.ICommandSender;


public class CommandGamemode
    extends CommandGameMode {

    public boolean opOnly;

    public CommandGamemode(boolean op) {
        this.opOnly = op;
    }


    public List getCommandAliases() {
        return Lists.newArrayList((Object[]) new String[]{"gm", "mygamemode"});
    }


    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        if (this.opOnly) {
            return sender.canCommandSenderUseCommand(3, getCommandName());
        }
        return sender.canCommandSenderUseCommand(0, getCommandName());
    }
}

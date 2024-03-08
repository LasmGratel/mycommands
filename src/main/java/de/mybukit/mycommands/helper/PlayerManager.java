package de.mybukkit.mycommands.helper;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.management.UserListOps;


public class PlayerManager {

    public static String[] getPlayerList() {
        return Server.getConfigurationManager().getAllUsernames();
    }

    public static List getplayerList() {
        return (Server.getConfigurationManager()).playerEntityList;
    }


    public static EntityPlayerMP getPlayer(String name, boolean ignoreCase) {
        EntityPlayerMP player = null;

        for (String tempName : getPlayerList()) {

            if (ignoreCase) {

                if (tempName.equalsIgnoreCase(name)) {
                    player = Server.getConfigurationManager().func_152612_a(tempName);

                }
            } else if (tempName.equals(name)) {
                player = Server.getConfigurationManager().func_152612_a(tempName);
            }
        }

        return player;
    }


    public static EntityPlayerMP getPlayer(String name) {
        return getPlayer(name, false);
    }


    public static EntityPlayerMP getPossiblePlayer(String info) {
        EntityPlayerMP player = null;

        player = getPlayer(info);
        if (player != null) {
            return player;
        }
        player = getPlayer(info, false);
        if (player != null) {
            return player;
        }
        boolean isUnique = true;
        for (String name : getPlayerList()) {

            if (name.toLowerCase().contains(info.toLowerCase())) {
                if (player == null) {
                    player = getPlayer(name);
                } else {
                    isUnique = false;
                }
            }
        }
        if (isUnique) {
            return player;
        }
        return null;
    }


    public static boolean isOp(String playerName) {

        UserListOps test = Server.getConfigurationManager().func_152603_m();
        String[] test2 = test.func_152685_a();
        for (String op : test2) {

            if (playerName.equalsIgnoreCase(op)) {
                return true;
            }
        }

        return false;
    }

    public static class TeleportRequests {

        private static final HashMap<UUID, UUID> requests = new HashMap<>();

        public static void add(UUID target, UUID requester) {
            requests.put(target, requester);
        }

        public static boolean pending(UUID target) {
            return requests.containsKey(target);
        }

        public static void remove(UUID target) {
            if (pending(target)) {
                requests.remove(target);
            }
        }

        public static UUID fromWho(UUID target) {
            if (pending(target)) {
                return requests.get(target);
            }
            return null;
        }
    }

}

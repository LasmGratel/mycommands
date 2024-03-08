package de.mybukkit.mycommands.helper;


public class McColor {

    public static final String black = "§0";
    public static final String blue = "§1";
    public static final String darkGreen = "§2";
    public static final String darkAqua = "§3";
    public static final String darkRed = "§4";
    public static final String purple = "§5";
    public static final String gold = "§6";
    public static final String grey = "§7";
    public static final String darkGrey = "§8";
    public static final String indigo = "§9";
    public static final String green = "§a";
    public static final String aqua = "§b";
    public static final String red = "§c";
    public static final String pink = "§d";
    public static final String yellow = "§e";
    public static final String white = "§f";

    public static String replaceColorCodes(String line) {
        String temp = line;

        temp = replaceString(temp, "^black", "§0");
        temp = replaceString(temp, "^blue", "§1");
        temp = replaceString(temp, "^darkGreen", "§2");
        temp = replaceString(temp, "^darkAqua", "§3");
        temp = replaceString(temp, "^darkRed", "§4");
        temp = replaceString(temp, "^purple", "§5");
        temp = replaceString(temp, "^gold", "§6");
        temp = replaceString(temp, "^grey", "§7");
        temp = replaceString(temp, "^darkGrey", "§8");
        temp = replaceString(temp, "^indigo", "§9");
        temp = replaceString(temp, "^green", "§a");
        temp = replaceString(temp, "^aqua", "§b");
        temp = replaceString(temp, "^red", "§c");
        temp = replaceString(temp, "^pink", "§d");
        temp = replaceString(temp, "^yellow", "§e");
        temp = replaceString(temp, "^white", "§f");

        temp = replaceString(temp, "&0", "§0");
        temp = replaceString(temp, "&1", "§1");
        temp = replaceString(temp, "&2", "§2");
        temp = replaceString(temp, "&3", "§3");
        temp = replaceString(temp, "&4", "§4");
        temp = replaceString(temp, "&5", "§5");
        temp = replaceString(temp, "&6", "§6");
        temp = replaceString(temp, "&7", "§7");
        temp = replaceString(temp, "&8", "§8");
        temp = replaceString(temp, "&9", "§9");
        temp = replaceString(temp, "&a", "§a");
        temp = replaceString(temp, "&b", "§b");
        temp = replaceString(temp, "&c", "§c");
        temp = replaceString(temp, "&d", "§d");
        temp = replaceString(temp, "&e", "§e");
        temp = replaceString(temp, "&f", "§f");

        return temp;
    }


    public static String replaceString(String line, String target, String replacement) {
        String temp = line;

        while (temp.indexOf(target) >= 0) {

            String front = "";
            if (temp.indexOf(target) > 0) {
                front = temp.substring(0, temp.indexOf(target));
            }
            String back = "";
            if (temp.indexOf(target) + target.length() < temp.length()) {
                back = temp.substring(temp.indexOf(target) + target.length());
            }
            temp = front + replacement + back;
        }

        return temp;
    }
}

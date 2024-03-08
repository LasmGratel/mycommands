package de.mybukkit.mycommands.helper;

import net.minecraftforge.common.MinecraftForge;


public class Forge {

    public static void registerEventHandler(Object eventHandler) {
        MinecraftForge.EVENT_BUS.register(eventHandler);
    }
}

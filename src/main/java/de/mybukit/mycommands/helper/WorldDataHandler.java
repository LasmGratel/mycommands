package de.mybukkit.mycommands.helper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import java.io.File;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraftforge.event.world.WorldEvent;


public class WorldDataHandler {

    private static File dir;
    private String dir1;

    public static File dir() {
        return dir;
    }

    @SubscribeEvent
    public void onLoad(WorldEvent.Load event) {
        if (!event.world.isRemote && event.world.provider.dimensionId == 0) {
            ISaveHandler save = event.world.getSaveHandler();
            if (dir == null || !dir.toString().equals(save.getWorldDirectory().toString())) {
                dir = save.getWorldDirectory();
            }
        }

    }
}

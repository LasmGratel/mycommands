package de.mybukkit.mycommands.helper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


public class SimpleScheduler {

    private static long tick = 0L;
    private static final List<AbstractMap.SimpleEntry<Long, Runnable>> scheduledRunnables = new ArrayList<>();
    private static final HashMap<UUID, AbstractMap.SimpleEntry<Long, Runnable>> combatRunnables = new HashMap<>();

    public static void onTick() {
        tick++;
        scheduledRunnables.forEach(longRunnablePair -> {
            if (longRunnablePair.getKey().longValue() == tick) {
                longRunnablePair.getValue().run();
            }
        });
        combatRunnables.forEach((uuid, longRunnablePair) -> {
            if (longRunnablePair.getKey().longValue() == tick) {
                longRunnablePair.getValue().run();
            }
        });
    }

    public static void schedule(Long ticks, Runnable runnable) {
        scheduledRunnables.add(
            new AbstractMap.SimpleEntry<>(Long.valueOf(ticks.longValue() + tick + 1L), runnable));
    }

    public static void combatScheduler(Long ticks, Runnable runnable, UUID uuid) {
        combatRunnables.put(uuid,
            new AbstractMap.SimpleEntry<>(Long.valueOf(ticks.longValue() + tick + 1L), runnable));
    }


    @SubscribeEvent
    public void tickStart(TickEvent.ServerTickEvent event) {
        onTick();
    }
}

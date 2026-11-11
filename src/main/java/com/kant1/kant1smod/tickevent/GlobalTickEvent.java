package com.kant1.kant1smod.tickevent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class GlobalTickEvent {

    @SubscribeEvent
    public void onTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            GoForwardTick.tickEvent();
        }
        if (event.phase == TickEvent.Phase.END) {
            // GalliumTick.tick();

        }
    }

    @SubscribeEvent
    public void onTickClient(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            GalliumTickClient.tick();
        }
    }
}

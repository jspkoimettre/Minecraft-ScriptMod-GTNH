package com.myname.mymodid.tickevent;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S09PacketHeldItemChange;

public class GalliumTick {

    private enum Action {
        goToIron,
        goToCopperWire,
        goToGalliumNugget,
        drop,
        none,
    }

    private static int count = 0;
    private static Action nextStep = Action.goToIron;
    private static EntityPlayerMP player;
    private static Action lastAction = Action.none;
    private static Action lastLastAction = Action.none;

    public static void init(int nb, EntityPlayerMP sender) {
        player = sender;
        count = nb;
        nextStep = Action.goToIron;
    }

    public static void tick() {

        if (count == 0) {
            return;
        }
        switch (nextStep) {
            case goToIron:
                player.inventory.currentItem = 0;
                player.playerNetServerHandler.sendPacket(new S09PacketHeldItemChange(player.inventory.currentItem));
                nextStep = Action.drop;
                lastLastAction = lastAction;
                lastAction = Action.goToIron;
                break;
            case goToCopperWire:
                player.inventory.currentItem = 1;
                player.playerNetServerHandler.sendPacket(new S09PacketHeldItemChange(player.inventory.currentItem));
                nextStep = Action.drop;
                lastLastAction = lastAction;
                lastAction = Action.goToCopperWire;
                break;
            case goToGalliumNugget:
                player.inventory.currentItem = 2;
                player.playerNetServerHandler.sendPacket(new S09PacketHeldItemChange(player.inventory.currentItem));
                nextStep = Action.drop;
                lastLastAction = lastAction;
                lastAction = Action.goToGalliumNugget;
                break;
            case drop:
                player.dropOneItem(false);
                switch (lastAction) {
                    case goToIron:
                        nextStep = Action.goToCopperWire;
                        break;
                    case goToCopperWire:
                        if (lastLastAction == Action.goToCopperWire) {
                            nextStep = Action.goToGalliumNugget;
                        } else {
                            nextStep = Action.goToCopperWire;
                        }
                        break;
                    case goToGalliumNugget:
                        if (lastLastAction == Action.goToGalliumNugget) {
                            nextStep = Action.goToIron;
                            if (count > 0) {
                                count--;
                            }
                        } else {
                            nextStep = Action.goToGalliumNugget;
                        }
                        break;
                }
                break;
        }
    }
}

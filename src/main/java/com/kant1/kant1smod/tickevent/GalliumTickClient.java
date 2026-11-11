package com.kant1.kant1smod.tickevent;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.command.CommandException;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.ChatComponentText;

public class GalliumTickClient {

    private enum Action {
        Iron,
        CopperWire,
        GalliumNugget,
        Drop,
    }

    private static final Action[] pattern = { Action.Iron, Action.CopperWire, Action.CopperWire, Action.GalliumNugget,
        Action.GalliumNugget };
    private static EntityClientPlayerMP player;
    private static int count = 0;
    private static int iter;
    private static int ironSlot = -1;
    private static int copperWireSlot = -1;
    private static int galliumNuggetSlot = -1;
    private static boolean wasLastADrop;

    public static void init(EntityClientPlayerMP playerArg, int countArg) {

        ironSlot = -1;
        copperWireSlot = -1;
        galliumNuggetSlot = -1;

        player = playerArg;
        count = countArg;
        iter = 0;
        wasLastADrop = true;
        findMat();
    }

    public static void stop() {
        count = 0;
    }

    private static void findMat() {
        ItemStack itemStack;
        for (int i = 0; i < 9; i++) {
            itemStack = player.inventory.getStackInSlot(i);
            if (itemStack == null) {
                continue;
            }
            String name = itemStack.getItem()
                .getItemStackDisplayName(itemStack);
            switch (name) {
                case "Iron Ingot":
                    if (ironSlot == -1) {
                        ironSlot = i;
                    } else if (itemStack.stackSize > player.inventory.getStackInSlot(ironSlot).stackSize) {
                        ironSlot = i;
                    }
                    break;
                case "Fine Copper Wire":
                    if (copperWireSlot == -1) {
                        copperWireSlot = i;
                    } else if (itemStack.stackSize > player.inventory.getStackInSlot(copperWireSlot).stackSize) {
                        copperWireSlot = i;
                    }
                    break;
                case "Gallium Nugget":
                    if (galliumNuggetSlot == -1) {
                        galliumNuggetSlot = i;
                    } else if (itemStack.stackSize > player.inventory.getStackInSlot(galliumNuggetSlot).stackSize) {
                        galliumNuggetSlot = i;
                    }
                    break;
            }
        }
        if (ironSlot == -1 || copperWireSlot == -1 || galliumNuggetSlot == -1) {
            player.addChatMessage(new ChatComponentText("§c[Eror] Materials missing from HotBar"));
            throw new CommandException("Missing materials for the command");
        }
    }

    public static void tick() {

        if (count <= 0) {
            return;
        }

        if (wasLastADrop) {
            wasLastADrop = false;
            switch (pattern[iter]) {
                case Iron:
                    player.inventory.currentItem = ironSlot;
                    break;
                case CopperWire:
                    player.inventory.currentItem = copperWireSlot;
                    break;
                case GalliumNugget:
                    player.inventory.currentItem = galliumNuggetSlot;
                    break;
            }
            player.sendQueue.addToSendQueue(new C09PacketHeldItemChange(player.inventory.currentItem));
            if (iter < pattern.length - 1) {
                iter++;
            } else {
                iter = 0;
            }
        } else {
            player.sendQueue.addToSendQueue(new C07PacketPlayerDigging(4, 0, 0, 0, 0));
            wasLastADrop = true;
            if (iter == 0) {
                count--;
            }
        }
    }
}

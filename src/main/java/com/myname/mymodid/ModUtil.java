package com.myname.mymodid;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class ModUtil {

    public static EntityPlayerMP getPlayerByUsername(String username) {
        return MinecraftServer.getServer()
            .getConfigurationManager()
            .func_152612_a(username);
    }

    public static MovingObjectPosition rayTrace(double reach, float partialTicks, EntityPlayerMP player, World world) {
        Vec3 eyesPos = player.getPosition(partialTicks).addVector(0.0D, (double)player.getEyeHeight(), 0.0D);
        Vec3 lookVec = player.getLookVec();
        Vec3 reachVec = eyesPos.addVector(lookVec.xCoord * reach, lookVec.yCoord * reach, lookVec.zCoord * reach);
        return world.rayTraceBlocks(eyesPos, reachVec);
    }

    public static String itemStackToString(ItemStack itemStack) {
        if (itemStack == null) {
            return "null ";
        }
        try {
            return itemStack.stackSize + "x" + itemStack.getItem().getItemStackDisplayName(itemStack) + " ";
        }
        catch (NullPointerException e) {
            return "null pointer exception";
        }
    }

    public static ChatComponentText parseInventory(IInventory container) {
        StringBuilder inventory = new StringBuilder();
        for (int i = 0; i < container.getSizeInventory(); i++) {

           inventory.append(itemStackToString(container.getStackInSlot(i)));
        }
        return new ChatComponentText(inventory.toString());
    }

}

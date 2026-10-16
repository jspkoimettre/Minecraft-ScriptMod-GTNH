package com.myname.mymodid;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

/**
 * for methods still obfuscated
 */

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

}

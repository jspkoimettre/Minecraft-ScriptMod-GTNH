package com.myname.mymodid;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

/**
 * for methods still obfuscated
 */

public class UnObfuscated {

    public static EntityPlayerMP getPlayerByUsername(String username) {
        return MinecraftServer.getServer().getConfigurationManager().func_152612_a(username);
    }

}

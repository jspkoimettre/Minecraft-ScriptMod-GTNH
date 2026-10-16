package com.myname.mymodid.tickevent;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayerMP;

import com.myname.mymodid.ModUtil;

public class GoForwardTick {

    private static final HashMap<String, Integer> playerMovement = new HashMap<>();

    public static void addPlayerMovement(String playerName, int nbTick) {
        playerMovement.put(playerName, nbTick);
    }

    public static void tickEvent() {

        if (playerMovement.isEmpty()) {
            return;
        }

        Iterator<Map.Entry<String, Integer>> it = playerMovement.entrySet()
            .iterator();

        while (it.hasNext()) {
            Map.Entry<String, Integer> entry = it.next();
            int nbTick = entry.getValue();
            String playerName = entry.getKey();
            EntityPlayerMP player = ModUtil.getPlayerByUsername(playerName);

            if (player == null) {
                it.remove();
                continue;
            }

            // on fait bouger le joueur sur 1 tick
            float speed = 0.25F;
            double rad = Math.toRadians(player.rotationYaw);
            // player.setPositionAndUpdate(player.posX -= Math.sin(rad)*speed, player.posY, player.posZ +=
            // Math.cos(rad)*speed);

            player.motionX -= Math.sin(rad) * speed;
            player.motionZ += Math.cos(rad) * speed;
            player.playerNetServerHandler
                .sendPacket(new net.minecraft.network.play.server.S12PacketEntityVelocity(player));

            if (--nbTick == 0) {
                it.remove();
            } else {
                entry.setValue(nbTick);
            }
        }
    }
}

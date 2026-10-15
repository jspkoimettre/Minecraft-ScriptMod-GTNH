package com.myname.mymodid.tickevent;

import akka.japi.Pair;
import com.myname.mymodid.UnObfuscated;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.*;

public class GoForwardTick {

    private static final HashMap<String, Integer> playerMovement = new HashMap<>();


    public static void addPlayerMovement(String playerName, int nbTick){
        playerMovement.put(playerName, nbTick);
    }

    public static void tickEvent(){

        if (playerMovement.isEmpty()){ return; }


        Iterator<Map.Entry<String, Integer>> it = playerMovement.entrySet().iterator();

        while (it.hasNext()){
            Map.Entry<String, Integer> entry = it.next();
            int nbTick = entry.getValue();
            String playerName = entry.getKey();
            EntityPlayerMP player = UnObfuscated.getPlayerByUsername(playerName);

            if (player == null){ it.remove(); continue; }

            //on fait bouger le joueur sur 1 tick
            float speed = 0.1F;
            double rad = Math.toRadians(player.rotationYaw);
            //player.setPositionAndUpdate(player.posX -= Math.sin(rad)*speed, player.posY, player.posZ += Math.cos(rad)*speed);

            player.motionX -= Math.sin(rad)*speed;
            player.motionZ += Math.cos(rad)*speed;
            player.playerNetServerHandler.sendPacket(new net.minecraft.network.play.server.S12PacketEntityVelocity(player));

            if(--nbTick == 0){
                it.remove();
            }
            else{
                entry.setValue(nbTick);
            }
        }
    }
}

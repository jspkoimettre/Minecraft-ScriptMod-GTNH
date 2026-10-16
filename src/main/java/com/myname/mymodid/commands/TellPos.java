package com.myname.mymodid.commands;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;

import com.myname.mymodid.ModUtil;

/**
 * send in sender chat the x y z position of the player
 * if no player in arg, send sender's position if a player
 */

public class TellPos extends net.minecraft.command.CommandBase {

    private static final String commandName = "TellPos";
    private static final String commandUsage = "/TellPos <playerName>";

    public TellPos() {}

    @Override
    public String getCommandName() {
        return commandName;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return commandUsage;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        double posX, posY, posZ;
        if (args.length > 0) {
            EntityPlayerMP player = ModUtil.getPlayerByUsername(args[0]);
            if (player != null) {
                posX = player.posX;
                posY = player.posY;
                posZ = player.posZ;
            } else {
                throw new CommandException("No player found " + args[0]);
            }
        } else {
            EntityPlayerMP player = ModUtil.getPlayerByUsername(sender.getCommandSenderName());
            if (player != null) {
                posX = player.posX;
                posY = player.posY;
                posZ = player.posZ;
            } else {
                throw new CommandException("No player name specified but wasn't executed by a player ");
            }
        }

        ChatComponentText coordinates = new ChatComponentText("x:" + posX + " y:" + posY + " z:" + posZ);
        sender.addChatMessage(coordinates);
    }
}

package com.myname.mymodid.commands;

import com.myname.mymodid.UnObfuscated;
import com.myname.mymodid.tickevent.GoForwardTick;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;

public class GoForward extends net.minecraft.command.CommandBase{

    public static final String commandName = "GoForward";
    public static final String commandUsage = "/GoForward <int block>";

    public GoForward(){}

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
        if (args.length == 0){
            throw new WrongUsageException("this command needs an argument " + commandUsage);
        }
        int blocks;
        try {
            blocks = Integer.parseInt(args[0]);
        }
        catch (NumberFormatException e) {
            throw new WrongUsageException("the argument must be a positive integer " + commandUsage);
        }
        if (blocks <= 0){
            throw new WrongUsageException("the argument must be a strictly positive integer " + commandUsage);
        }
        String senderName = sender.getCommandSenderName();
        EntityPlayerMP player =  UnObfuscated.getPlayerByUsername(senderName);
        if (player != null) {
            GoForwardTick.addPlayerMovement(senderName, (int) Math.round(blocks/0.215));
        }

    }
}

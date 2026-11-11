package com.kant1.kant1smod.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;

import com.kant1.kant1smod.ModUtil;
import com.kant1.kant1smod.tickevent.GalliumTick;

public class Gallium extends net.minecraft.command.CommandBase {

    private static final String commandName = "Gallium";
    private static final String commandUsage = "/Gallium <int nb>";

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
        if (args.length == 0) {
            throw new WrongUsageException("this command needs an argument " + commandUsage);
        }
        int nb;
        try {
            nb = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            throw new WrongUsageException("the argument must be a positive integer " + commandUsage);
        }

        String senderName = sender.getCommandSenderName();
        EntityPlayerMP player = ModUtil.getPlayerByUsername(senderName);

        if (nb > 0) {
            GalliumTick.init(nb, player);
        } else {
            GalliumTick.init(0, player);
        }

    }
}

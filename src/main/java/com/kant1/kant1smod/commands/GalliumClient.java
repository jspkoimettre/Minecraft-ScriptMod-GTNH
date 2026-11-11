package com.kant1.kant1smod.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;

import com.kant1.kant1smod.tickevent.GalliumTickClient;

public class GalliumClient extends CommandBase {

    private static final String commandName = "GalliumClient";
    private static final String commandUsage = "/GalliumClient <int nb>";

    @Override
    public String getCommandName() {
        return commandName;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return commandUsage;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
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
        EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;

        if (nb > 0) {
            GalliumTickClient.init(player, nb);
        } else {
            GalliumTickClient.stop();
        }

    }
}

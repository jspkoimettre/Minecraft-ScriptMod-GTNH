package com.myname.mymodid.commands;

import com.myname.mymodid.ModUtil;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.management.ItemInWorldManager;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class OpenAndReadChest extends CommandBase {

    private static  final String commandName = "OpenAndReadChest";
    private static final String commandUsage = "/OpenAndReadChest";

    public OpenAndReadChest() {}

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
        if (sender instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP) sender;
            World world = player.getEntityWorld();

            MovingObjectPosition block = ModUtil.rayTrace(5.0D, 1.0F, player, world);
            if (block == null) {
                throw new CommandException("no block in range");
            }
            Vec3 blockHitVec = block.hitVec;
            int blockSide = block.sideHit;

            int blockCoordX = block.blockX;
            int blockCoordY = block.blockY;
            int blockCoordZ = block.blockZ;

            float blockHitVecX = (float) (blockHitVec.xCoord - blockCoordX);
            float blockHitVecY = (float) (blockHitVec.yCoord - blockCoordY);
            float blockHitVecZ = (float) (blockHitVec.zCoord - blockCoordZ);

            ItemInWorldManager itemInWorldManager = player.theItemInWorldManager;

            itemInWorldManager.activateBlockOrUseItem(player, world, player.getCurrentEquippedItem(), blockCoordX, blockCoordY, blockCoordZ, blockSide, blockHitVecX, blockHitVecY, blockHitVecZ);
            player.playerNetServerHandler.sendPacket(new net.minecraft.network.play.server.S23PacketBlockChange(blockCoordX, blockCoordY, blockCoordZ, world));
        }

    }
}

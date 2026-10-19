package com.myname.mymodid.commands;

import com.myname.mymodid.ModUtil;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.server.management.ItemInWorldManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldManager;

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

            MovingObjectPosition blockPos = ModUtil.rayTrace(5.0D, 1.0F, player, world);
            if (blockPos == null) {
                throw new CommandException("no block in range");
            }
            Vec3 blockHitVec = blockPos.hitVec;
            int blockSide = blockPos.sideHit;

            int blockCoordX = blockPos.blockX;
            int blockCoordY = blockPos.blockY;
            int blockCoordZ = blockPos.blockZ;

            TileEntity tileEntity = world.getTileEntity(blockCoordX, blockCoordY, blockCoordZ);

            if(!(tileEntity instanceof IInventory)){
                throw new CommandException("block is not a container");
            }
            float blockHitVecX = (float) (blockHitVec.xCoord - blockCoordX);
            float blockHitVecY = (float) (blockHitVec.yCoord - blockCoordY);
            float blockHitVecZ = (float) (blockHitVec.zCoord - blockCoordZ);

            ItemInWorldManager itemInWorldManager = player.theItemInWorldManager;

            itemInWorldManager.activateBlockOrUseItem(player, world, player.getCurrentEquippedItem(), blockCoordX, blockCoordY, blockCoordZ, blockSide, blockHitVecX, blockHitVecY, blockHitVecZ);

            player.playerNetServerHandler.sendPacket(new net.minecraft.network.play.server.S23PacketBlockChange(blockCoordX, blockCoordY, blockCoordZ, world));

            sender.addChatMessage(ModUtil.parseInventory((IInventory) tileEntity));
        }

    }
}

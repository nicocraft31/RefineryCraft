package me.nicocraft31.refinery.common.block.tileentity;

import me.nicocraft31.refinery.common.energy.IEnergyProvider;
import me.nicocraft31.refinery.common.energy.IEnergyGenerator;
import me.nicocraft31.refinery.common.energy.IEnergyTransmitter;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.management.PlayerChunkMapEntry;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class TileEntityUtil {
	public static float tooKRF(int rf)
	{
		float krf = rf / 1000;
		return krf;
	}

	public static float toMRF(int rf)
	{
		float mrf = rf / 1000000;
		return mrf;
	}
	
	public static void sendUpdateToAllPlayers(TileEntity tile)
	{
		WorldServer world = (WorldServer) tile.getWorld();
		PlayerChunkMapEntry entry = world.getPlayerChunkMap().getEntry(tile.getPos().getX() >> 4, tile.getPos().getZ() >> 4);

        if (entry == null) 
        	return;
	
        for(EntityPlayerMP p : entry.getWatchingPlayers())
        {
        	p.connection.sendPacket(tile.getUpdatePacket());
        }
	}

	public static int doEnergyInteraction(TileEntity src, TileEntity dest, EnumFacing srcFacing, int amount)
	{
		// Directly copied from ActuallyAditions' code:
		// https://github.com/Ellpeck/ActuallyAdditions/blob/12206a03a383a035758d6615c5da3ae32adaac81/src/main/java/de/ellpeck/actuallyadditions/mod/util/WorldUtil.java#L121
		
		if (amount > 0)
		{
            EnumFacing opp = srcFacing == null ? null : srcFacing.getOpposite();
            IEnergyStorage handlerFrom = src.getCapability(CapabilityEnergy.ENERGY, srcFacing);
            IEnergyStorage handlerTo = dest.getCapability(CapabilityEnergy.ENERGY, opp);
            if (handlerFrom != null && handlerTo != null)
            {
            	int drain = handlerFrom.extractEnergy(amount, true);
                if (drain > 0) {
                    int filled = handlerTo.receiveEnergy(drain, false);
                    handlerFrom.extractEnergy(filled, false);
                    return filled;
                }
            }
        }
	
		return 0;
	}
	
	public static int doEnergyInteractionWithoutConsuming(TileEntity src, TileEntity dest, EnumFacing srcFacing, int amount)
	{
		// Directly copied from ActuallyAditions' code:
		// https://github.com/Ellpeck/ActuallyAdditions/blob/12206a03a383a035758d6615c5da3ae32adaac81/src/main/java/de/ellpeck/actuallyadditions/mod/util/WorldUtil.java#L121
		
		if (amount > 0)
		{
            EnumFacing opp = srcFacing == null ? null : srcFacing.getOpposite();
            IEnergyStorage handlerFrom = src.getCapability(CapabilityEnergy.ENERGY, srcFacing);
            IEnergyStorage handlerTo = dest.getCapability(CapabilityEnergy.ENERGY, opp);
            if (handlerFrom != null && handlerTo != null)
            {
                 int filled = handlerTo.receiveEnergy(amount, false);
                 return filled;
            }
		}
	
		return 0;
	}

	public static void transmitUtil(TileEntity src, int transfer)
	{
		World world = src.getWorld();
		BlockPos pos = src.getPos();
		IEnergyTransmitter trans = (IEnergyTransmitter) src;
		IEnergyProvider prov = (IEnergyProvider) src;
		
		int blocks = 0;
		int totalAmount = 0;
		
		for(EnumFacing facing : EnumFacing.values())
		{
			BlockPos position = pos.offset(facing);
			IBlockState state = world.getBlockState(position);
			Block block = state.getBlock();
			
			if(block.hasTileEntity(state))
			{
				TileEntity tile = world.getTileEntity(position);
				if(tile instanceof IEnergyGenerator)
					continue;
				if(tile.hasCapability(CapabilityEnergy.ENERGY, facing.getOpposite()))
					blocks++;
			}
		}
		
		if(blocks*transfer > prov.getEnergy())
			totalAmount = prov.getEnergy() / blocks;
		else
			totalAmount = blocks*transfer;
		
		for(EnumFacing facing : EnumFacing.values())
		{
			BlockPos position = pos.offset(facing);
			IBlockState state = world.getBlockState(position);
			Block block = state.getBlock();
			
			if(block.hasTileEntity(state))
			{
				TileEntity tile = world.getTileEntity(position);
				if(!tile.hasCapability(CapabilityEnergy.ENERGY, facing.getOpposite()))
					continue;
				if(tile instanceof IEnergyGenerator)
					continue;
				if(trans.getRecievedCables().contains(tile.getPos()))
					continue;
				
				if(tile instanceof IEnergyTransmitter)
				{
					TileEntityUtil.markTransmitterAsVisitedWithoutConsuming(src, tile, facing, totalAmount);
					continue;
				}
				
				TileEntityUtil.doEnergyInteractionWithoutConsuming(src, tile, facing, totalAmount);
			}
		}
		
		prov.removeEnergy(totalAmount);
	}
	
	public static void markTransmitterAsVisited(TileEntity src, TileEntity dest, EnumFacing srcFacing, int amount)
	{
		IEnergyTransmitter destCable = (IEnergyTransmitter) dest;
		destCable.getRecievedCables().add(src.getPos());
		
		doEnergyInteraction(src, dest, srcFacing, amount);
	}
	
	public static void markTransmitterAsVisitedWithoutConsuming(TileEntity src, TileEntity dest, EnumFacing srcFacing, int amount)
	{
		IEnergyTransmitter destCable = (IEnergyTransmitter) dest;
		destCable.getRecievedCables().add(src.getPos());
		
		doEnergyInteractionWithoutConsuming(src, dest, srcFacing, amount);
	}
}
package me.nicocraft31.refinery.common.block.tileentity;

import me.nicocraft31.refinery.common.energy.ITransmitter;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.management.PlayerChunkMapEntry;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.WorldServer;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class TileEntityUtil {
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

	public static void doEnergyInteraction(TileEntity src, TileEntity dest, EnumFacing srcFacing, int amount)
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
                    return;
                }
            }
        }
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
            	int drain = handlerFrom.extractEnergy(amount, true);
                if (drain > 0) {
                    int filled = handlerTo.receiveEnergy(drain, false);
                    return filled;
                }
            }
        }
	
		return 0;
	}

	public static void markCableAsVisited(TileEntity src, TileEntity dest, EnumFacing srcFacing, int amount)
	{
		ITransmitter destCable = (ITransmitter) dest;
		destCable.getRecievedCables().add(src.getPos());
		
		doEnergyInteraction(src, dest, srcFacing, amount);
	}
	
	public static void markCableAsVisitedWithoutConsuming(TileEntity src, TileEntity dest, EnumFacing srcFacing, int amount)
	{
		ITransmitter destCable = (ITransmitter) dest;
		destCable.getRecievedCables().add(src.getPos());
		
		doEnergyInteractionWithoutConsuming(src, dest, srcFacing, amount);
	}
}
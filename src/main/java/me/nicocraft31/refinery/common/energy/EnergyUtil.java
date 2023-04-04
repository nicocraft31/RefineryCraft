package me.nicocraft31.refinery.common.energy;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class EnergyUtil {
	public static float toKRF(int rf)
	{
		float krf = rf / 1000;
		return krf;
	}

	public static float toMRF(int rf)
	{
		float mrf = rf / 1000000;
		return mrf;
	}
	
	public static float toGRF(int rf)
	{
		float grf = rf / 1000000000;
		return grf;
	}
	
	public static EnergyUnit getBestEnergyUnit(int rf)
	{
		if(rf < EnergyUnit.KRF_RATE)
		{
			return EnergyUnit.RF;
		}
		else if(rf >= EnergyUnit.KRF_RATE && rf < EnergyUnit.MRF_RATE)
		{
			return EnergyUnit.KRF;
		}
		else if(rf >= EnergyUnit.MRF_RATE)
		{
			return EnergyUnit.MRF;
		}
		
		return EnergyUnit.RF;
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
package me.nicocraft31.refinery.common.block.tileentity;

import me.nicocraft31.refinery.common.energy.IRecievingEnergyProvider;

public abstract class TileEntityEnergyRecieving extends TileEntityBasic implements IRecievingEnergyProvider {
	protected static int CONSUMING_AMOUNT = 100;
	protected boolean consuming = false;
	
	protected boolean consume()
	{
		int i = removeEnergy(CONSUMING_AMOUNT);
		return i != 0;
	}
}
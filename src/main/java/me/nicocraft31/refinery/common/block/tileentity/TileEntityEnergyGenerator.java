package me.nicocraft31.refinery.common.block.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public class TileEntityEnergyGenerator extends TileEntityGenerator {
	public TileEntityEnergyGenerator()
	{
		SHARE_AMOUNT = 100;
		
		ignoredTileEntities.add(this);
	}
	
	@Override
	public void tick()
	{
		if(world.isBlockPowered(pos))
		{
			addEnergy(SHARE_AMOUNT);
		}
		
		super.tick();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) 
	{
		super.readFromNBT(nbt);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) 
	{
		return super.writeToNBT(nbt);
	}
	
	@Override
	public EnumFacing[] getEnergySides() {
		return EnumFacing.values();
	}
}
package me.nicocraft31.refinery.common.block.tileentity;

import me.nicocraft31.refinery.common.energy.EnergyUtil;
import me.nicocraft31.refinery.common.energy.IEnergyGenerator;
import me.nicocraft31.refinery.common.energy.RefineryEnergyStorage;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.energy.CapabilityEnergy;

public class TileEntityCoalGenerator extends TileEntityBasic implements IEnergyGenerator {
	public TileEntityCoalGenerator()
	{
		storage = new RefineryEnergyStorage(290290, 0, 290290);
	}
	
	@Override
	public void tick()
	{
		if(world.isBlockPowered(pos))
			addEnergy(100);

		if(getEnergy() >= 100 && !this.world.isRemote)
			this.generateEnergy(100);
	}
	
	@Override
	public int generateEnergy(int amountForEachTileEntity) {
		int blocks = 0;
	
		for(EnumFacing facing : this.getGeneratingFacings())
		{
			BlockPos position = this.pos.offset(facing);
			IBlockState state = this.world.getBlockState(position);
			Block block = state.getBlock();
			
			if(block.hasTileEntity(state))
			{
				TileEntity tile = (TileEntity) this.world.getTileEntity(position);
				if(tile instanceof IEnergyGenerator)
					continue;
				
				if(tile.hasCapability(CapabilityEnergy.ENERGY, facing))
				{
					EnergyUtil.doEnergyInteractionWithoutConsuming(tile, tile, facing, amountForEachTileEntity);
					blocks++;
				}
			}
		}

		if(blocks != 0)
			return removeEnergy(amountForEachTileEntity);
			
		return 0;
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
	public EnumFacing[] getGeneratingFacings() {
		return EnumFacing.values();
	}
}
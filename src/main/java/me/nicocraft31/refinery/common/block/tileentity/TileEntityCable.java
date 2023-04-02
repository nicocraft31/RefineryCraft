package me.nicocraft31.refinery.common.block.tileentity;

import java.util.List;

import com.google.common.collect.Lists;

import me.nicocraft31.refinery.common.energy.IGenerator;
import me.nicocraft31.refinery.common.energy.ITransmitter;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.energy.CapabilityEnergy;

public class TileEntityCable extends TileEntityBasic implements ITransmitter {
	protected int TRANSFER_AMOUNT = 100;
	private final List<BlockPos> recievedCables = Lists.newArrayList();
	
	@Override
	public void tick() {
		transmit();
	}

	@Override
	public void transmit()
	{
		int blocks = 0;
		int totalAmount = 0;
		
		for(EnumFacing facing : EnumFacing.values())
		{
			BlockPos position = this.pos.offset(facing);
			IBlockState state = this.world.getBlockState(position);
			Block block = state.getBlock();
			
			if(block.hasTileEntity(state))
			{
				TileEntity tile = this.world.getTileEntity(position);
				if(tile instanceof IGenerator)
					continue;
				if(tile.hasCapability(CapabilityEnergy.ENERGY, facing.getOpposite()))
					blocks++;
			}
		}
		
		if(blocks*TRANSFER_AMOUNT > storage.getEnergy())
			totalAmount = storage.getEnergy() / blocks;
		else
			totalAmount = blocks*TRANSFER_AMOUNT;
		
		for(EnumFacing facing : EnumFacing.values())
		{
			BlockPos position = this.pos.offset(facing);
			IBlockState state = this.world.getBlockState(position);
			Block block = state.getBlock();
			
			if(block.hasTileEntity(state))
			{
				TileEntity tile = this.world.getTileEntity(position);
				if(!tile.hasCapability(CapabilityEnergy.ENERGY, facing.getOpposite()))
					continue;
				if(tile instanceof IGenerator)
					continue;
				if(this.recievedCables.contains(tile.getPos()))
					continue;
				
				if(tile instanceof ITransmitter)
				{
					TileEntityUtil.markCableAsVisitedWithoutConsuming(this, tile, facing, totalAmount);
					continue;
				}
				
				TileEntityUtil.doEnergyInteractionWithoutConsuming(this, tile, facing, totalAmount);
			}
		}
		
		removeEnergy(totalAmount);
	}
	
	@Override
	public List<BlockPos> getRecievedCables()
	{
		return recievedCables;
	}
}
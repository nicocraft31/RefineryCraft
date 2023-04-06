package me.nicocraft31.refinery.common.block.tileentity;

import java.util.List;

import com.google.common.collect.Lists;

import me.nicocraft31.refinery.common.energy.EnergyUtil;
import me.nicocraft31.refinery.common.energy.IEnergyGenerator;
import me.nicocraft31.refinery.common.energy.IEnergyTransmitter;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.energy.CapabilityEnergy;

public class TileEntityCable extends TileEntityBasic implements IEnergyTransmitter {
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
		int energy = getEnergy();
		
		if(energy == 0)
			return;
		
		for(EnumFacing facing : EnumFacing.values())
		{
			BlockPos position = this.pos.offset(facing);
			IBlockState state = world.getBlockState(position);
			Block block = state.getBlock();
			
			if(block.hasTileEntity(state))
			{
				TileEntity tile = this.world.getTileEntity(position);
				if(tile instanceof IEnergyGenerator)
					continue;
				if(tile.hasCapability(CapabilityEnergy.ENERGY, facing.getOpposite()))
					blocks++;
			}
		}
		
		if(blocks == 0)
			return;
		
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
				if(tile instanceof IEnergyGenerator)
					continue;
				if(this.recievedCables.contains(tile.getPos()))
					continue;
				
				int transfer = TRANSFER_AMOUNT;
				if(transfer > getEnergy())
				{
					transfer = getEnergy();
				}
				
				if(tile instanceof IEnergyTransmitter)
				{
					EnergyUtil.markTransmitterAsVisited(this, tile, facing, transfer);
					continue;
				}
				
				EnergyUtil.doEnergyInteraction(this, tile, facing, transfer);
			}
		}
	}
	
	@Override
	public List<BlockPos> getRecievedCables()
	{
		return recievedCables;
	}
}
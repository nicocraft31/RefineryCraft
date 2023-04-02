package me.nicocraft31.refinery.common.block.tileentity;

import java.util.List;

import com.google.common.collect.Lists;

import me.nicocraft31.refinery.common.energy.IEnergyGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;

public abstract class TileEntityGenerator extends TileEntityBasic implements IEnergyGenerator {
	protected static int SHARE_AMOUNT = 100;
	protected final List<TileEntity> ignoredTileEntities = Lists.newArrayList();
	
	@Override
	public void tick()
	{
		tryShareEnergy(world, pos);
	}
	
	protected int tryShareEnergy(World world, BlockPos pos)
	{
		if(!world.isRemote)
		{
			for(EnumFacing facing : this.getEnergySides())
			{
				BlockPos position = pos.offset(facing);
				IBlockState state = world.getBlockState(position);
				Block block = state.getBlock();
				
				if(block.hasTileEntity(state))
				{
					TileEntity tile = world.getTileEntity(position);
					
					if(ignoredTileEntities.contains(tile))
						return 0;
					
					if(tile.hasCapability(CapabilityEnergy.ENERGY, facing.getOpposite()))
					{
						TileEntityUtil.doEnergyInteraction(this, tile, facing, SHARE_AMOUNT);
					}
				}
			}
		}
		
		return 0;
	}
}
package me.nicocraft31.refinery.common.block.tileentity;

import java.util.List;

import com.google.common.collect.Lists;

import me.nicocraft31.refinery.common.energy.EnergyUtil;
import me.nicocraft31.refinery.common.energy.IEnergyGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.energy.CapabilityEnergy;

public class TileEntityWirelessTransciver extends TileEntityBasic {
	private final List<BlockPos> transmittingBlocks = Lists.newArrayList(); 
	
	@Override
	public void tick() {
		if(world.isBlockPowered(pos))
			addEnergy(100);
		
		for(BlockPos pos : transmittingBlocks)
		{
			IBlockState state = this.world.getBlockState(pos);
			Block block = state.getBlock();
			
			if(block.hasTileEntity(state))
			{
				TileEntity tile = this.world.getTileEntity(pos);
				if(tile instanceof IEnergyGenerator)
					continue;

				boolean flag = false;
				for(EnumFacing f : EnumFacing.values())
				{
					if(flag)
						continue;
					
					if(tile.hasCapability(CapabilityEnergy.ENERGY, f))
					{
						int i = EnergyUtil.doEnergyInteraction(this, tile, f, 100);
						if(i > 0)
							flag = true;
					}
				}
			}
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		NBTTagList list = new NBTTagList();
		
		int i = 0;
		for(BlockPos blockpos : transmittingBlocks)
		{
			NBTTagCompound temp = new NBTTagCompound();
			int x = blockpos.getX();
			int y = blockpos.getY();
			int z = blockpos.getZ();
			temp.setInteger("x", x);
			temp.setInteger("y", y);
			temp.setInteger("z", z);
			
			list.appendTag(temp);
			i++;
		}
		
		nbt.setTag("pos", list);
		nbt.setInteger("numpos", i);
		return super.writeToNBT(nbt);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		NBTTagList positions = (NBTTagList) nbt.getTag("pos");
		int i = nbt.getInteger("numpos");
		
		for(int j = 0; j < i; j++)
		{
			NBTTagCompound temp = (NBTTagCompound) positions.get(j);
			int x = temp.getInteger("x");
			int y = temp.getInteger("y");
			int z = temp.getInteger("z");
		
			BlockPos pos = new BlockPos(x, y, z);
			transmittingBlocks.set(j, pos);
		}
		
		super.readFromNBT(nbt);
	}
	
	public List<BlockPos> getTransmittingBlocks()
	{
		return this.transmittingBlocks;
	}
}
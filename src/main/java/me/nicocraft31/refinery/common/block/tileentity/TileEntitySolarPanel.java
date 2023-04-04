package me.nicocraft31.refinery.common.block.tileentity;

import me.nicocraft31.refinery.common.energy.EnergyUtil;
import me.nicocraft31.refinery.common.energy.IEnergyGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.energy.CapabilityEnergy;

/**
 * 
 * Yoinked code from ActuallyAdditions' code.
 * https://github.com/Ellpeck/ActuallyAdditions/blob/1.12.2/src/main/java/de/ellpeck/actuallyadditions/mod/tile/TileEntityFurnaceSolar.java
 *
 */
public class TileEntitySolarPanel extends TileEntityBasic implements IEnergyGenerator {
	@Override
	public void tick() {
		if(!this.world.isRemote)
		{
			int i = getPowerToGenerate(8);
			int power = addEnergy(i);
			
			if(power > 0)
				this.generateEnergy(power);
		}
	}

	@Override
	public int generateEnergy(int amountForEachTileEntity) {
		if(getEnergy() <= 0 || getEnergy() < amountForEachTileEntity)
			return 0;
		
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
		
		if(blocks > 0)
			removeEnergy(amountForEachTileEntity);
		
		return 0;
	}
	
	public int getPowerToGenerate(int power) {
        for (int y = 1; y <= this.world.getHeight() - this.pos.getY(); y++) {
            if (power > 0) {
                BlockPos pos = this.pos.up(y);
                IBlockState state = this.world.getBlockState(pos);

                if (state.getMaterial().isOpaque()) {
                    power = 0;
                } else if (!state.getBlock().isAir(state, this.world, pos)) {
                    power--;
                }
            } else {
                break;
            }
        }

        return power;
    }
	
	@Override
	public EnumFacing[] getGeneratingFacings() {
		return EnumFacing.values();
	}
}
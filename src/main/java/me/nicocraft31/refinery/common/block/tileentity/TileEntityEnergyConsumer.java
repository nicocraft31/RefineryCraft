package me.nicocraft31.refinery.common.block.tileentity;

import net.minecraft.util.EnumFacing;

public class TileEntityEnergyConsumer extends TileEntityEnergyRecieving {
	@Override
	public void tick() {
		if(this.world.isBlockPowered(pos))
			consume();
	}
	
	public EnumFacing[] getEnergySides() {
		return EnumFacing.values();
	}
}
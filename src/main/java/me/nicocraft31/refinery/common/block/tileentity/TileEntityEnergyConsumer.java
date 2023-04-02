package me.nicocraft31.refinery.common.block.tileentity;

import me.nicocraft31.refinery.common.energy.IConsumer;
import net.minecraft.util.EnumFacing;

public class TileEntityEnergyConsumer extends TileEntityBasic implements IConsumer {
	private final int CONSUMING_AMOUNT = 100;
	
	@Override
	public void tick() {
		if(this.world.isBlockPowered(pos))
			consume();
	}
	
	@Override
	public boolean consume()
	{
		int i = removeEnergy(CONSUMING_AMOUNT);
		return i != 0;
	}
	
	@Override
	public EnumFacing[] getConsumeSides() {
		return EnumFacing.values();
	}
}
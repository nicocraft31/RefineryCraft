package me.nicocraft31.refinery.common.energy;

import net.minecraft.util.EnumFacing;

public interface IConsumer extends IEnergyProvider {
	public boolean consume();
	public EnumFacing[] getConsumeSides();
}
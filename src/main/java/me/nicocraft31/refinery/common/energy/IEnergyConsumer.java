package me.nicocraft31.refinery.common.energy;

import net.minecraft.util.EnumFacing;

public interface IEnergyConsumer extends IEnergyProvider {
	public boolean consume();
	public EnumFacing[] getConsumeSides();
}
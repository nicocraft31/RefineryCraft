package me.nicocraft31.refinery.common.energy;

import net.minecraft.util.EnumFacing;

public interface IEnergyGenerator extends IEnergyProvider {
	public EnumFacing[] getEnergySides();
}
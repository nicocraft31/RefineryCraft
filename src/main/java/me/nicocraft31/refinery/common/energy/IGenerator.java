package me.nicocraft31.refinery.common.energy;

import net.minecraft.util.EnumFacing;

public interface IGenerator extends IEnergyProvider {
	public EnumFacing[] getEnergySides();
}
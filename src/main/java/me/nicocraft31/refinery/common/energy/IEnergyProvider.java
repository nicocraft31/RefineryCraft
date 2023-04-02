package me.nicocraft31.refinery.common.energy;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.IEnergyStorage;

public interface IEnergyProvider {
	public IEnergyStorage getStorage(EnumFacing facing);
}
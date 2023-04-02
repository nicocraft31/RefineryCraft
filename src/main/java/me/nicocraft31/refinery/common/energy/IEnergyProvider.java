package me.nicocraft31.refinery.common.energy;

import net.minecraftforge.energy.IEnergyStorage;

public interface IEnergyProvider {
	public IEnergyStorage getStorage();
	public int addEnergy(int amount);
	public int removeEnergy(int amount);
	public int getEnergy();
}
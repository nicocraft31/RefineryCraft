package me.nicocraft31.refinery.common.energy;

public interface IEnergyProvider {
	public RefineryEnergyStorage getStorage();
	public int addEnergy(int amount);
	public int removeEnergy(int amount);
	public int getEnergy();
}
package me.nicocraft31.refinery.common.energy;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.energy.EnergyStorage;

public class RefineryEnergyStorage extends EnergyStorage {
	public RefineryEnergyStorage(int capacity, int maxReceive, int maxExtract) {
		super(capacity, maxReceive, maxExtract, 0);
	}
	
	public RefineryEnergyStorage(int capacity, int maxReceive) {
		super(capacity, maxReceive, 0, 0);
	}
	
	public RefineryEnergyStorage(int capacity) {
		super(capacity, capacity, capacity, 0);
	}
	
	public RefineryEnergyStorage(int capacity, int maxReceive, int maxExtract, int energy) {
		super(capacity, maxReceive, maxExtract, energy);
	}

	@Override
	public int receiveEnergy(int amount, boolean simulate)
	{
		if(!canReceive())
			return 0;
		
		int simulate_amount = Math.min(capacity - energy, Math.min(this.maxReceive, amount));
		
		if(!simulate)
			setEnergy(this.energy + simulate_amount);
		return simulate_amount;
	}
	
	public int receiveEnergyNoChecks(int amount)
	{
		int simulate_amount = Math.min(capacity - energy, amount);
		setEnergy(this.energy + simulate_amount);
		return simulate_amount;
	}
	
	@Override
	public int extractEnergy(int amount, boolean simulate)
	{
		if(!canExtract()) 
			return 0;
		
		int simulate_amount = Math.min(this.energy, Math.min(this.maxExtract, amount));
		
		if(!simulate)
			setEnergy(this.energy - simulate_amount);
		return simulate_amount;
	}
	
	public int extractEnergyNoChecks(int amount)
	{
		int simulate_amount = Math.min(this.energy, amount);
		setEnergy(this.energy - simulate_amount);
		return simulate_amount;
	}
	
	@Override
	public boolean canReceive()
    {
        return this.maxReceive > 0;
    }
	
	@Override
	public boolean canExtract() 
	{
		return this.maxExtract > 0;
	}
	
	public int setEnergy(int amount)
	{
		this.energy = amount;
		return amount;
	}
	
	public int addEnergy(int amount)
	{
		return receiveEnergyNoChecks(amount);
	}
	
	public int removeEnergy(int amount)
	{
		return extractEnergyNoChecks(amount);
	}

	public int getEnergy()
	{
		return this.energy;
	}
	
	public void readFromNBT(NBTTagCompound nbt)
	{
		this.energy = nbt.getInteger("Energy");
	}

	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		nbt.setInteger("Energy", energy);
		
		return nbt;
	}
}
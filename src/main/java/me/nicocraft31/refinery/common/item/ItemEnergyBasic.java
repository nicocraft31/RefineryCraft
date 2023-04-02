package me.nicocraft31.refinery.common.item;

import me.nicocraft31.refinery.common.energy.RefineryEnergyStorage;
import me.nicocraft31.refinery.common.energy.IEnergyProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public abstract class ItemEnergyBasic extends ItemBasic implements IEnergyProvider {
	public ItemEnergyBasic(String name) {
		super(name);
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		return new EnergyCapabilityProvider(stack, this);
	}

	public IEnergyStorage getStorage(EnumFacing facing)
	{
		return null;
	}
	
	public class EnergyCapabilityProvider implements ICapabilityProvider
	{
		public final RefineryEnergyStorage storage;
		
		public EnergyCapabilityProvider(ItemStack stack, ItemEnergyBasic item)
		{
			this.storage = new RefineryEnergyStorage(290290)
			{
			};
		}

		@Override
		public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
			return getCapability(capability, facing) != null;
		}
		
		@Override
		public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
			if(capability == CapabilityEnergy.ENERGY) 
				return CapabilityEnergy.ENERGY.cast(getStorage(facing));
			
			return null;
		}
	}
}
package me.nicocraft31.refinery.common.item;

import java.util.List;

import java.text.NumberFormat;

import me.nicocraft31.refinery.common.energy.RefineryEnergyStorage;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 
 * Yet again, just yoinked from Actually Aditions' code.
 * https://github.com/Ellpeck/ActuallyAdditions/blob/1.12.2/src/main/java/de/ellpeck/actuallyadditions/mod/items/ItemDrill.java
 *
 */

public abstract class ItemEnergyBasic extends ItemBasic {
	public int capacity = 0, transfer = 0;
	
	public ItemEnergyBasic(String name, int capacity, int transfer) {
		super(name);
	
		this.capacity = capacity;
		this.transfer = transfer;
		
		this.setHasSubtypes(true);
        this.setMaxStackSize(1);
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		return new EnergyCapabilityProvider(stack, this);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		NumberFormat num = NumberFormat.getInstance();
		tooltip.add(String.format("Energy: %s/%s RF", num.format(getEnergyStored(stack)), num.format(capacity)));
		
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
	
	@Override
    public void getSubItems(CreativeTabs tabs, NonNullList<ItemStack> list) {
        if (this.isInCreativeTab(tabs)) {
            ItemStack stackFull = new ItemStack(this);
            if (stackFull.hasCapability(CapabilityEnergy.ENERGY, null)) {
                IEnergyStorage storage = stackFull.getCapability(CapabilityEnergy.ENERGY, null);
                if (storage != null) {
                    this.setEnergy(stackFull, storage.getMaxEnergyStored());
                    list.add(stackFull);
                }
            }

            ItemStack stackEmpty = new ItemStack(this);
            this.setEnergy(stackEmpty, 0);
            list.add(stackEmpty);
        }
    }

	public void setEnergy(ItemStack stack, int energy) {
        if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if (storage instanceof RefineryEnergyStorage) {
                ((RefineryEnergyStorage) storage).setEnergy(energy);
            }
        }
    }

    public int getEnergyStored(ItemStack stack) {
        if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if (storage != null) { return storage.getEnergyStored(); }
        }
        return 0;
    }

    public int getMaxEnergyStored(ItemStack stack) {
        if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if (storage != null) { return storage.getMaxEnergyStored(); }
        }
        return 0;
    }
	
	@Override
    public boolean showDurabilityBar(ItemStack itemStack) {
        return getEnergyStored(itemStack) != getMaxEnergyStored(itemStack);
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if (storage != null) {
                double maxAmount = storage.getMaxEnergyStored();
                double energyDif = maxAmount - storage.getEnergyStored();
                return energyDif / maxAmount;
            }
        }
        return super.getDurabilityForDisplay(stack);
    }
	
    @Override
    public int getMetadata(int damage) {
        return damage;
    }
    
    @Override
    public int getDamage(ItemStack stack)
    {
    	return getEnergyStored(stack);
    };
    
    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {
        return false;
    }
    
	private static class EnergyCapabilityProvider implements ICapabilityProvider
	{
		public final RefineryEnergyStorage storage;
		
		public EnergyCapabilityProvider(ItemStack stack, ItemEnergyBasic item)
		{
			this.storage = new RefineryEnergyStorage(item.capacity, item.transfer, item.transfer)
			{
				@Override
                public int getEnergyStored() {
                    if (stack.hasTagCompound())
                        return stack.getTagCompound().getInteger("Energy");
                    
                    return 0;
                }
				
				@Override
				public int receiveEnergy(int amount, boolean simulate) {
					if(this.energy + amount >= this.capacity)
						return 0;
					
					return super.receiveEnergy(amount, simulate);
				}
				
				@Override
				public int setEnergy(int energy) {
					this.energy = energy;
					
					if (!stack.hasTagCompound())
                        stack.setTagCompound(new NBTTagCompound());

                    stack.getTagCompound().setInteger("Energy", energy);
				
                    return energy;
				}
			};
		}

		@Override
		public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
			return getCapability(capability, facing) != null;
		}
		
		@Override
		public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
			if(capability == CapabilityEnergy.ENERGY) 
				return CapabilityEnergy.ENERGY.cast(storage);
			
			return null;
		}
	}
}
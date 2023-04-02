package me.nicocraft31.refinery.common.block.tileentity;

import me.nicocraft31.refinery.RefineryCraft;
import me.nicocraft31.refinery.common.energy.RefineryEnergyStorage;
import me.nicocraft31.refinery.common.energy.IEnergyProvider;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public abstract class TileEntityBasic extends TileEntity implements ITickable, IEnergyProvider {
	protected RefineryEnergyStorage storage = new RefineryEnergyStorage(290290);
	
	public void update() {tick();}
	public abstract void tick();

	public int addEnergy(int amount) {
		return storage.addEnergy(amount);
	}
	public int removeEnergy(int amount) {
		return storage.removeEnergy(amount);
	}
	
	public void sendUpdate()
	{
		RefineryCraft.LOGGER.info("Just sent update to client.");
		TileEntityUtil.sendUpdateToAllPlayers(this);
	}
	
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		storage.readFromNBT(nbt);
	}
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		return storage.writeToNBT(nbt);
	}
	
	@Override
	public void markDirty() {
		super.markDirty();
	
		sendUpdate();
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return getCapability(capability, facing) != null;
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityEnergy.ENERGY) 
			return CapabilityEnergy.ENERGY.cast(getStorage(facing));
		
		return super.getCapability(capability, facing);
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);
        return new SPacketUpdateTileEntity(this.pos, -1, nbt);
	}
	
	@Override
	public IEnergyStorage getStorage(EnumFacing facing)
	{
		return storage;
	}
}
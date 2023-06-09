package me.nicocraft31.refinery.common.energy;

import java.util.List;

import net.minecraft.util.math.BlockPos;

public interface IEnergyTransmitter extends IEnergyProvider {
	public void transmit();
	public List<BlockPos> getRecievedCables();
}
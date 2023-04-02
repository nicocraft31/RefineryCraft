package me.nicocraft31.refinery.common.energy;

import java.util.List;

import net.minecraft.util.math.BlockPos;

public interface ITransmitter extends IEnergyProvider {
	void transmitEnergy();
	public List<BlockPos> getRecievedCables();
}
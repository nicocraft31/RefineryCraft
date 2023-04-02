package me.nicocraft31.refinery.common.block;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;

public class RefineryBlocks {
	public static final List<Block> BLOCKS = Lists.newArrayList();
	public static Block COPPER_ORE = null;
	public static Block CABLE = null;
	public static Block ENERGY_GENERATOR = null;
	public static Block ENERGY_CONSUMER = null;
	
	public static void init()
	{
		COPPER_ORE = addBlock(new BlockCopperOre());
		CABLE = addBlock(new BlockCable());
		ENERGY_GENERATOR = addBlock(new BlockEnergyGenerator());
		ENERGY_CONSUMER = addBlock(new BlockEnergyConsumer());
	}
	
	public static Block addBlock(Block block) {BLOCKS.add(block); return block;}
}
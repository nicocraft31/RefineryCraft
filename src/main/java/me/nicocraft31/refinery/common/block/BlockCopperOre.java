package me.nicocraft31.refinery.common.block;

import me.nicocraft31.refinery.RegistryUtil;
import net.minecraft.block.BlockOre;

public class BlockCopperOre extends BlockOre {
	public BlockCopperOre()
	{
		RegistryUtil.helpBlock(this, "copper_ore");
	}
}
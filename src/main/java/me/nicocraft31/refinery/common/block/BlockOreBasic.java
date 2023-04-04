package me.nicocraft31.refinery.common.block;

import me.nicocraft31.refinery.RegistryUtil;
import net.minecraft.block.BlockOre;

public abstract class BlockOreBasic extends BlockOre {
	public String name;
	
	public BlockOreBasic(String name)
	{
		this.name = name;

		RegistryUtil.helpBlock(this, name);
	}
}
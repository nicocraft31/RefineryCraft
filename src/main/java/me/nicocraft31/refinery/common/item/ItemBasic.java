package me.nicocraft31.refinery.common.item;

import me.nicocraft31.refinery.RefineryCraft;
import me.nicocraft31.refinery.RegistryUtil;
import net.minecraft.item.Item;

public abstract class ItemBasic extends Item {
	private String name;
	private String unlocalizedName;
	
	public ItemBasic(String name)
	{
		this.name = name;
		this.unlocalizedName = RefineryCraft.MODID + "." + name;
		
		RegistryUtil.helpItem(this, name);
	}

	public String getName()
	{
		return this.name;
	}

	public String getUnlocalizedName()
	{
		return this.unlocalizedName;
	}
}
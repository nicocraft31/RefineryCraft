package me.nicocraft31.refinery.common.item;

import java.util.List;

import org.lwjgl.input.Keyboard;

import me.nicocraft31.refinery.RefineryCraft;
import me.nicocraft31.refinery.RegistryUtil;
import me.nicocraft31.refinery.client.LocalizationHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class ItemBasic extends Item {
	protected String name;
	private boolean hasShiftDescription = this instanceof IShiftableInformation;
	
	public ItemBasic(String name)
	{
		this.name = name;
		RegistryUtil.helpItem(this, name);
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if(!hasShiftDescription)
			return;
			
		boolean shift = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);
		if(!tooltip.isEmpty())
			tooltip.add("");
		
		if(shift)
		{
			tooltip.add(((IShiftableInformation)this).getShiftDescription());
		}
		else
		{
			tooltip.add(LocalizationHelper.format("tooltip."+RefineryCraft.MODID+".shiftdesc"));
		}
	}
	
	public boolean hasShiftDescription()
	{
		return this.hasShiftDescription;
	}
}
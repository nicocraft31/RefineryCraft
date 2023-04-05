package me.nicocraft31.refinery.common.block;

import java.util.List;

import org.lwjgl.input.Keyboard;

import me.nicocraft31.refinery.RefineryCraft;
import me.nicocraft31.refinery.RegistryUtil;
import me.nicocraft31.refinery.client.ClientUtil;
import me.nicocraft31.refinery.common.item.IShiftableInformation;
import net.minecraft.block.BlockOre;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class BlockOreBasic extends BlockOre {
	public String name;
	private boolean hasShiftDescription = this instanceof IShiftableInformation;
	
	public BlockOreBasic(String name)
	{
		this.name = name;

		RegistryUtil.helpBlock(this, name);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if(!hasShiftDescription)
			return;
			
		boolean shift = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);
		IShiftableInformation info = (IShiftableInformation) this; 
		
		if(info.hasSpace())
			tooltip.add("");
		
		if(shift)
		{
			tooltip.add(info.getShiftDescription());
		}
		else
		{
			tooltip.add(ClientUtil.format("tooltip."+RefineryCraft.MODID+".shiftdesc"));
		}
	}

	public boolean hasShiftDescription()
	{
		return this.hasShiftDescription;
	}
}
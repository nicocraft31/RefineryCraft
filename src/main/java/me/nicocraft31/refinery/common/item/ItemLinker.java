package me.nicocraft31.refinery.common.item;

import java.util.List;

import me.nicocraft31.refinery.client.ClientUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemLinker extends ItemBasic {
	private BlockPos pos = null;
	
	public ItemLinker() {
		super("wireless_linker");
	}

	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		if(stack.hasTagCompound())
		{
			NBTTagCompound nbt = stack.getTagCompound();
			NBTTagCompound block = (NBTTagCompound) nbt.getTag("Block");
			int x = block.getInteger("x");
			int y = block.getInteger("y");
			int z = block.getInteger("z");
			
			pos = new BlockPos(x, y, z);
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if(pos != null)
		{
			int x = pos.getX(), y = pos.getY(), z = pos.getZ();
			tooltip.add(String.format(ClientUtil.format("item.refinery.wireless_linker.tooltip") + " %d, %d, %d", x, y, z));
		}
		
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
	
	public void onShiftRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand)
	{
		if(!world.isRemote)
		{
			NBTTagCompound nbt = null;
			
			if(!stack.hasTagCompound())
				nbt = new NBTTagCompound();
			else
				nbt = stack.getTagCompound();
			
			NBTTagCompound block = new NBTTagCompound();
			int x = pos.getX(), y = pos.getY(), z = pos.getZ();
			block.setInteger("x", x);
			block.setInteger("y", y);
			block.setInteger("z", z);
			nbt.setTag("Block", block);
			
			ClientUtil.sendStatusMessage(player, ClientUtil.format("item.refinery.wireless_linker.success"));
		}
	}
	
	public void setBlockPos(BlockPos pos)
	{
		this.pos = pos;
	}

	public BlockPos getPos()
	{
		return this.pos;
	}
}
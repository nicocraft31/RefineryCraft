package me.nicocraft31.refinery.common.item;

import java.util.List;

import me.nicocraft31.refinery.client.ClientUtil;
import me.nicocraft31.refinery.common.block.BlockWirelessTransciver;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;

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
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ActionResult<ItemStack> result = new ActionResult<ItemStack>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));

		if(!playerIn.isSneaking())
			return result; 
		
		ItemStack stack = playerIn.getHeldItem(handIn);
		World world = worldIn;
		if(world.isRemote)
			return result;
		RayTraceResult hover_object = Minecraft.getMinecraft().objectMouseOver;
		if(hover_object == null)
			return result;
		
		BlockPos pos = hover_object.getBlockPos();
		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		if(block instanceof BlockWirelessTransciver)
			return result;
		
		if(!block.hasTileEntity(state))
			return result;
		
		int count = 0;
		for(EnumFacing f : EnumFacing.values())
		{
			if(world.getTileEntity(pos).hasCapability(CapabilityEnergy.ENERGY, f))
				count++;
		}
		if(count <= 0)
			return result;
		
		ItemLinker vinculer = (ItemLinker) stack.getItem();
		vinculer.setBlockPos(pos);
		vinculer.onShiftRightClick(stack, world, playerIn, handIn);
		
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
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
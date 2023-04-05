package me.nicocraft31.refinery.client;

import me.nicocraft31.refinery.common.block.BlockWirelessTransciver;
import me.nicocraft31.refinery.common.item.ItemLinker;
import me.nicocraft31.refinery.common.item.RefineryItems;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(Side.CLIENT)
public class ClientUtil {
	public static String format(String key)
	{
		return I18n.format(key);
	}

	/**
	 * Copied from Cyclic:
	 * https://github.com/Lothrazar/Cyclic/blob/a1a5435f6dcff99c25d9afbb374bc0b735999d5f/src/main/java/com/lothrazar/cyclicmagic/util/UtilChat.java#L153 
	 */
	public static void sendStatusMessage(EntityPlayer player, String string) {
		player.sendStatusMessage(new TextComponentTranslation(string), true);
	}
	
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent.RightClickBlock e)
	{
		EntityPlayer player = e.getEntityPlayer();
		if(!player.isSneaking())
			return;
		
		ItemStack stack = e.getItemStack();
		if(stack.getItem() == RefineryItems.WIRELESS_VINCULER)
		{
			World world = e.getWorld();
			if(world.isRemote)
				return;
			BlockPos pos = e.getPos();
			IBlockState state = world.getBlockState(pos);
			Block block = state.getBlock();
			if(block instanceof BlockWirelessTransciver)
				return;
			
			if(!block.hasTileEntity(state))
				return;
			
			int count = 0;
			for(EnumFacing f : EnumFacing.values())
			{
				if(world.getTileEntity(pos).hasCapability(CapabilityEnergy.ENERGY, f))
					count++;
			}
			if(count <= 0)
				return;
			
			ItemLinker vinculer = (ItemLinker) stack.getItem();
			vinculer.setBlockPos(pos);
			vinculer.onShiftRightClick(stack, world, player, e.getHand());
		}
	}
}
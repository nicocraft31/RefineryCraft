package me.nicocraft31.refinery.common.block;

import me.nicocraft31.refinery.client.ClientUtil;
import me.nicocraft31.refinery.common.block.tileentity.TileEntityWirelessTransciver;
import me.nicocraft31.refinery.common.item.ItemLinker;
import me.nicocraft31.refinery.common.item.RefineryItems;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockWirelessTransciver extends BlockBasic {
	public BlockWirelessTransciver() {
		super(Material.ROCK, "wireless");
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote)
		{
			if(playerIn.getHeldItem(playerIn.getActiveHand()).getItem() == RefineryItems.WIRELESS_VINCULER)
			{
				ItemLinker linker = (ItemLinker) playerIn.getHeldItem(playerIn.getActiveHand()).getItem();
				BlockPos position = linker.getPos();
				if(position == null)
					return false;
				
				TileEntityWirelessTransciver te = (TileEntityWirelessTransciver) worldIn.getTileEntity(pos);
				if(te.getTransmittingBlocks().contains(position))
					return false;
				te.getTransmittingBlocks().add(position);
				ClientUtil.sendStatusMessage(playerIn, ClientUtil.format("tile.refinery.wireless.success"));
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityWirelessTransciver();
	}
}
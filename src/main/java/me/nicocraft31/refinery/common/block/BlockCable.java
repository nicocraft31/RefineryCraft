package me.nicocraft31.refinery.common.block;

import me.nicocraft31.refinery.RegistryUtil;
import me.nicocraft31.refinery.common.block.tileentity.TileEntityCable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockCable extends Block {
	public BlockCable()
	{
		super(Material.ROCK);
		RegistryUtil.helpBlock(this, "cable");
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityCable();
	}
}
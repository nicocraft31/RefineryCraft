package me.nicocraft31.refinery.common.block;

import me.nicocraft31.refinery.RegistryUtil;
import me.nicocraft31.refinery.common.block.tileentity.TileEntityTestConsumer;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockEnergyConsumer extends Block {
	public BlockEnergyConsumer()
	{
		super(Material.ROCK);
		RegistryUtil.helpBlock(this, "energy_consumer");
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityTestConsumer();
	}
}
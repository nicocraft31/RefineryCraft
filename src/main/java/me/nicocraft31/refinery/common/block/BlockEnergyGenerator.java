package me.nicocraft31.refinery.common.block;

import me.nicocraft31.refinery.RegistryUtil;
import me.nicocraft31.refinery.common.block.tileentity.TileEntityCoalGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockEnergyGenerator extends Block {
	public BlockEnergyGenerator()
	{
		super(Material.ROCK);
		RegistryUtil.helpBlock(this, "energy_generator");
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityCoalGenerator();
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
}
package me.nicocraft31.refinery.common.block;

import me.nicocraft31.refinery.common.block.tileentity.TileEntitySolarPanel;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockSolarPanel extends BlockBasic {
	public BlockSolarPanel() {
		super(Material.ROCK, "solar_panel");
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntitySolarPanel();
	}
}
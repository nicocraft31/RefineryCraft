package me.nicocraft31.refinery.common.block;

import me.nicocraft31.refinery.common.block.tileentity.TileEntityWirelessTransciver;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockWirelessTransciver extends BlockBasic {
	public BlockWirelessTransciver() {
		super(Material.ROCK, "wireless");
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
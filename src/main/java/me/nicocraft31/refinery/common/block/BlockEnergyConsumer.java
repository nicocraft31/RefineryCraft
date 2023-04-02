package me.nicocraft31.refinery.common.block;

import me.nicocraft31.refinery.client.LocalizationHelper;
import me.nicocraft31.refinery.common.block.tileentity.TileEntityTestConsumer;
import me.nicocraft31.refinery.common.item.IShiftableInformation;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockEnergyConsumer extends BlockBasic implements IShiftableInformation {
	public BlockEnergyConsumer()
	{
		super(Material.ROCK, "energy_consumer");
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityTestConsumer();
	}

	@Override
	public boolean hasSpace() {
		return false;
	}
	
	@Override
	public String getShiftDescription() {
		return LocalizationHelper.format(IShiftableInformation.getShiftLocalization(name));
	}
}
package me.nicocraft31.refinery.common.block;

import me.nicocraft31.refinery.client.ClientUtil;
import me.nicocraft31.refinery.common.block.tileentity.TileEntityCoalGenerator;
import me.nicocraft31.refinery.common.item.IShiftableInformation;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockEnergyGenerator extends BlockBasic implements IShiftableInformation {
	public BlockEnergyGenerator()
	{
		super(Material.ROCK, "energy_generator");
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityCoalGenerator();
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public boolean hasSpace() {
		return false;
	}
	
	@Override
	public String getShiftDescription() {
		return ClientUtil.format(IShiftableInformation.getShiftLocalization(name));
	}
}
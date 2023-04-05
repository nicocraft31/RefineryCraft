package me.nicocraft31.refinery.common.item;

import me.nicocraft31.refinery.client.ClientUtil;

public class ItemBattery extends ItemEnergyBasic implements IShiftableInformation {
	public ItemBattery() {
		super("battery", 50000, 50000);
	}

	@Override
	public boolean hasSpace() {
		return true;
	}
	
	public String getShiftDescription() {
		return ClientUtil.format(IShiftableInformation.getShiftLocalization(name));
	}
}
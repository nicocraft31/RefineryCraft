package me.nicocraft31.refinery.common.item;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.item.Item;

public class RefineryItems {
	public static final List<Item> ITEMS = Lists.newArrayList();

	public static Item COPPER_INGOT = null;
	public static Item COPPER_PLATE = null;
	public static Item COPPER_CABLE = null;
	public static Item BATTERY = null;
	
	public static void init()
	{
		COPPER_INGOT = addItem(new ItemCopperIngot());
		COPPER_PLATE = addItem(new ItemCopperPlate());
		COPPER_CABLE = addItem(new ItemCopperCable());
		BATTERY = addItem(new ItemBattery());
	}
	
	public static Item addItem(Item item) {ITEMS.add(item); return item;}
}
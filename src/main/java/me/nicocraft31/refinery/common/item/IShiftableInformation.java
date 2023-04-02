package me.nicocraft31.refinery.common.item;

import me.nicocraft31.refinery.RefineryCraft;

public interface IShiftableInformation {
	public String getShiftDescription();
	
	public static String getShiftLocalization(String name)
	{
		return "shiftdesc."+ RefineryCraft.MODID + "." + name;
	}
}
package me.nicocraft31.refinery.common.item;

import me.nicocraft31.refinery.RefineryCraft;

/**
 * 
 * ANOTHER time (3 times now) I stealed this from some poor random dude.
 * This time is from Galacticraft.
 *
 */

public interface IShiftableInformation {
	public String getShiftDescription();
	public boolean hasSpace();
	
	public static String getShiftLocalization(String name)
	{
		return "shiftdesc."+ RefineryCraft.MODID + "." + name;
	}
}
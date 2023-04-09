package me.nicocraft31.refinery.client;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(Side.CLIENT)
public class ClientUtil {
	public static String format(String key)
	{
		return I18n.format(key);
	}

	/**
	 * Copied from Cyclic:
	 * https://github.com/Lothrazar/Cyclic/blob/a1a5435f6dcff99c25d9afbb374bc0b735999d5f/src/main/java/com/lothrazar/cyclicmagic/util/UtilChat.java#L153 
	 */
	public static void sendStatusMessage(EntityPlayer player, String string) {
		player.sendStatusMessage(new TextComponentTranslation(string), true);
	}
}
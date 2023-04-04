package me.nicocraft31.refinery.common.block.tileentity;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.management.PlayerChunkMapEntry;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;

public class TileEntityUtil {
	/**
	 * 
	 * I also copied this from someone's code but I can't even remember who I copied from.
	 * 
	 */
	public static void sendUpdateToAllPlayers(TileEntity tile)
	{
		WorldServer world = (WorldServer) tile.getWorld();
		PlayerChunkMapEntry entry = world.getPlayerChunkMap().getEntry(tile.getPos().getX() >> 4, tile.getPos().getZ() >> 4);

        if (entry == null) 
        	return;
	
        for(EntityPlayerMP p : entry.getWatchingPlayers())
        {
        	p.connection.sendPacket(tile.getUpdatePacket());
        }
	}
}
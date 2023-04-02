package me.nicocraft31.refinery;

import org.apache.logging.log4j.Logger;

import me.nicocraft31.refinery.common.block.RefineryBlocks;
import me.nicocraft31.refinery.common.item.RefineryItems;
import me.nicocraft31.refinery.common.proxy.Proxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = RefineryCraft.MODID, name = RefineryCraft.NAME)
public class RefineryCraft {
	public static final String MODID = "refinery";
	public static final String NAME = "Refinery Mod";
	
	@Instance
	public static RefineryCraft instance;

	@SidedProxy(clientSide = "me.nicocraft31.refinery.client.proxy.ClientProxy",
			serverSide = "me.nicocraft31.refinery.server.proxy.ServerProxy")
	public static Proxy proxy;
	
	public static Logger LOGGER;

	public static final CreativeTabs REFINERY_MAIN = new CreativeTabs(RefineryCraft.MODID) {
		public ItemStack getTabIconItem() {
			return new ItemStack(Item.getItemFromBlock(RefineryBlocks.COPPER_ORE));
		}
	};
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e)
	{
		LOGGER = e.getModLog();
		
		RefineryBlocks.init();
		RefineryItems.init();
		RegistryUtil.registerOreDicts();
		MinecraftForge.EVENT_BUS.register(new RegistryUtil());

		proxy.preInit(e);
	}

	@EventHandler
	public void init(FMLInitializationEvent e)
	{
		RegistryUtil.registerGuis();
		RegistryUtil.registerFurnaceRecipes();
		RegistryUtil.registerCraftingRecipes();
		
		proxy.init(e);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e)
	{
		proxy.postInit(e);
	}
}
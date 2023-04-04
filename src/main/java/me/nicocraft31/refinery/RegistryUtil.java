package me.nicocraft31.refinery;

import me.nicocraft31.refinery.client.gui.GuiHandler;
import me.nicocraft31.refinery.common.block.RefineryBlocks;
import me.nicocraft31.refinery.common.block.tileentity.*;
import me.nicocraft31.refinery.common.item.*;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

@EventBusSubscriber
public class RegistryUtil {
	public static void helpBlock(Block block, String name)
	{
		block.setRegistryName(RefineryCraft.MODID, name);
		block.setUnlocalizedName(RefineryCraft.MODID + "." + name);
		block.setCreativeTab(RefineryCraft.REFINERY_MAIN);
		
		RefineryItems.ITEMS.add(getItemBlockFromBlock(block));
	}
	
	public static Item getItemBlockFromBlock(Block block)
	{
		ItemBlock itemblock = new ItemBlock(block);
		
		return itemblock.setRegistryName(block.getRegistryName()).setUnlocalizedName(block.getLocalizedName()).setCreativeTab(RefineryCraft.REFINERY_MAIN);
	}
	
	public static void helpItem(ItemBasic item, String name)
	{
		item.setRegistryName(RefineryCraft.MODID, name);
		item.setUnlocalizedName(RefineryCraft.MODID + "." + name);
		item.setCreativeTab(RefineryCraft.REFINERY_MAIN);
	}
	
	public static void registerFurnaceRecipes()
	{
		GameRegistry.addSmelting(RefineryBlocks.COPPER_ORE, new ItemStack(RefineryItems.COPPER_INGOT), 10f);
	}

	public static void registerCraftingRecipes()
	{
	}
	
	public static void registerGuis()
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(RefineryCraft.instance, new GuiHandler());
	}
	
	public static void registerTileEntities()
	{
		GameRegistry.registerTileEntity(TileEntityCoalGenerator.class, new ResourceLocation(RefineryCraft.MODID, "energy_generator"));
		GameRegistry.registerTileEntity(TileEntityCable.class, new ResourceLocation(RefineryCraft.MODID, "cable"));
		GameRegistry.registerTileEntity(TileEntityTestConsumer.class, new ResourceLocation(RefineryCraft.MODID, "energy_consumer"));
		GameRegistry.registerTileEntity(TileEntitySolarPanel.class, new ResourceLocation(RefineryCraft.MODID, "solar_panel"));
	}
	
	public static void registerOreDicts()
	{
		OreDictionary.registerOre("oreCopper", RefineryBlocks.COPPER_ORE);
		OreDictionary.registerOre("ingotCopper", RefineryItems.COPPER_INGOT);
		OreDictionary.registerOre("plateCopper", RefineryItems.COPPER_PLATE);
	}
	
	public static void registerWorldGeneration()
	{
	}
	
	@SubscribeEvent
	public void onBlockRegister(Register<Block> e)
	{
		for(Block b : RefineryBlocks.BLOCKS)
		{
			e.getRegistry().register(b);
		}
		
		registerTileEntities();
	}

	@SubscribeEvent
	public void onItemRegister(Register<Item> e)
	{
		for(Item i : RefineryItems.ITEMS)
		{
			e.getRegistry().register(i);
		}
	}
	
	@SubscribeEvent
	public void onModelRegister(ModelRegistryEvent e)
	{
		for(Item i : RefineryItems.ITEMS)
		{
			ModelLoader.setCustomModelResourceLocation(i, 0, 
					new ModelResourceLocation(i.getRegistryName(), "inventory"));
		}
	
		for(Block i : RefineryBlocks.BLOCKS)
		{
			Item item = Item.getItemFromBlock(i);
			ModelLoader.setCustomModelResourceLocation(item, 0, 
					new ModelResourceLocation(item.getRegistryName(), "inventory"));
		}
	}
}
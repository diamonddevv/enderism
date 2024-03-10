package dev.diamond.enderism;

import dev.diamond.enderism.impl.GeneralModCompat;
import dev.diamond.enderism.network.InitPackets;
import dev.diamond.enderism.registry.*;
import dev.diamond.enderism.item.CharmItem;
import dev.diamond.enderism.item.CursedChorusItem;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.diamonddev.libgenetics.common.api.v1.interfaces.BlockRegistryHelper.getBlockItem;
import static net.minecraft.item.ItemGroups.*;

public class EnderismMod implements ModInitializer {
	public static final String modid = "enderism";
	private static final String version = buildVersionString(modid);
	public static final Logger logger = LoggerFactory.getLogger(modid);

	@Override
	public void onInitialize() {

		// Start Registration
		long startTime = System.currentTimeMillis();
		//

		new InitConfig().register();
		new InitEvents().register();
		new InitPackets().register();
		new InitBlocks().register();
		new InitItems().register();
		new InitDamageSources().register();
		new InitEffects().register();
		new InitEnchants().register();
		new InitGamerules().register();
		new InitPotions().register();
		new InitSoundEvents().register();
		new InitResourceListener().register();
		new InitDataModifiers().register();
		new InitAdvancementCriteria().register();
		new InitCommands().register();
		new ItemGroupEditor().register();

		GeneralModCompat.checkInstalls();
		//
		double time = System.currentTimeMillis() - startTime;
		// Finish Registration

		logger.info("Everything from mod " + modid + " (with version '" + version + "') has been loaded! Have Fun! (Time Elapsed: " + time + " milliseconds)");
	}

	public static Identifier id(String path) {
		return new Identifier(modid, path);
	}



	public static class ItemGroupEditor implements RegistryInitializer {
		@Override
		public void register() {
			place();
		}


		private static void place() {
			ItemGroupEvents.modifyEntriesEvent(INGREDIENTS).register(content -> {
				content.addAfter(Items.NETHERITE_INGOT, InitItems.ANCIENT_SCRAP);
				content.addAfter(InitItems.ANCIENT_SCRAP, InitItems.PIRPELL_FRAGMENT);
				content.addAfter(InitItems.PIRPELL_FRAGMENT, InitItems.PIRPELL_INGOT);
				content.addAfter(InitItems.PIRPELL_INGOT, InitItems.SCULK_SPINE);
				content.addAfter(InitItems.SCULK_SPINE, InitItems.IONISED_IRON_INGOT);

				content.addAfter(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, InitItems.PIRPELL_PLATED_TOOL_UPGRADE);
				content.addAfter(InitItems.PIRPELL_PLATED_TOOL_UPGRADE, InitItems.ANCIENT_PLATED_ARMOR_UPGRADE);
			});

			ItemGroupEvents.modifyEntriesEvent(BUILDING_BLOCKS).register(content -> {
				content.addAfter(Items.NETHERITE_BLOCK, getBlockItem(InitBlocks.PIRPELL_BLOCK));

				content.addAfter(getBlockItem(InitBlocks.PIRPELL_BLOCK), getBlockItem(InitBlocks.PATTERNED_PIRPELL));
				content.addAfter(getBlockItem(InitBlocks.PATTERNED_PIRPELL), getBlockItem(InitBlocks.IONISED_IRON_BLOCK));
				//content.add(getBlockItem(InitBlocks.BAMBOO_SPIKE));
			});

			ItemGroupEvents.modifyEntriesEvent(FUNCTIONAL).register(content -> {
				content.addAfter(Items.LODESTONE, getBlockItem(InitBlocks.CHORUS_MAGNETITE));
				content.addAfter(getBlockItem(InitBlocks.CHORUS_MAGNETITE), getBlockItem(InitBlocks.FIBROUS_CHORUS));
			});

			ItemGroupEvents.modifyEntriesEvent(FOOD_AND_DRINK).register(content -> {
				CursedChorusItem.addCursedChorus(content, InitItems.CURSED_CHORUS);
			});

			ItemGroupEvents.modifyEntriesEvent(TOOLS).register(content -> {
				content.addAfter(Items.GOAT_HORN, InitItems.PURPUR_FLUTE);
				content.addAfter(InitItems.PURPUR_FLUTE, InitItems.CHORUS_CELLO);
				content.addAfter(InitItems.CHORUS_CELLO, InitItems.VIBRATOTAMATONE);

				content.addAfter(Items.NETHERITE_HOE, InitItems.PIRPELL_NETHERITE_SHOVEL);
				content.addAfter(InitItems.PIRPELL_NETHERITE_SHOVEL, InitItems.PIRPELL_NETHERITE_PICK);
				content.addAfter(InitItems.PIRPELL_NETHERITE_PICK, InitItems.PIRPELL_NETHERITE_AXE);
				content.addAfter(InitItems.PIRPELL_NETHERITE_AXE, InitItems.PIRPELL_NETHERITE_HOE);
			});

			ItemGroupEvents.modifyEntriesEvent(COMBAT).register(content -> {
				content.addAfter(Items.NETHERITE_SWORD, InitItems.PIRPELL_NETHERITE_SWORD);
				content.addAfter(Items.NETHERITE_AXE, InitItems.PIRPELL_NETHERITE_AXE);

				content.addAfter(Items.NETHERITE_BOOTS, InitItems.ANCIENT_NETHERITE_HELMET);
				content.addAfter(InitItems.ANCIENT_NETHERITE_HELMET, InitItems.ANCIENT_NETHERITE_CHESTPLATE);
				content.addAfter(InitItems.ANCIENT_NETHERITE_CHESTPLATE, InitItems.ANCIENT_NETHERITE_LEGGINGS);
				content.addAfter(InitItems.ANCIENT_NETHERITE_LEGGINGS, InitItems.ANCIENT_NETHERITE_BOOTS);

				content.addAfter(Items.TURTLE_HELMET, InitItems.SHULKER_SHELLMET);

				content.add(InitItems.LIGHTNING_BOTTLE);
				content.add(InitItems.STATIC_CORE);

				CharmItem.addAllCharms(content, InitItems.WANDERERS_CHARM);
				CharmItem.addAllCharms(content, InitItems.ENDSTONE_CHARM);
				CharmItem.addAllCharms(content, InitItems.PURPUR_CHARM);
				CharmItem.addAllCharms(content, InitItems.OBSIDIAN_CHARM);
			});
		}
	}

	public static String buildVersionString(String modid) {
		return FabricLoader.getInstance().getModContainer(modid).orElseThrow().getMetadata().getVersion().getFriendlyString();
	}
}

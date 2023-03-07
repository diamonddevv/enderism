package net.diamonddev.enderism;

import net.diamonddev.enderism.item.CharmItem;
import net.diamonddev.enderism.item.CursedChorusItem;
import net.diamonddev.enderism.network.InitPackets;
import net.diamonddev.enderism.registry.*;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.diamonddev.libgenetics.common.api.v1.interfaces.BlockRegistryHelper.getBlockItem;
import static net.minecraft.item.ItemGroups.*;

public class EnderismMod implements ModInitializer {
	public static final String modid = "enderism";
	private static final String version = FabricLoaderImpl.INSTANCE.getModContainer(modid).orElseThrow().getMetadata().getVersion().getFriendlyString();
	public static final Logger logger = LoggerFactory.getLogger(modid);

	@Override
	public void onInitialize() {

		// Start Registration
		long startTime = System.currentTimeMillis();
		//

		new InitConfig().register();

		new InitPackets().register();

		new InitBlocks().register();
		new InitItems().register();
		new InitEffects().register();
		new InitEnchants().register();
		new InitGamerules().register();
		new InitPotions().register();
		new InitSoundEvents().register();
		new InitResourceListener().register();


		new ItemGroupEditor().register();

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
			ItemGroupEvents.modifyEntriesEvent(FUNCTIONAL).register(content -> {
				content.addAfter(Items.LODESTONE, getBlockItem(InitBlocks.CHORUS_MAGNETITE));
				content.addAfter(getBlockItem(InitBlocks.CHORUS_MAGNETITE), getBlockItem(InitBlocks.FIBROUS_CHORUS));
			});

			ItemGroupEvents.modifyEntriesEvent(FOOD_AND_DRINK).register(content -> {
				CursedChorusItem.addCursedChorus(content, InitItems.CURSED_CHORUS);

				content.addAfter(InitItems.CURSED_CHORUS, getBlockItem(InitBlocks.CHORUS_FRUIT_PIE));
			});

			ItemGroupEvents.modifyEntriesEvent(TOOLS).register(content -> {

			});

			ItemGroupEvents.modifyEntriesEvent(COMBAT).register(content -> {
				content.addAfter(Items.TURTLE_HELMET, InitItems.SHULKER_SHELLMET);

				CharmItem.addAllCharms(content, InitItems.ENDSTONE_CHARM);
				CharmItem.addAllCharms(content, InitItems.PURPUR_CHARM);
				CharmItem.addAllCharms(content, InitItems.OBSIDIAN_CHARM);
			});
		}
	}
}

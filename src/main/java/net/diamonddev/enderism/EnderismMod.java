package net.diamonddev.enderism;

import net.diamonddev.enderism.item.CharmItem;
import net.diamonddev.enderism.item.CursedChorusItem;
import net.diamonddev.enderism.network.InitPackets;
import net.diamonddev.enderism.registry.*;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.Version;
import org.quiltmc.loader.impl.QuiltLoaderImpl;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.diamonddev.libgenetics.common.api.v1.interfaces.BlockRegistryHelper.getBlockItem;
import static net.minecraft.item.ItemGroups.*;

public class EnderismMod implements ModInitializer {
	public static final String modid = "enderism";
	private static final String version = buildVersionString(modid);
	public static final Logger logger = LoggerFactory.getLogger(modid);

	@Override
	public void onInitialize(ModContainer modContainer) {

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
		new InitDataModifiers().register();

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
			ItemGroupEvents.modifyEntriesEvent(FUNCTIONAL_BLOCKS).register(content -> {
				content.addAfter(Items.LODESTONE, getBlockItem(InitBlocks.CHORUS_MAGNETITE));
				content.addAfter(getBlockItem(InitBlocks.CHORUS_MAGNETITE), getBlockItem(InitBlocks.FIBROUS_CHORUS));
			});

			ItemGroupEvents.modifyEntriesEvent(FOOD_AND_DRINKS).register(content -> {
				CursedChorusItem.addCursedChorus(content, InitItems.CURSED_CHORUS);

//				content.addAfter(InitItems.CURSED_CHORUS, getBlockItem(InitBlocks.CHORUS_FRUIT_PIE));
			});

			ItemGroupEvents.modifyEntriesEvent(TOOLS_AND_UTILITIES).register(content -> {
				content.addAfter(Items.GOAT_HORN, InitItems.PURPUR_FLUTE);
				content.addAfter(InitItems.PURPUR_FLUTE, InitItems.CHORUS_CELLO);
				content.addAfter(InitItems.CHORUS_CELLO, InitItems.VIBRATOTAMATONE);
			});

			ItemGroupEvents.modifyEntriesEvent(COMBAT).register(content -> {
				content.addAfter(Items.TURTLE_HELMET, InitItems.SHULKER_SHELLMET);

				CharmItem.addAllCharms(content, InitItems.WANDERERS_CHARM);
				CharmItem.addAllCharms(content, InitItems.ENDSTONE_CHARM);
				CharmItem.addAllCharms(content, InitItems.PURPUR_CHARM);
				CharmItem.addAllCharms(content, InitItems.OBSIDIAN_CHARM);
			});
		}
	}

	public static String buildVersionString(String modid) {
		Version.Semantic semantic = QuiltLoaderImpl.INSTANCE.getModContainer(modid).orElseThrow().metadata().version().semantic();
		return String.format("%s.%s.%s", semantic.major(), semantic.minor(), semantic.patch());
	}
}

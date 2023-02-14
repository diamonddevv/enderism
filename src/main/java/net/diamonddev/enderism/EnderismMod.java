package net.diamonddev.enderism;

import net.diamonddev.enderism.item.CharmItem;
import net.diamonddev.enderism.registry.*;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.diamonddev.enderism.registry.BlockInit.getBlockItem;
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

		new BlockInit().register();
		new ItemInit().register();
		new EffectInit().register();
		new EnchantInit().register();
		new GameruleInit().register();
		new PotionInit().register();
		new SoundEventInit().register();


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
				content.addAfter(Items.LODESTONE, getBlockItem(BlockInit.CHORUS_MAGNETITE));
				content.addAfter(getBlockItem(BlockInit.CHORUS_MAGNETITE), getBlockItem(BlockInit.FIBROUS_CHORUS));
			});

			ItemGroupEvents.modifyEntriesEvent(FOOD_AND_DRINK).register(content -> {
				content.addAfter(Items.CHORUS_FRUIT, ItemInit.CURSED_CHORUS);
				content.addAfter(ItemInit.CURSED_CHORUS, getBlockItem(BlockInit.CHORUS_FRUIT_PIE));
			});

			ItemGroupEvents.modifyEntriesEvent(COMBAT).register(content -> {
				CharmItem.addAllCharms(content, ItemInit.CHARM);
			});
		}
	}
}

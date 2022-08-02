package net.diamonddev.enderism;

import net.diamonddev.enderism.init.BlockInit;
import net.diamonddev.enderism.init.EnchantInit;
import net.diamonddev.enderism.init.ItemInit;
import net.diamonddev.enderism.init.SoundEventInit;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnderismMod implements ModInitializer {
	public static final String modid = "enderism";
	public static final Logger logger = LoggerFactory.getLogger(modid);

	@Override
	public void onInitialize() {
		new ItemInit().register();
		new BlockInit().register();
		new EnchantInit().register();
		new SoundEventInit().register();
	}
}

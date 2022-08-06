package net.diamonddev.enderism;

import net.diamonddev.enderism.init.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnderismMod implements ModInitializer {
	public static final String modid = "enderism";
	private static final String version = FabricLoaderImpl.INSTANCE.getModContainer(modid).orElseThrow().getMetadata().getVersion().getFriendlyString();
	public static final Logger logger = LoggerFactory.getLogger(modid);

	@Override
	public void onInitialize() {
		logger.info("Initialising " + modid + " with version " + version);
		long startTime = System.currentTimeMillis();

		new ItemInit().register();
		new BlockInit().register();
		new EnchantInit().register();
		new GameruleInit().register();
		new SoundEventInit().register();

		double time = System.currentTimeMillis() - startTime;
		logger.info("Everything from " + modid + " has been loaded! Have Fun! (Time Elapsed: " + time + " milliseconds)");
	}
}

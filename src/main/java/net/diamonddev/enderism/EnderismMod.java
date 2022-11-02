package net.diamonddev.enderism;

import net.diamonddev.enderism.init.*;
import net.diamonddev.enderism.integration.Integrations;
import net.diamonddev.libgenetics.common.api.v1.util.IdentifierBuilder;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnderismMod implements ModInitializer {
	public static final String modid = "enderism";
	private static final String version = FabricLoaderImpl.INSTANCE.getModContainer(modid).orElseThrow().getMetadata().getVersion().getFriendlyString();
	public static final Logger logger = LoggerFactory.getLogger(modid);

	public static IdentifierBuilder id = new IdentifierBuilder("enderism");

	@Override
	public void onInitialize() {
		logger.info("Initialising " + modid + " with version " + version);

		// Start Registration
		long startTime = System.currentTimeMillis();
		//
		new Integrations().register();

		new BlockInit().register();
		new ItemInit().register();
		new EffectInit().register();
		new EnchantInit().register();
		new GameruleInit().register();
		new PotionInit().register();
		new SoundEventInit().register();

		//
		double time = System.currentTimeMillis() - startTime;
		// Finish Registration

		logger.info("Everything from " + modid + " has been loaded! Have Fun! (Time Elapsed: " + time + " milliseconds)");
	}
}

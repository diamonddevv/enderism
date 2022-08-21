package net.diamonddev.enderism;

import net.diamonddev.enderism.api.AbstractModIntegration;
import net.diamonddev.enderism.api.IdentifierBuilder;
import net.diamonddev.enderism.init.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.diamonddev.enderism.api.AbstractModIntegration.INTEGRATIONS;

public class EnderismMod implements ModInitializer {
	public static final String modid = "enderism";
	private static final String version = FabricLoaderImpl.INSTANCE.getModContainer(modid).orElseThrow().getMetadata().getVersion().getFriendlyString();
	public static final Logger logger = LoggerFactory.getLogger(modid);

	@Override
	public void onInitialize() {
		logger.info("Initialising " + modid + " with version " + version);

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
		new IntegrationInit().register();


		// integrations
		for (AbstractModIntegration ami : INTEGRATIONS) {
			if (ami.getModLoaded()) {
				ami.onInitializeWithMod();
			}
		}

		//
		double time = System.currentTimeMillis() - startTime;
		// Finish Registration

		logger.info("Everything from " + modid + " has been loaded! Have Fun! (Time Elapsed: " + time + " milliseconds)");
	}
}

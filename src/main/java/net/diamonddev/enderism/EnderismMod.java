package net.diamonddev.enderism;

import net.diamonddev.enderism.api.Registerable;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.*;
import java.util.Set;

public class EnderismMod implements ModInitializer {
	public static final String modid = "enderism";
	private static final String version = FabricLoaderImpl.INSTANCE.getModContainer(modid).orElseThrow().getMetadata().getVersion().getFriendlyString();
	public static final Logger logger = LoggerFactory.getLogger(modid);

	@Override
	public void onInitialize() {
		logger.info("Initialising " + modid + " with version " + version);

		// Start Registration
		long startTime = System.currentTimeMillis();

		Reflections reflections = new Reflections("net.diamonddev.enderism");
		Set<Class<? extends Registerable>> registrars = reflections.getSubTypesOf(Registerable.class);

		for (Class<? extends Registerable> registerableClass : registrars) {
			Registerable instance = null;
			try {
				instance = registerableClass.getConstructor().newInstance();
			} catch (InstantiationException | IllegalAccessException | InvocationTargetException |
					 NoSuchMethodException e) {
				throw new RuntimeException(e);
			}
			instance.register();
		}

		double time = System.currentTimeMillis() - startTime;
		// Finish Registration

		logger.info("Everything from " + modid + " has been loaded! Have Fun! (Time Elapsed: " + time + " milliseconds)");
	}
}

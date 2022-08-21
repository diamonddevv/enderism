package net.diamonddev.enderism.init;

import net.diamonddev.enderism.api.AbstractModIntegration;
import net.diamonddev.enderism.api.Registerable;
import net.diamonddev.enderism.integration.BetterEndIntegration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IntegrationInit implements Registerable {
    Logger logger = LogManager.getLogger("Enderism Mod Integrations");

    public static AbstractModIntegration BETTER_END;


    @Override
    public void register() {
        BETTER_END = AbstractModIntegration.register(new BetterEndIntegration());

        logger.info("Registered all Mod Integrations: " + AbstractModIntegration.INTEGRATIONS);
    }
}

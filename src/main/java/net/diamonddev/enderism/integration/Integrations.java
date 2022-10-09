package net.diamonddev.enderism.integration;

import net.diamonddev.enderism.api.Registerable;

public class Integrations implements Registerable {
    public static BetterEndIntegration BETTER_END;
    public static AileronIntegration AILERON;


    @Override
    public void register() {
        BETTER_END = new BetterEndIntegration();
        AILERON = new AileronIntegration();

        AbstractModIntegration.integrations.forEach((integration) -> {
            if (integration.getModLoaded()) {
                integration.integrationlogger.info("Mod (" + integration.modid + ") Found - Integration Started!");
            }
        });
    }
}

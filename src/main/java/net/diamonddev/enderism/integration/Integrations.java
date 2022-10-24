package net.diamonddev.enderism.integration;

import net.diamonddev.enderism.api.Registerable;

public class Integrations implements Registerable {

    private static final String associatedModName = "Enderism";

    public static BetterEndIntegration BETTER_END;
    public static AileronIntegration AILERON;


    @Override
    public void register() {
        BETTER_END = new BetterEndIntegration(associatedModName);
        AILERON = new AileronIntegration(associatedModName);

        AbstractModIntegration.integrations.forEach((integration) -> {
            if (integration.getModLoaded()) {
                integration.integrationlogger.info("Mod (" + integration.modid + ") Found - Integration Started!");
            }
        });
    }
}

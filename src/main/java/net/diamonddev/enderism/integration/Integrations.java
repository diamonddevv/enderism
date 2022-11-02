package net.diamonddev.enderism.integration;

import net.diamonddev.libgenetics.common.api.v1.integration.ModIntegrationAPI;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;

public class Integrations implements RegistryInitializer {

    private static final String associatedModName = "Enderism";

    public static BetterEndIntegration BETTER_END = new BetterEndIntegration(associatedModName);
    public static AileronIntegration AILERON = new AileronIntegration(associatedModName);


    @Override
    public void register() {

        ModIntegrationAPI.register(BETTER_END);
        ModIntegrationAPI.register(AILERON);
    }
}

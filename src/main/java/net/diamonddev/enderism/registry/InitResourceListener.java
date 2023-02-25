package net.diamonddev.enderism.registry;

import net.diamonddev.enderism.resource.EnderismCharmRecipeListener;
import net.diamonddev.enderism.resource.type.CharmRecipeResourceType;
import net.diamonddev.libgenetics.common.api.v1.dataloader.DataLoaderListener;
import net.diamonddev.libgenetics.common.api.v1.dataloader.DataLoaderResourceType;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;

public class InitResourceListener implements RegistryInitializer {

    // Listeners
    public static final DataLoaderListener ENDERISM_CHARMS = new EnderismCharmRecipeListener();


    // Types
    public static final DataLoaderResourceType CHARM_TYPE = new CharmRecipeResourceType();

    @Override
    public void register() {
        // Listeners
        DataLoaderListener.registerListener(ENDERISM_CHARMS);

        // Types
        ENDERISM_CHARMS.getManager().registerType(CHARM_TYPE);
    }
}

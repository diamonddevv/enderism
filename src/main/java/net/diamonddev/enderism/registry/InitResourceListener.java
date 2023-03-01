package net.diamonddev.enderism.registry;

import net.diamonddev.enderism.resource.EnderismCharmRecipeListener;
import net.diamonddev.enderism.resource.EnderismMusicSheetListener;
import net.diamonddev.enderism.resource.type.CharmRecipeResourceType;
import net.diamonddev.enderism.resource.type.MusicSheetResourceType;
import net.diamonddev.libgenetics.common.api.v1.dataloader.DataLoaderListener;
import net.diamonddev.libgenetics.common.api.v1.dataloader.DataLoaderResourceType;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;

public class InitResourceListener implements RegistryInitializer {

    // Listeners
    public static final DataLoaderListener ENDERISM_CHARMS = new EnderismCharmRecipeListener();
    public static final DataLoaderListener ENDERISM_MUSIC_SHEETS = new EnderismMusicSheetListener();


    // Types
    public static final DataLoaderResourceType CHARM_TYPE = new CharmRecipeResourceType();
    public static final DataLoaderResourceType MUSIC_TYPE = new MusicSheetResourceType();

    @Override
    public void register() {
        // Listeners
        DataLoaderListener.registerListener(ENDERISM_CHARMS);
        DataLoaderListener.registerListener(ENDERISM_MUSIC_SHEETS);

        // Types
        ENDERISM_CHARMS.getManager().registerType(CHARM_TYPE);
        ENDERISM_MUSIC_SHEETS.getManager().registerType(MUSIC_TYPE);
    }
}

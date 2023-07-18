package net.diamonddev.enderism.registry;

import net.diamonddev.enderism.resource.EnderismCharmRecipeListener;
import net.diamonddev.enderism.resource.EnderismMusicSheetListener;
import net.diamonddev.enderism.resource.type.CharmRecipeResourceType;
import net.diamonddev.enderism.resource.type.MusicSheetResourceType;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionDataListener;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionRegistry;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionResourceType;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;

public class InitResourceListener implements RegistryInitializer {

    // Listeners
    public static final CognitionDataListener ENDERISM_CHARMS = new EnderismCharmRecipeListener();
    public static final CognitionDataListener ENDERISM_MUSIC_SHEETS = new EnderismMusicSheetListener();


    // Types
    public static final CognitionResourceType CHARM_TYPE = new CharmRecipeResourceType();
    public static final CognitionResourceType MUSIC_TYPE = new MusicSheetResourceType();

    @Override
    public void register() {
        // Listeners
        CognitionRegistry.registerListener(ENDERISM_CHARMS);
        CognitionRegistry.registerListener(ENDERISM_MUSIC_SHEETS);

        // Types
        CognitionRegistry.registerType(ENDERISM_CHARMS, CHARM_TYPE);
        CognitionRegistry.registerType(ENDERISM_MUSIC_SHEETS, MUSIC_TYPE);
    }
}

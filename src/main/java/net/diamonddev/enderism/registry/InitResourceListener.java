package net.diamonddev.enderism.registry;

import net.diamonddev.enderism.resource.EnderismCharmRecipeListener;
import net.diamonddev.enderism.resource.EnderismMusicSheetListener;
import net.diamonddev.enderism.resource.type.CharmRecipeResourceType;
import net.diamonddev.enderism.resource.type.MusicSheetResourceType;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionDataListener;
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
        CognitionDataListener.registerListener(ENDERISM_CHARMS);
        CognitionDataListener.registerListener(ENDERISM_MUSIC_SHEETS);

        // Types
        ENDERISM_CHARMS.getManager().registerType(CHARM_TYPE);
        ENDERISM_MUSIC_SHEETS.getManager().registerType(MUSIC_TYPE);
    }
}

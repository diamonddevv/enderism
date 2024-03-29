package dev.diamond.enderism.registry;

import dev.diamond.enderism.resource.EnderismCharmRecipeListener;
import dev.diamond.enderism.resource.EnderismLightningRecipeListener;
import dev.diamond.enderism.resource.EnderismMusicSheetListener;
import dev.diamond.enderism.resource.EnderismMusicalInstrumentListener;
import dev.diamond.enderism.resource.type.CharmRecipeResourceType;
import dev.diamond.enderism.resource.type.LightningRecipeResourceType;
import dev.diamond.enderism.resource.type.MusicInstrumentResourceType;
import dev.diamond.enderism.resource.type.MusicSheetResourceType;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionDataListener;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionRegistry;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionResourceType;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;

public class InitResourceListener implements RegistryInitializer {

    // Listeners
    public static final CognitionDataListener ENDERISM_CHARMS = new EnderismCharmRecipeListener();
    public static final CognitionDataListener ENDERISM_MUSIC_SHEETS = new EnderismMusicSheetListener();
    public static final CognitionDataListener ENDERISM_INSTRUMENTS = new EnderismMusicalInstrumentListener();
    public static final CognitionDataListener ENDERISM_LIGHTNING = new EnderismLightningRecipeListener();


    // Types
    public static final CognitionResourceType CHARM_TYPE = new CharmRecipeResourceType();
    public static final CognitionResourceType MUSIC_TYPE = new MusicSheetResourceType();
    public static final CognitionResourceType INSTRUMENT_TYPE = new MusicInstrumentResourceType();
    public static final CognitionResourceType LIGHTNING_TYPE = new LightningRecipeResourceType();

    @Override
    public void register() {
        // Listeners
        CognitionRegistry.registerListener(ENDERISM_CHARMS);
        CognitionRegistry.registerListener(ENDERISM_MUSIC_SHEETS);
        CognitionRegistry.registerListener(ENDERISM_INSTRUMENTS);
        CognitionRegistry.registerListener(ENDERISM_LIGHTNING);

        // Types
        CognitionRegistry.registerType(ENDERISM_CHARMS, CHARM_TYPE);
        CognitionRegistry.registerType(ENDERISM_MUSIC_SHEETS, MUSIC_TYPE);
        CognitionRegistry.registerType(ENDERISM_INSTRUMENTS, INSTRUMENT_TYPE);
        CognitionRegistry.registerType(ENDERISM_LIGHTNING, LIGHTNING_TYPE);
    }
}

package net.diamonddev.enderism.resource;

import net.diamonddev.enderism.EnderismMod;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionDataListener;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionDataResource;
import net.minecraft.util.Identifier;

public class EnderismMusicalInstrumentListener extends CognitionDataListener {
    public EnderismMusicalInstrumentListener() {
        super("MusicalInstrumentDataListenerManager", EnderismMod.id("musical_instrument_listener"),
                "instruments");
    }

    @Override
    public void onReloadForEachResource(CognitionDataResource resource, Identifier path) {

    }

    @Override
    public void onFinishReload() {

    }

    @Override
    public void onClearCachePhase() {

    }
}

package net.diamonddev.enderism.resource;

import net.diamonddev.enderism.EnderismMod;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionDataListener;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionDataResource;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionRegistry;
import net.minecraft.util.Identifier;

public class EnderismMusicSheetListener extends CognitionDataListener {
    public EnderismMusicSheetListener() {
        super("Enderism Music Sheets", EnderismMod.id("enderism_music_sheets"), "music_sheets");
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

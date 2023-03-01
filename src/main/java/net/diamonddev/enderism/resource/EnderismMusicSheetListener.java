package net.diamonddev.enderism.resource;

import net.diamonddev.enderism.EnderismMod;
import net.diamonddev.libgenetics.common.api.v1.dataloader.DataLoaderListener;

public class EnderismMusicSheetListener extends DataLoaderListener {
    public EnderismMusicSheetListener() {
        super("Enderism Music Sheets", EnderismMod.id("enderism_music_sheets"), "music_sheets");
    }
}

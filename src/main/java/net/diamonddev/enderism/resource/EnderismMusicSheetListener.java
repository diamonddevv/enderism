package net.diamonddev.enderism.resource;

import net.diamonddev.enderism.EnderismMod;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionDataListener;

public class EnderismMusicSheetListener extends CognitionDataListener {
    public EnderismMusicSheetListener() {
        super("Enderism Music Sheets", EnderismMod.id("enderism_music_sheets"), "music_sheets");
    }
}

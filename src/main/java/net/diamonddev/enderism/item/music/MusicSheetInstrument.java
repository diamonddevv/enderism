package net.diamonddev.enderism.item.music;

import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.sound.SoundEvent;

public record MusicSheetInstrument(String id, SoundEvent sound) {
    public static MusicSheetInstrument fromNoteBlockInstrument(NoteBlockInstrument instrument) {
        return new MusicSheetInstrument(instrument.name(), instrument.getSound().value());
    }
}

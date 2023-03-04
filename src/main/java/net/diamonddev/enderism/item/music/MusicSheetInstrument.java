package net.diamonddev.enderism.item.music;

import net.minecraft.block.enums.Instrument;
import net.minecraft.sound.SoundEvent;

public record MusicSheetInstrument(String id, SoundEvent sound) {
    public static MusicSheetInstrument fromInstrument(Instrument instrument) {
        return new MusicSheetInstrument(instrument.name(), instrument.getSound().value());
    }
}

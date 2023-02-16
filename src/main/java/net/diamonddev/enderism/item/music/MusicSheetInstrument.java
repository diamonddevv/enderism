package net.diamonddev.enderism.item.music;

import net.minecraft.block.enums.Instrument;
import net.minecraft.sound.SoundEvent;

public class MusicSheetInstrument {
    private final SoundEvent sound;

    public MusicSheetInstrument(SoundEvent sound) {
        this.sound = sound;
    }
    public static MusicSheetInstrument fromInstrument(Instrument instrument) {
        return new MusicSheetInstrument(instrument.getSound().value());
    }


    public SoundEvent getSound() {
        return this.sound;
    }
}

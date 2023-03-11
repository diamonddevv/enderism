package net.diamonddev.enderism.item;

import net.diamonddev.enderism.item.music.InstrumentItem;
import net.diamonddev.enderism.item.music.MusicSheetInstrument;
import net.minecraft.sound.SoundEvents;

public class ChorusCelloItem extends InstrumentItem {
    public ChorusCelloItem(Settings settings) {
        super(settings);
    }

    @Override
    public MusicSheetInstrument getInstrument() {
        return new MusicSheetInstrument("cello", SoundEvents.BLOCK_NOTE_BLOCK_BASS.value());
    }
}

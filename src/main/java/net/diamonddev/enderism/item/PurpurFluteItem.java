package net.diamonddev.enderism.item;

import net.diamonddev.enderism.item.music.InstrumentItem;
import net.diamonddev.enderism.item.music.MusicSheetInstrument;
import net.minecraft.block.enums.Instrument;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class PurpurFluteItem extends InstrumentItem {
    public PurpurFluteItem(Settings settings) {
        super(settings);
    }

    @Override
    public int getCooldownTicks() {
        return 20;
    }

    @Override
    public SoundEvent getDefaultSoundEvent() {
        return SoundEvents.BLOCK_NOTE_BLOCK_FLUTE.value();
    }

    @Override
    public MusicSheetInstrument getInstrument() {
        return MusicSheetInstrument.fromInstrument(Instrument.FLUTE);
    }
}

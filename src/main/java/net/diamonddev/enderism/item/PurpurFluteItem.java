package net.diamonddev.enderism.item;

import net.diamonddev.enderism.item.music.MusicSheetInstrument;
import net.diamonddev.enderism.item.music.MusicSheetInstrumentConstants;
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
    public SoundEvent getSoundEvent() {
        return SoundEvents.BLOCK_BELL_USE;
    }

    @Override
    public MusicSheetInstrument getInstrument() {
        return MusicSheetInstrumentConstants.OCTARIAN_BURP;
//        return MusicSheetInstrument.fromInstrument(Instrument.FLUTE);
    }
}

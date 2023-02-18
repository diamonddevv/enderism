package net.diamonddev.enderism.item.wip;

import net.diamonddev.enderism.item.wip.music.MusicSheetInstrument;
import net.diamonddev.enderism.item.wip.music.MusicSheetInstrumentConstants;
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
        return MusicSheetInstrumentConstants.FLUTE;
//        return MusicSheetInstrument.fromInstrument(Instrument.FLUTE);
    }
}

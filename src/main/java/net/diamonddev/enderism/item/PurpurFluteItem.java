package net.diamonddev.enderism.item;

import net.minecraft.block.enums.Instrument;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class PurpurFluteItem extends InstrumentItem {
    public PurpurFluteItem(Settings settings) {
        super(settings);
    }

    @Override
    int getCooldownTicks() {
        return 20;
    }

    @Override
    SoundEvent getSoundEvent() {
        return SoundEvents.BLOCK_BELL_USE;
    }

    @Override
    Instrument getInstrument() {
        return Instrument.FLUTE;
    }
}

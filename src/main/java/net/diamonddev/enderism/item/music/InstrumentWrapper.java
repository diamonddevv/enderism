package net.diamonddev.enderism.item.music;

import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class InstrumentWrapper {
    private final InstrumentBean instrument;

    public InstrumentWrapper(InstrumentBean instrument) {
        this.instrument = instrument;
    }

    public SoundEvent getDefaultSound() {
        return Registries.SOUND_EVENT.get(new Identifier(instrument.defaultSoundId));
    }

    public boolean containsInstrumentItem(Identifier itemId) {
        return instrument.instrumentItemIds.contains(itemId.toString());
    }

    public String getIdentifier() {
        return instrument.nonSerializedIdentifier;
    }
}

package net.diamonddev.enderism.item.music;

import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class MusicSheetDataWrapper {
    private final SerializedMusicSheet sheet;

    public MusicSheetDataWrapper(SerializedMusicSheet sheet) {
        this.sheet = sheet;
    }

    public Optional<SoundEvent> getSoundFromHash(MusicSheetInstrument instrument) {
        if (this.sheet.hashedInstruments.containsKey(instrument.id())) {
            SoundEvent soundEvent = Registries.SOUND_EVENT.get(Identifier.tryParse(this.sheet.hashedInstruments.get(instrument.id())));
            if (soundEvent != null) {
                return Optional.of(soundEvent);
            }
        } return Optional.empty();
    }
}

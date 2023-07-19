package net.diamonddev.enderism.item.music;

import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class MusicSheetWrapper {
    public final MusicSheetBean bean;

    public MusicSheetWrapper(MusicSheetBean sheet) {
        this.bean = sheet;
    }

    public Optional<SoundEvent> getSoundFromHash(InstrumentWrapper instrument) {
        String id = instrument.getIdentifier().toLowerCase();
        SoundEvent soundEvent = getSound(id);
        if (soundEvent != null) {
            return Optional.of(soundEvent);
        } return Optional.empty();
    }

    @Nullable
    private SoundEvent getSound(String instrumentId) {

        for (var se : Registries.SOUND_EVENT.getIds()) {
            if (se.getPath().equals(getStringifiedSoundEventId(new Identifier(bean.id), instrumentId))) {
                return Registries.SOUND_EVENT.get(se);
            }
        } return null;
    }

    public boolean canBePlayedWithInstrument(InstrumentWrapper instrument) {
        return getSound(instrument.getIdentifier()) != null;
    }

    private static String parseIdFormatToSoundKeyFormat(String idFormat) {
        return idFormat.replace(':', '.');
    }

    public static String getStringifiedSoundEventId(Identifier sheetId, String instrumentId) {
        return "musicsheet." + parseIdFormatToSoundKeyFormat(sheetId.toString()) + "." + instrumentId;
    }

    public int getCooldownTicks() {
        return bean.cooldown;
    }
}

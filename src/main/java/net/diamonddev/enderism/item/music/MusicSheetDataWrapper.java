package net.diamonddev.enderism.item.music;

import net.diamonddev.enderism.EnderismMod;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class MusicSheetDataWrapper {
    private final SerializedMusicSheet sheet;

    public MusicSheetDataWrapper(SerializedMusicSheet sheet) {
        this.sheet = sheet;
    }

    public Optional<SoundEvent> getSoundFromHash(MusicSheetInstrument instrument) {
        String id = instrument.id().toLowerCase();
        SoundEvent soundEvent = getSound(EnderismMod.modid, id);
        if (soundEvent != null) {
            return Optional.of(soundEvent);
        } return Optional.empty();
    }

    @Nullable
    private SoundEvent getSound(String modid, String instrumentId) {
        return Registries.SOUND_EVENT.get(new Identifier(
                modid,
                getStringifiedSoundEventId(new Identifier(sheet.id), instrumentId)
        ));
    }

    private static String parseIdFormatToSoundKeyFormat(String idFormat) {
        return idFormat.replace(':', '.');
    }

    public static String getStringifiedSoundEventId(Identifier sheetId, String instrumentId) {
        return "musicsheet." + parseIdFormatToSoundKeyFormat(sheetId.toString()) + "." + instrumentId;
    }
}

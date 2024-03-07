package dev.diamond.enderism.registry;

import dev.diamond.enderism.EnderismMod;
import dev.diamond.enderism.item.music.MusicSheetWrapper;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class InitSoundEvents implements RegistryInitializer {

    public static SoundEvent CURSED_CHORUS_FRUIT_CHORUS_MAGNETITE_BIND;
    public static SoundEvent CURSED_CHORUS_FRUIT_PLAYER_BIND;

    public static SoundEvent FIBROUS_CHORUS_BOUNCE;

    public static SoundEvent CHARM_USE;

    public static SoundEvent NOTE_OTAMATONE;

    public static HashMap<String, SoundEvent> SHEET_MEGALOVANIA;
    public static HashMap<String, SoundEvent> SHEET_RUSHE;
    public static HashMap<String, SoundEvent> SHEET_POLCOW;

    public static SoundEvent STATIC_CORE_USE;

    @Override
    public void register() {
        CURSED_CHORUS_FRUIT_CHORUS_MAGNETITE_BIND = create("item.enderism.cursed_chorus.bind.chorus_magnetite");
        CURSED_CHORUS_FRUIT_PLAYER_BIND = create("item.enderism.cursed_chorus.bind.player");

        FIBROUS_CHORUS_BOUNCE = create("block.enderism.fibrous_chorus.bounce");

        CHARM_USE = create("item.enderism.charm.use");

        NOTE_OTAMATONE = create("note.enderism.otamatone");

        SHEET_MEGALOVANIA = createSheetMusic(EnderismMod.id("megalovania"),
                "flute", "cello", "otamatone"
        );

        SHEET_RUSHE = createSheetMusic(EnderismMod.id("rushe"),
                "flute", "cello"
        );

        SHEET_POLCOW = createSheetMusic(EnderismMod.id("polcow"),
                "otamatone"
        );

        STATIC_CORE_USE = create("item.enderism.static_core.use");
    }

    private static SoundEvent create(String name) {
        Identifier id = EnderismMod.id(name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }


    public static HashMap<String, SoundEvent> createSheetMusic(Identifier songId, String... instruments) {
        HashMap<String, SoundEvent> hash = new HashMap<>();
        addSheetMusic(hash, songId, instruments);
        return hash;
    }

    public static void addSheetMusic(HashMap<String, SoundEvent> hash, Identifier songId, String... instruments) {
        for (String s : instruments) {
            hash.put(s, create(MusicSheetWrapper.getStringifiedSoundEventId(songId, s)));
        }
    }
}

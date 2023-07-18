package net.diamonddev.enderism.registry;

import net.diamonddev.enderism.EnderismMod;
import net.diamonddev.enderism.item.music.MusicSheetDataWrapper;
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

    public static HashMap<String, SoundEvent> SHEET_MEGALOVANIA;
    public static HashMap<String, SoundEvent> SHEET_RUSHE;

    @Override
    public void register() {
        CURSED_CHORUS_FRUIT_CHORUS_MAGNETITE_BIND = create("item.enderism.cursed_chorus.bind.chorus_magnetite");
        CURSED_CHORUS_FRUIT_PLAYER_BIND = create("item.enderism.cursed_chorus.bind.player");

        FIBROUS_CHORUS_BOUNCE = create("block.enderism.fibrous_chorus.bounce");

        CHARM_USE = create("item.enderism.charm.use");

        SHEET_MEGALOVANIA = createSheetMusic(EnderismMod.id("megalovania"),
                "flute", "cello"
        );

        SHEET_RUSHE = createSheetMusic(EnderismMod.id("rushe"),
                "flute", "cello"
        );
    }

    private static SoundEvent create(String name) {
        Identifier id = EnderismMod.id(name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
    }

    public static HashMap<String, SoundEvent> createSheetMusic(Identifier songId, String... instruments) {
        HashMap<String, SoundEvent> hash = new HashMap<>();
        for (String s : instruments) {
            hash.put(s, create(MusicSheetDataWrapper.getStringifiedSoundEventId(songId, s)));
        }
        return hash;
    }
}

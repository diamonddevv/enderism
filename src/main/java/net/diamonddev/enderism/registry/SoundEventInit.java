package net.diamonddev.enderism.registry;

import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class SoundEventInit implements RegistryInitializer {

    public static SoundEvent CHORUS_MAGNETITE_BIND;
    public static SoundEvent PLAYER_BIND;
    public static SoundEvent FIBROUS_CHORUS_BOUNCE;

    @Override
    public void register() {
        CHORUS_MAGNETITE_BIND = create("entity.cursed_chorus.bind.chorus_magnetite");
        PLAYER_BIND = create("entity.cursed_chorus.bind.player");
        FIBROUS_CHORUS_BOUNCE = create("block.fibrous_chorus.bounce");
    }

    private SoundEvent create(String name) {
        Identifier id = new Identifier(name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }
}

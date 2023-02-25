package net.diamonddev.enderism.registry;

import net.diamonddev.enderism.EnderismMod;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class InitSoundEvents implements RegistryInitializer {

    public static SoundEvent CURSED_CHORUS_FRUIT_CHORUS_MAGNETITE_BIND;
    public static SoundEvent CURSED_CHORUS_FRUIT_PLAYER_BIND;
    public static SoundEvent FIBROUS_CHORUS_BOUNCE;

    @Override
    public void register() {
        CURSED_CHORUS_FRUIT_CHORUS_MAGNETITE_BIND = create("item.cursed_chorus.bind.chorus_magnetite");
        CURSED_CHORUS_FRUIT_PLAYER_BIND = create("item.cursed_chorus.bind.player");
        FIBROUS_CHORUS_BOUNCE = create("block.fibrous_chorus.bounce");
    }

    private SoundEvent create(String name) {
        Identifier id = EnderismMod.id(name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }
}

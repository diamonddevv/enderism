package net.diamonddev.enderism.init;

import net.diamonddev.enderism.api.Identifier;
import net.diamonddev.enderism.api.Registerable;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.registry.Registry;

public class SoundEventInit implements Registerable {

    public static SoundEvent CHORUS_MAGNETITE_BIND;
    public static SoundEvent PLAYER_BIND;

    @Override
    public void register() {
        CHORUS_MAGNETITE_BIND = create("entity.cursed_chorus.bind.chorus_magnetite");
        PLAYER_BIND = create("entity.cursed_chorus.bind.player");
    }

    private SoundEvent create(String name) {
        Identifier id = new Identifier(name);
        return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(id));
    }
}

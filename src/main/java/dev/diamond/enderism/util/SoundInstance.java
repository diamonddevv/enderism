package dev.diamond.enderism.util;

import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundEvent;

public class SoundInstance {
    private final SoundEvent sound;
    private final float vol;
    private final float pitch;

    public SoundInstance(SoundEvent soundEvent, float volume, float pitch) {
        this.sound = soundEvent;
        this.vol = volume;
        this.pitch = pitch;
    }

    public void play(Entity from) {
        from.playSound(sound, vol, pitch);
    }
}

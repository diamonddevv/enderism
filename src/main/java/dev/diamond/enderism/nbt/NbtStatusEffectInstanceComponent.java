package dev.diamond.enderism.nbt;

import net.diamonddev.libgenetics.common.api.v1.nbt.cerebellum.CerebellumNbtComponent;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class NbtStatusEffectInstanceComponent extends CerebellumNbtComponent<StatusEffectInstance> {
    public NbtStatusEffectInstanceComponent(String key) {
        super(key);
    }

    private static final String ID = "id";
    private static final String AMP = "amplifier";
    private static final String DUR = "duration";


    @Override
    public StatusEffectInstance read(NbtCompound nbt) {
        InstanceBuilder builder = new InstanceBuilder();

        NbtCompound compound = nbt.getCompound(key);
        builder.effect = fromId(compound.getString(ID));
        builder.amp = compound.getInt(AMP);
        builder.dur = compound.getInt(DUR);

        return builder.build();
    }

    @Override
    public void write(NbtCompound nbt, StatusEffectInstance data) {
        NbtCompound compound = new NbtCompound();
        compound.putString(ID, getIdOrDefault(data.getEffectType()).toString());
        compound.putInt(AMP, data.getAmplifier());
        compound.putInt(DUR, data.getDuration());
        nbt.put(key, compound);
    }


    private static Identifier getIdOrDefault(StatusEffect effect) {
        Identifier id = Registries.STATUS_EFFECT.getId(effect);
        if (id == null) {
            return new Identifier("instant_health");
        } else return id;
    }
    private static StatusEffect fromId(String id) {
        return Registries.STATUS_EFFECT.get(new Identifier(id));
    }
    private static class InstanceBuilder {
        private StatusEffect effect;
        private int dur;
        private int amp;

        InstanceBuilder() {}

        private StatusEffectInstance build() {
            return new StatusEffectInstance(effect, dur, amp);
        }

    }
}


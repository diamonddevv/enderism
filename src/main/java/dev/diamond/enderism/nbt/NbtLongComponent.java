package dev.diamond.enderism.nbt;

import net.diamonddev.libgenetics.common.api.v1.nbt.cerebellum.CerebellumNbtComponent;
import net.minecraft.nbt.NbtCompound;

public class NbtLongComponent extends CerebellumNbtComponent<Long> {
    public NbtLongComponent(String key) {
        super(key);
    }

    @Override
    public Long read(NbtCompound nbt) {
        return nbt.getLong(key);
    }

    @Override
    public void write(NbtCompound nbt, Long data) {
        nbt.putLong(key, data);
    }
}

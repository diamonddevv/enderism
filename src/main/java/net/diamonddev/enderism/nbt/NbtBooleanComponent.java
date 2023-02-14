package net.diamonddev.enderism.nbt;

import net.diamonddev.libgenetics.common.api.v1.nbt.NbtComponent;
import net.minecraft.nbt.NbtCompound;

public class NbtBooleanComponent extends NbtComponent<Boolean> {
    public NbtBooleanComponent(String key) {
        super(key);
    }

    @Override
    public Boolean read(NbtCompound nbt) {
        return nbt.getBoolean(key);
    }

    @Override
    public void write(NbtCompound nbt, Boolean data) {
        nbt.putBoolean(key, data);
    }
}

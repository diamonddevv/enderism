package net.diamonddev.enderism.nbt;

import net.diamonddev.libgenetics.common.api.v1.nbt.NbtComponent;
import net.minecraft.nbt.NbtCompound;


public class NbtStringComponent extends NbtComponent<String> {
    public NbtStringComponent(String key) {
        super(key);
    }

    @Override
    public String read(NbtCompound nbt) {
        return nbt.getString(key);
    }

    @Override
    public void write(NbtCompound nbt, String data) {
        nbt.putString(key, data);
    }

}

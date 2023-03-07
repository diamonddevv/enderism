package net.diamonddev.enderism.nbt;

import net.diamonddev.libgenetics.common.api.v1.nbt.cerebellum.CerebellumNbtComponent;
import net.minecraft.nbt.NbtCompound;


public class NbtStringComponent extends CerebellumNbtComponent<String> {
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

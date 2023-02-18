package net.diamonddev.enderism.nbt;

import net.diamonddev.libgenetics.common.api.v1.nbt.NbtComponent;
import net.minecraft.nbt.NbtCompound;

import java.util.UUID;

public class NbtUuidComponent extends NbtComponent<UUID> {
    public NbtUuidComponent(String key) {
        super(key);
    }

    @Override
    public UUID read(NbtCompound nbt) {
        return UUID.fromString(nbt.getString(key));
    }

    @Override
    public void write(NbtCompound nbt, UUID data) {
        nbt.putString(key, data.toString());
    }
}

package dev.diamond.enderism.nbt;

import net.diamonddev.libgenetics.common.api.v1.nbt.cerebellum.CerebellumNbtComponent;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Vec3d;

public class NbtVec3dComponent extends CerebellumNbtComponent<Vec3d> {
    public NbtVec3dComponent(String key) {
        super(key);
    }

    private static final String X_KEY = "x";
    private static final String Y_KEY = "y";
    private static final String Z_KEY = "z";

    @Override
    public Vec3d read(NbtCompound nbt) {
        NbtCompound compounded = nbt.getCompound(key);
        double x = compounded.getDouble(X_KEY);
        double y = compounded.getDouble(Y_KEY);
        double z = compounded.getDouble(Z_KEY);
        return new Vec3d(x, y, z);
    }

    @Override
    public void write(NbtCompound nbt, Vec3d data) {
        NbtCompound compounded = new NbtCompound();
        compounded.putDouble(X_KEY, data.x);
        compounded.putDouble(Y_KEY, data.y);
        compounded.putDouble(Z_KEY, data.z);
        nbt.put(key, compounded);
    }
}

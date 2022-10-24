package net.diamonddev.enderism.api;

import net.diamonddev.enderism.EnderismMod;
import net.minecraft.util.registry.Registry;

public class Identifier extends net.minecraft.util.Identifier {
    public Identifier(String path) {
        super(EnderismMod.modid, path);
    }



    public static <T> net.minecraft.util.Identifier getIdentifierFromRegistry(Registry<T> registry, T object) {
        return registry.getId(object);
    }
}

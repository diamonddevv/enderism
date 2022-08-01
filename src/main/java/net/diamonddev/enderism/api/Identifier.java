package net.diamonddev.enderism.api;

import net.diamonddev.enderism.EnderismMod;

public class Identifier extends net.minecraft.util.Identifier {
    public Identifier(String path) {
        super(EnderismMod.modid, path);
    }
}

package dev.diamond.enderism.impl;


import net.fabricmc.loader.api.FabricLoader;

public class GeneralModCompat {

    public static boolean hasXXLPackets = false;


    public static void checkInstalls() {
        hasXXLPackets = checkId("xxlpackets");
    }

    private static boolean checkId(String modid) {
        return FabricLoader.getInstance().isModLoaded(modid);
    }
}

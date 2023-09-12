package net.diamonddev.enderism.impl;

import org.quiltmc.loader.api.QuiltLoader;

public class GeneralModCompat {

    public static boolean hasXXLPackets = false;


    public static void checkInstalls() {
        hasXXLPackets = checkId("xxlpackets");
    }

    private static boolean checkId(String modid) {
        return QuiltLoader.isModLoaded(modid);
    }
}

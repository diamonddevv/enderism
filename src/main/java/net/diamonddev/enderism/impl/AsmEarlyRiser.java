package net.diamonddev.enderism.impl;


import com.chocohead.mm.api.ClassTinkerers;
import org.quiltmc.loader.api.MappingResolver;
import org.quiltmc.loader.api.QuiltLoader;

public class AsmEarlyRiser implements Runnable {

    public static final String
            ENCHTARGET_ELYTRA = "ENDERISM_ELYTRA";

    @Override
    public void run() {
        MappingResolver remapper = QuiltLoader.getMappingResolver();
        String enchantTarget = remapper.mapClassName("intermediary", "net.minecraft.class_1886");

        ClassTinkerers.enumBuilder(enchantTarget)
                .addEnumSubclass(ENCHTARGET_ELYTRA, "net.diamonddev.enderism.enchantment.target.ElytraEnchantmentTarget").build();

    }
}

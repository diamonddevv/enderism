package net.diamonddev.enderism.init;

import net.diamonddev.enderism.EnderismMod;
import net.diamonddev.enderism.item.CursedChorusItem;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.minecraft.util.registry.Registry;

public class ItemInit implements RegistryInitializer {


    public static CursedChorusItem CURSED_CHORUS = new CursedChorusItem();

    @Override
    public void register() {
        Registry.register(Registry.ITEM, EnderismMod.id.build("cursed_chorus_fruit"), CURSED_CHORUS);
    }
}

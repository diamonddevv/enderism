package net.diamonddev.enderism.registry;

import net.diamonddev.enderism.EnderismMod;
import net.diamonddev.enderism.item.CharmItem;
import net.diamonddev.enderism.item.CursedChorusItem;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ItemInit implements RegistryInitializer {


    public static CursedChorusItem CURSED_CHORUS = new CursedChorusItem();
    public static CharmItem CHARM = new CharmItem(new FabricItemSettings().maxCount(1).maxDamage(100));

    @Override
    public void register() {
        Registry.register(Registries.ITEM, EnderismMod.id("cursed_chorus_fruit"), CURSED_CHORUS);
        Registry.register(Registries.ITEM, EnderismMod.id("charm"), CHARM);
    }
}

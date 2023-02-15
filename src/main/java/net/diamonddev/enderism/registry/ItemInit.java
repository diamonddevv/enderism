package net.diamonddev.enderism.registry;

import net.diamonddev.enderism.EnderismMod;
import net.diamonddev.enderism.item.CharmItem;
import net.diamonddev.enderism.item.CursedChorusItem;
import net.diamonddev.enderism.item.MusicSheetItem;
import net.diamonddev.enderism.item.PurpurFluteItem;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ItemInit implements RegistryInitializer {


    public static CursedChorusItem CURSED_CHORUS = new CursedChorusItem();

    public static PurpurFluteItem PURPUR_FLUTE = new PurpurFluteItem(new FabricItemSettings());

    public static MusicSheetItem TEST_SHEET = new MusicSheetItem(new FabricItemSettings(), () -> ""); // need to parse music?

    public static CharmItem ENDSTONE_CHARM = new CharmItem(new FabricItemSettings().maxCount(1).maxDamage(50));
    public static CharmItem PURPUR_CHARM = new CharmItem(new FabricItemSettings().maxCount(1).maxDamage(75));
    public static CharmItem OBSIDIAN_CHARM = new CharmItem(new FabricItemSettings().maxCount(1).maxDamage(100));

    @Override
    public void register() {
        Registry.register(Registries.ITEM, EnderismMod.id("cursed_chorus_fruit"), CURSED_CHORUS);

        Registry.register(Registries.ITEM, EnderismMod.id("purpur_flute"), PURPUR_FLUTE);

        Registry.register(Registries.ITEM, EnderismMod.id("end_stone_charm"), ENDSTONE_CHARM);
        Registry.register(Registries.ITEM, EnderismMod.id("purpur_charm"), PURPUR_CHARM);
        Registry.register(Registries.ITEM, EnderismMod.id("obsidian_charm"), OBSIDIAN_CHARM);
    }
}

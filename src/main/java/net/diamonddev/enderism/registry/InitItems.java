package net.diamonddev.enderism.registry;

import net.diamonddev.enderism.EnderismMod;
import net.diamonddev.enderism.item.CharmItem;
import net.diamonddev.enderism.item.CursedChorusItem;
import net.diamonddev.enderism.item.ShulkerShellmetItem;
import net.diamonddev.enderism.item.music.MusicSheetItem;
import net.diamonddev.enderism.item.PurpurFluteItem;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class InitItems implements RegistryInitializer {

    public static CursedChorusItem CURSED_CHORUS = new CursedChorusItem();

    public static ShulkerShellmetItem SHULKER_SHELLMET = new ShulkerShellmetItem();

    public static PurpurFluteItem PURPUR_FLUTE = new PurpurFluteItem(new FabricItemSettings());
    public static MusicSheetItem MUSIC_SHEET = new MusicSheetItem(new FabricItemSettings());

    public static CharmItem ENDSTONE_CHARM = new CharmItem(new FabricItemSettings().maxCount(1).maxDamage(25));
    public static CharmItem PURPUR_CHARM = new CharmItem(new FabricItemSettings().maxCount(1).maxDamage(38));
    public static CharmItem OBSIDIAN_CHARM = new CharmItem(new FabricItemSettings().maxCount(1).maxDamage(50));

    public static Item CHORUS_JAM_BREAD = new Item(new FabricItemSettings().food(new FoodComponent.Builder().statusEffect(new StatusEffectInstance(InitEffects.CHORUSKIRMISH, 100, 0), 1.0f).hunger(5).saturationModifier(3f).build()));

    @Override
    public void register() {
        Registry.register(Registries.ITEM, EnderismMod.id("cursed_chorus_fruit"), CURSED_CHORUS);

        Registry.register(Registries.ITEM, EnderismMod.id("shulker_shellmet"), SHULKER_SHELLMET);

        Registry.register(Registries.ITEM, EnderismMod.id("purpur_flute"), PURPUR_FLUTE);
        Registry.register(Registries.ITEM, EnderismMod.id("music_sheet"), MUSIC_SHEET);

        Registry.register(Registries.ITEM, EnderismMod.id("end_stone_charm"), ENDSTONE_CHARM);
        Registry.register(Registries.ITEM, EnderismMod.id("purpur_charm"), PURPUR_CHARM);
        Registry.register(Registries.ITEM, EnderismMod.id("obsidian_charm"), OBSIDIAN_CHARM);

        Registry.register(Registries.ITEM, EnderismMod.id("chorus_jam_on_bread"), CHORUS_JAM_BREAD);
    }
}

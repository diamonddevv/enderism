package net.diamonddev.enderism.registry;

import net.diamonddev.enderism.EnderismMod;
import net.diamonddev.enderism.item.*;
import net.diamonddev.enderism.item.music.InstrumentItem;
import net.diamonddev.enderism.item.music.MusicSheetItem;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

public class InitItems implements RegistryInitializer {

    public static CursedChorusItem CURSED_CHORUS = new CursedChorusItem();

    public static ShulkerShellmetItem SHULKER_SHELLMET = new ShulkerShellmetItem();

    public static InstrumentItem PURPUR_FLUTE = new InstrumentItem(new QuiltItemSettings());
    public static InstrumentItem CHORUS_CELLO = new InstrumentItem(new QuiltItemSettings());
    public static InstrumentItem VIBRATOTAMATONE = new InstrumentItem(new QuiltItemSettings());

    public static MusicSheetItem MUSIC_SHEET = new MusicSheetItem(new QuiltItemSettings().maxCount(1));

    public static CharmItem WANDERERS_CHARM = new CharmItem(new QuiltItemSettings().maxCount(1).maxDamage(75));
    public static CharmItem ENDSTONE_CHARM = new CharmItem(new QuiltItemSettings().maxCount(1).maxDamage(25));
    public static CharmItem PURPUR_CHARM = new CharmItem(new QuiltItemSettings().maxCount(1).maxDamage(38));
    public static CharmItem OBSIDIAN_CHARM = new CharmItem(new QuiltItemSettings().maxCount(1).maxDamage(50));

    public static Item PIRPELL_FRAGMENT = new Item(new QuiltItemSettings());
    public static Item PIRPELL_INGOT = new Item(new QuiltItemSettings());

    public static Item ANCIENT_SCRAP = new Item(new QuiltItemSettings());
    public static Item SCULK_SPINE = new Item(new QuiltItemSettings());


    @Override
    public void register() {
        Registry.register(Registries.ITEM, EnderismMod.id("cursed_chorus_fruit"), CURSED_CHORUS);

        Registry.register(Registries.ITEM, EnderismMod.id("shulker_shellmet"), SHULKER_SHELLMET);

        Registry.register(Registries.ITEM, EnderismMod.id("purpur_flute"), PURPUR_FLUTE);
        Registry.register(Registries.ITEM, EnderismMod.id("chorus_cello"), CHORUS_CELLO);
        Registry.register(Registries.ITEM, EnderismMod.id("vibratotamatone"), VIBRATOTAMATONE);

        Registry.register(Registries.ITEM, EnderismMod.id("music_sheet"), MUSIC_SHEET);

        Registry.register(Registries.ITEM, EnderismMod.id("wanderers_charm"), WANDERERS_CHARM);
        Registry.register(Registries.ITEM, EnderismMod.id("end_stone_charm"), ENDSTONE_CHARM);
        Registry.register(Registries.ITEM, EnderismMod.id("purpur_charm"), PURPUR_CHARM);
        Registry.register(Registries.ITEM, EnderismMod.id("obsidian_charm"), OBSIDIAN_CHARM);

        Registry.register(Registries.ITEM, EnderismMod.id("pirpell_fragment"), PIRPELL_FRAGMENT);
        Registry.register(Registries.ITEM, EnderismMod.id("pirpell_ingot"), PIRPELL_INGOT);

        Registry.register(Registries.ITEM, EnderismMod.id("ancient_scrap"), ANCIENT_SCRAP);
        Registry.register(Registries.ITEM, EnderismMod.id("sculk_spine"), SCULK_SPINE);
    }
}

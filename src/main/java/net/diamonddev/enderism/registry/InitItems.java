package net.diamonddev.enderism.registry;

import net.diamonddev.enderism.EnderismMod;
import net.diamonddev.enderism.item.*;
import net.diamonddev.enderism.item.material.AncientPlatedArmorMaterial;
import net.diamonddev.enderism.item.material.PirpellPlatedToolMaterial;
import net.diamonddev.enderism.item.music.InstrumentItem;
import net.diamonddev.enderism.item.music.MusicSheetItem;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import java.util.List;

public class InitItems implements RegistryInitializer {

    public static CursedChorusItem CURSED_CHORUS = new CursedChorusItem();

    public static ShulkerShellmetItem SHULKER_SHELLMET = new ShulkerShellmetItem();

    public static InstrumentItem PURPUR_FLUTE = new InstrumentItem(new QuiltItemSettings());
    public static InstrumentItem CHORUS_CELLO = new InstrumentItem(new QuiltItemSettings());
    public static InstrumentItem VIBRATOTAMATONE = new InstrumentItem(new QuiltItemSettings());

    public static MusicSheetItem MUSIC_SHEET = new MusicSheetItem(new QuiltItemSettings().maxCount(1));

    public static CharmItem WANDERERS_CHARM = new CharmItem(new QuiltItemSettings().maxCount(1).maxDamage(75), 20 * 45);
    public static CharmItem ENDSTONE_CHARM = new CharmItem(new QuiltItemSettings().maxCount(1).maxDamage(25), 20 * 15);
    public static CharmItem PURPUR_CHARM = new CharmItem(new QuiltItemSettings().maxCount(1).maxDamage(38), 20 * 10);
    public static CharmItem OBSIDIAN_CHARM = new CharmItem(new QuiltItemSettings().maxCount(1).maxDamage(50), 20 * 8);

    public static Item PIRPELL_FRAGMENT = new Item(new QuiltItemSettings());
    public static Item PIRPELL_INGOT = new Item(new QuiltItemSettings());

    public static Item ANCIENT_SCRAP = new Item(new QuiltItemSettings());
    public static Item SCULK_SPINE = new Item(new QuiltItemSettings());

    public static SmithingTemplateItem PIRPELL_PLATED_TOOL_UPGRADE = SmithingTemplateHelper.createPirpellPlatedUpgrade();
    public static SmithingTemplateItem ANCIENT_PLATED_ARMOR_UPGRADE = SmithingTemplateHelper.createAncientPlatedUpgrade();

    public static SwordItem PIRPELL_NETHERITE_SWORD = new SwordItem(PirpellPlatedToolMaterial.INSTANCE, 3, -2.4f, new QuiltItemSettings().fireproof());
    public static ShovelItem PIRPELL_NETHERITE_SHOVEL = new ShovelItem(PirpellPlatedToolMaterial.INSTANCE, 1.5f, -3f, new QuiltItemSettings().fireproof());
    public static PickaxeItem PIRPELL_NETHERITE_PICK = new PickaxeItem(PirpellPlatedToolMaterial.INSTANCE, 1, -2.8f, new QuiltItemSettings().fireproof());
    public static AxeItem PIRPELL_NETHERITE_AXE = new AxeItem(PirpellPlatedToolMaterial.INSTANCE, 5, -3f, new QuiltItemSettings().fireproof());
    public static HoeItem PIRPELL_NETHERITE_HOE = new HoeItem(PirpellPlatedToolMaterial.INSTANCE, -4, 0, new QuiltItemSettings().fireproof());

    public static ArmorItem ANCIENT_NETHERITE_HELMET = new ArmorItem(AncientPlatedArmorMaterial.INSTANCE, ArmorItem.ArmorSlot.HELMET, new QuiltItemSettings().fireproof());
    public static ArmorItem ANCIENT_NETHERITE_CHESTPLATE = new ArmorItem(AncientPlatedArmorMaterial.INSTANCE, ArmorItem.ArmorSlot.CHESTPLATE, new QuiltItemSettings().fireproof());
    public static ArmorItem ANCIENT_NETHERITE_LEGGINGS = new ArmorItem(AncientPlatedArmorMaterial.INSTANCE, ArmorItem.ArmorSlot.LEGGINGS, new QuiltItemSettings().fireproof());
    public static ArmorItem ANCIENT_NETHERITE_BOOTS = new ArmorItem(AncientPlatedArmorMaterial.INSTANCE, ArmorItem.ArmorSlot.BOOTS, new QuiltItemSettings().fireproof());

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

        Registry.register(Registries.ITEM, EnderismMod.id("pirpell_plated_tool_upgrade_smithing_template"), PIRPELL_PLATED_TOOL_UPGRADE);
        Registry.register(Registries.ITEM, EnderismMod.id("ancient_plated_armor_upgrade_smithing_template"), ANCIENT_PLATED_ARMOR_UPGRADE);

        Registry.register(Registries.ITEM, EnderismMod.id("pirpell_plated_netherite_sword"), PIRPELL_NETHERITE_SWORD);
        Registry.register(Registries.ITEM, EnderismMod.id("pirpell_plated_netherite_shovel"), PIRPELL_NETHERITE_SHOVEL);
        Registry.register(Registries.ITEM, EnderismMod.id("pirpell_plated_netherite_pickaxe"), PIRPELL_NETHERITE_PICK);
        Registry.register(Registries.ITEM, EnderismMod.id("pirpell_plated_netherite_axe"), PIRPELL_NETHERITE_AXE);
        Registry.register(Registries.ITEM, EnderismMod.id("pirpell_plated_netherite_hoe"), PIRPELL_NETHERITE_HOE);

        Registry.register(Registries.ITEM, EnderismMod.id("ancient_plated_netherite_helmet"), ANCIENT_NETHERITE_HELMET);
        Registry.register(Registries.ITEM, EnderismMod.id("ancient_plated_netherite_chestplate"), ANCIENT_NETHERITE_CHESTPLATE);
        Registry.register(Registries.ITEM, EnderismMod.id("ancient_plated_netherite_leggings"), ANCIENT_NETHERITE_LEGGINGS);
        Registry.register(Registries.ITEM, EnderismMod.id("ancient_plated_netherite_boots"), ANCIENT_NETHERITE_BOOTS);
    }

    private static final class SmithingTemplateHelper {
        private static final List<Identifier> TOOL_ICONS = List.of(
                new Identifier("item/empty_slot_hoe"),
                new Identifier("item/empty_slot_shovel"),
                new Identifier("item/empty_slot_pickaxe"),
                new Identifier("item/empty_slot_axe"),
                new Identifier("item/empty_slot_sword")
        );

        private static final List<Identifier> ARMOR_ICONS = List.of(
                new Identifier("item/empty_armor_slot_helmet"),
                new Identifier("item/empty_armor_slot_chestplate"),
                new Identifier("item/empty_armor_slot_leggings"),
                new Identifier("item/empty_armor_slot_boots")
        );

        private static final List<Identifier> PIRPELL_ADDITION_ICONS = List.of(
                EnderismMod.id("icons/empty_slot_pirpell.png")
        );

        private static final List<Identifier> ANCIENT_SCRAP_ADDITION_ICONS = List.of(
                EnderismMod.id("icons/empty_slot_ancient_scrap.png")
        );


        public static SmithingTemplateItem createPirpellPlatedUpgrade() {
            return new SmithingTemplateItem(
                    Text.translatable("tooltip.pirpell_upgrade.applies").formatted(Formatting.BLUE),
                    Text.translatable("tooltip.pirpell_upgrade.ingredients").formatted(Formatting.BLUE),
                    Text.translatable("tooltip.pirpell_upgrade.upgrade").formatted(Formatting.GRAY),
                    Text.translatable("tooltip.pirpell_upgrade.base"),
                    Text.translatable("tooltip.pirpell_upgrade.additions"),
                    TOOL_ICONS, PIRPELL_ADDITION_ICONS
            );
        }

        public static SmithingTemplateItem createAncientPlatedUpgrade() {
            return new SmithingTemplateItem(
                    Text.translatable("tooltip.ancient_upgrade.applies").formatted(Formatting.BLUE),
                    Text.translatable("tooltip.ancient_upgrade.ingredients").formatted(Formatting.BLUE),
                    Text.translatable("tooltip.ancient_upgrade.upgrade").formatted(Formatting.GRAY),
                    Text.translatable("tooltip.ancient_upgrade.base"),
                    Text.translatable("tooltip.ancient_upgrade.additions"),
                    ARMOR_ICONS, ANCIENT_SCRAP_ADDITION_ICONS
            );
        }

    }
}

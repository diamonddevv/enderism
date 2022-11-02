package net.diamonddev.enderism.init;

import net.diamonddev.enderism.EnderismMod;
import net.diamonddev.enderism.block.ChorusFruitPieBlock;
import net.diamonddev.enderism.block.FibrousChorusBlock;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockInit implements RegistryInitializer {

    // Purposeful Blocks
    public static Block CHORUS_MAGNETITE = new Block(FabricBlockSettings.of(Material.ORGANIC_PRODUCT).luminance(8).hardness(1.5f).strength(1.2f));
    public static FibrousChorusBlock FIBROUS_CHORUS = new FibrousChorusBlock(1.5f, 5f, FabricBlockSettings.of(Material.ORGANIC_PRODUCT).hardness(2.0f).strength(2.0f));
    public static ChorusFruitPieBlock CHORUS_FRUIT_PIE = new ChorusFruitPieBlock(new FoodComponent.Builder().hunger(2).saturationModifier(0.3f).build(), 6, FabricBlockSettings.of(Material.CAKE));

    @Override
    public void register() {
        registerBlockAndItem(CHORUS_MAGNETITE, EnderismMod.id.build("chorus_magnetite"), new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS));
        registerBlockAndItem(FIBROUS_CHORUS, EnderismMod.id.build("fibrous_chorus"), new FabricItemSettings().group(ItemGroup.MISC));
        registerBlockAndItem(CHORUS_FRUIT_PIE, EnderismMod.id.build("chorus_fruit_pie"), new FabricItemSettings().group(ItemGroup.FOOD));
    }

    public static void registerBlockAndItem(Block block, Identifier identifier, Item.Settings settings) {
        Registry.register(Registry.BLOCK, identifier, block);
        Registry.register(Registry.ITEM, identifier, new BlockItem(block, settings));
    }
}

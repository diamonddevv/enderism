package net.diamonddev.enderism.init;

import net.diamonddev.enderism.api.Identifier;
import net.diamonddev.enderism.api.Registerable;
import net.diamonddev.enderism.block.FibrousChorusBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

public class BlockInit implements Registerable {

    // Purposeful Blocks
    public static Block CHORUS_MAGNETITE = new Block(FabricBlockSettings.of(Material.ORGANIC_PRODUCT).luminance(8).hardness(1.5f).strength(1.2f));
    public static FibrousChorusBlock FIBROUS_CHORUS = new FibrousChorusBlock(1.5f, 5f, FabricBlockSettings.of(Material.ORGANIC_PRODUCT).hardness(2.0f).strength(2.0f));


    @Override
    public void register() {
        registerBlockAndItem(CHORUS_MAGNETITE, new Identifier("chorus_magnetite"), new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS));
        registerBlockAndItem(FIBROUS_CHORUS, new Identifier("fibrous_chorus"), new FabricItemSettings().group(ItemGroup.MISC));
    }

    public static void registerBlockAndItem(Block block, net.minecraft.util.Identifier identifier, Item.Settings settings) {
        Registry.register(Registry.BLOCK, identifier, block);
        Registry.register(Registry.ITEM, identifier, new BlockItem(block, settings));
    }
}

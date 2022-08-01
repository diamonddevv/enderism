package net.diamonddev.enderism.init;

import net.diamonddev.enderism.api.Identifier;
import net.diamonddev.enderism.api.Registerable;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

public class BlockInit implements Registerable {

    public static Block CHORUS_MAGNETITE = new Block(FabricBlockSettings.of(Material.ORGANIC_PRODUCT).luminance(3).hardness(1.5f).strength(1.2f));

    @Override
    public void register() {
        registerBlockAndItem(CHORUS_MAGNETITE, new Identifier("chorus_magnetite"), new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS));
    }

    public static void registerBlockAndItem(Block block, net.minecraft.util.Identifier identifier, Item.Settings settings) {
        Registry.register(Registry.BLOCK, identifier, block);
        Registry.register(Registry.ITEM, identifier, new BlockItem(block, settings));
    }
}

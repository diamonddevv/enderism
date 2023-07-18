package net.diamonddev.enderism.registry;

import net.diamonddev.enderism.EnderismMod;
import net.diamonddev.enderism.block.FibrousChorusBlock;
import net.diamonddev.libgenetics.common.api.v1.interfaces.BlockRegistryHelper;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.sound.BlockSoundGroup;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;

public class InitBlocks implements RegistryInitializer, BlockRegistryHelper {

    // Purposeful Blocks
    public static Block CHORUS_MAGNETITE = new Block(blankSettings().sounds(BlockSoundGroup.LODESTONE).luminance(8).hardness(1.5f).strength(1.2f));
    public static FibrousChorusBlock FIBROUS_CHORUS = new FibrousChorusBlock(1.5f, 5f, blankSettings().hardness(2.0f).strength(2.0f));
//    public static ChorusFruitPieBlock CHORUS_FRUIT_PIE = new ChorusFruitPieBlock(new FoodComponent.Builder().hunger(2).saturationModifier(0.3f).build(), 6, FabricBlockSettings.of(Material.CAKE));
    @Override
    public void register() {
        registerBlockAndItem(CHORUS_MAGNETITE, EnderismMod.id("chorus_magnetite"), new FabricItemSettings());
        registerBlockAndItem(FIBROUS_CHORUS, EnderismMod.id("fibrous_chorus"), new FabricItemSettings());
//        registerBlockAndItem(CHORUS_FRUIT_PIE, EnderismMod.id("chorus_fruit_pie"), new FabricItemSettings());
    }


    public static QuiltBlockSettings blankSettings() {
        return QuiltBlockSettings.copyOf(AbstractBlock.Settings.create());
    }
}

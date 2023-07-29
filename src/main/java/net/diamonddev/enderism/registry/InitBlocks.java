package net.diamonddev.enderism.registry;

import net.diamonddev.enderism.EnderismMod;
import net.diamonddev.enderism.block.FibrousChorusBlock;
import net.diamonddev.libgenetics.common.api.v1.interfaces.BlockRegistryHelper;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.sound.BlockSoundGroup;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

public class InitBlocks implements RegistryInitializer, BlockRegistryHelper {

    // Decorative
    public static Block PIRPELL_BLOCK = new Block(blankSettings().sounds(BlockSoundGroup.METAL).strength(5f, 6f));
    public static Block PATTERNED_PIRPELL = new Block(blankSettings().sounds(BlockSoundGroup.METAL).strength(2.5f, 3f));

    // Purposeful Blocks
    public static Block CHORUS_MAGNETITE = new Block(blankSettings().sounds(BlockSoundGroup.LODESTONE).luminance(8).strength(1.5f, 1.2f));
    public static FibrousChorusBlock FIBROUS_CHORUS = new FibrousChorusBlock(1.5f, 5f, blankSettings().strength(2.0f, 2.0f));


//    public static ChorusFruitPieBlock CHORUS_FRUIT_PIE = new ChorusFruitPieBlock(new FoodComponent.Builder().hunger(2).saturationModifier(0.3f).build(), 6, FabricBlockSettings.of(Material.CAKE));
    @Override
    public void register() {
        registerBlockAndItem(CHORUS_MAGNETITE, EnderismMod.id("chorus_magnetite"), new QuiltItemSettings());
        registerBlockAndItem(FIBROUS_CHORUS, EnderismMod.id("fibrous_chorus"), new QuiltItemSettings());
        registerBlockAndItem(PIRPELL_BLOCK, EnderismMod.id("pirpell_block"), new QuiltItemSettings());
        registerBlockAndItem(PATTERNED_PIRPELL, EnderismMod.id("patterned_pirpell"), new QuiltItemSettings());


//        registerBlockAndItem(CHORUS_FRUIT_PIE, EnderismMod.id("chorus_fruit_pie"), new FabricItemSettings());
    }


    public static QuiltBlockSettings blankSettings() {
        return QuiltBlockSettings.copyOf(AbstractBlock.Settings.create());
    }
    public static QuiltBlockSettings copySettings(Block block) {
        return QuiltBlockSettings.copyOf(block);
    }
}

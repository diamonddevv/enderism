package dev.diamond.enderism.registry;

import dev.diamond.enderism.EnderismMod;
import dev.diamond.enderism.block.FibrousChorusBlock;
import net.diamonddev.libgenetics.common.api.v1.interfaces.BlockRegistryHelper;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.sound.BlockSoundGroup;

public class InitBlocks implements RegistryInitializer, BlockRegistryHelper {

    // Decorative
    public static Block PIRPELL_BLOCK = new Block(blankSettings().sounds(BlockSoundGroup.METAL).strength(5f, 6f));
    public static Block PATTERNED_PIRPELL = new Block(blankSettings().sounds(BlockSoundGroup.METAL).strength(2.5f, 3f));

    // Purposeful Blocks
    public static Block CHORUS_MAGNETITE = new Block(blankSettings().sounds(BlockSoundGroup.LODESTONE).luminance(8).strength(1.5f, 1.2f).pistonBehavior(PistonBehavior.IGNORE));
    public static FibrousChorusBlock FIBROUS_CHORUS = new FibrousChorusBlock(1.5f, InitConfig.ENDERISM.blockConfig.fibrousChorusMaxBounce, blankSettings().strength(2.0f, 2.0f));


    @Override
    public void register() {
        registerBlockAndItem(CHORUS_MAGNETITE, EnderismMod.id("chorus_magnetite"), new FabricItemSettings());
        registerBlockAndItem(FIBROUS_CHORUS, EnderismMod.id("fibrous_chorus"), new FabricItemSettings());
        registerBlockAndItem(PIRPELL_BLOCK, EnderismMod.id("pirpell_block"), new FabricItemSettings());
        registerBlockAndItem(PATTERNED_PIRPELL, EnderismMod.id("patterned_pirpell"), new FabricItemSettings());

    }


    public static FabricBlockSettings blankSettings() {
        return FabricBlockSettings.copyOf(AbstractBlock.Settings.create());
    }
    public static FabricBlockSettings copySettings(Block block) {
        return FabricBlockSettings.copyOf(block);
    }
}

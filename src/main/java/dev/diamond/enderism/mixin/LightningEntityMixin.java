package dev.diamond.enderism.mixin;

import dev.diamond.enderism.registry.InitResourceListener;
import dev.diamond.enderism.resource.type.LightningRecipeResourceType;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;


@Mixin(LightningEntity.class)
public abstract class LightningEntityMixin extends Entity {


    @Shadow private int ambientTick;

    public LightningEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }


    @Inject(method = "tick", at = @At("TAIL"))
    public void dialabs$tickLightningRecipes(CallbackInfo ci) {
        if (ambientTick >= 0 && ambientTick <= 1) {
            if (!this.getWorld().isClient) {

                BlockPos struckBlock = getBlockPos().down();
                if (this.getWorld().getBlockState(struckBlock).getBlock().equals(Blocks.LIGHTNING_ROD)) {
                    struckBlock = struckBlock.down();
                }

                BlockPos directlyStruck = struckBlock;
                doLightningRecipeConversion(getWorld(), directlyStruck);

                /* adjacent 8 blocks
                *      # # #
                *      # @ #
                *      # # #
                *  where @ is the direct, and # is the adjacent blocks
                */

                List<BlockPos> converted = new ArrayList<>();
                for (int i = 0; i < 2; i++) {
                    int x = getWorld().getRandom().nextBetween(-1, 1);
                    int z = getWorld().getRandom().nextBetween(-1, 1);
                    BlockPos adjPos = directlyStruck.add(x, 0, z);
                    if (!converted.contains(adjPos)) {
                        converted.add(adjPos);
                        doLightningRecipeConversion(getWorld(), adjPos);
                    }
                }
            }
        }
    }


    @Unique
    private static void doLightningRecipeConversion(World world, BlockPos pos) {
        CognitionRegistry.forEachResource(InitResourceListener.ENDERISM_LIGHTNING, InitResourceListener.LIGHTNING_TYPE, recipe -> {
            Identifier ogBlock = recipe.getIdentifier(LightningRecipeResourceType.FROM);
            Identifier newBlock = recipe.getIdentifier(LightningRecipeResourceType.TO);

            if (Registries.BLOCK.getId(world.getBlockState(pos).getBlock()).equals(ogBlock)) {
                Block block = Registries.BLOCK.get(newBlock);
                world.setBlockState(pos, block.getDefaultState());
                // particles
                world.addParticle(ParticleTypes.ELECTRIC_SPARK,
                        pos.getX() + .5,
                        pos.getY() + 1,
                        pos.getZ() + .5,
                        1, 1, 1);
            }
        });
    }
}
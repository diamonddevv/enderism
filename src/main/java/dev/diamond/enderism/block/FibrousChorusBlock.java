package dev.diamond.enderism.block;

import dev.diamond.enderism.registry.InitAdvancementCriteria;
import dev.diamond.enderism.registry.InitGamerules;
import dev.diamond.enderism.registry.InitSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public class FibrousChorusBlock extends Block {

    public FibrousChorusBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (entity.bypassesLandingEffects()) {
            super.onLandedUpon(world, state, pos, entity, fallDistance);
        } else {
            entity.handleFallDamage(fallDistance, 0.0F, entity.getDamageSources().fall());
            this.sound(entity);
        }
    }

    @Override
    public void onEntityLand(BlockView world, Entity entity) {
        if (entity.bypassesLandingEffects()) {
            super.onEntityLand(world, entity);
        } else {
            if (Math.round(entity.getVelocity().y) < 0) {
                this.propel(entity);
                if (entity instanceof ServerPlayerEntity spe)
                    InitAdvancementCriteria.BOUNCE_ON_FIBROUS_CHORUS.trigger(spe);
            }
        }
    }

    private void propel(Entity entity) {
        Vec3d vec3d = entity.getVelocity();
        if (vec3d.y < 0) {
            double d = entity instanceof LivingEntity ? 1.0 : 0.8;
            entity.setVelocity(vec3d.x, Math.min(-vec3d.y * getBounce(entity.getWorld()) / d, getMaxBounce(entity.getWorld())), vec3d.z);
        }
    }

    private void sound(Entity entity) {
        entity.getWorld().playSound(
                null,
                entity.getBlockPos(),
                InitSoundEvents.FIBROUS_CHORUS_BOUNCE,
                SoundCategory.BLOCKS,
                1.8f,
                new Random().nextFloat(0.1f, 1.5f)
        );
    }

    public static double getBounce(World world) {
        return world.getGameRules().getInt(InitGamerules.FIBROUS_CHORUS_BOUNCE_POWER_TIMES_HUNDRED) / 100f;
    }

    public static float getMaxBounce(World world) {
        return world.getGameRules().getInt(InitGamerules.FIBROUS_CHORUS_MAX_BOUNCE_POWER_TIMES_HUNDRED) / 100f;
    }
}
package net.diamonddev.enderism.block;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.world.BlockView;

public class FibrousChorusBlock extends Block {
    private final float bounce;

    public FibrousChorusBlock(float bounce, Settings settings) {
        super(settings);
        this.bounce = bounce;
    }

    @Override
    public void onEntityLand(BlockView world, Entity entity) {
        if (entity.bypassesLandingEffects()) {
            super.onEntityLand(world, entity);
        } else {
            this.propel(entity);
        }
    }

    private void propel(Entity entity) {
        float yVel = entity.fallDistance;
        System.out.println(Math.min(yVel * bounce, 5));
        entity.setVelocity(0.0, Math.min(yVel * bounce, 5), 0.0);
        entity.velocityModified = true;
    }


}

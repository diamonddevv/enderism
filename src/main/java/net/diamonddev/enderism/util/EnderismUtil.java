package net.diamonddev.enderism.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.event.GameEvent;

public class EnderismUtil {

    public static void chorusTeleport(LivingEntity user) {
        chorusTeleport(user, 16, 16.0);
    }
    public static void chorusTeleport(LivingEntity user, int chance, double range) {
        double d = user.getX();
        double e = user.getY();
        double f = user.getZ();

        for (int i = 0; i < chance; ++i) {
            double g = user.getX() + (user.getRandom().nextDouble() - 0.5) * range;
            double h = MathHelper.clamp(user.getY() + (double) (user.getRandom().nextInt((int) Math.round(range)) - 8), user.world.getBottomY(), (user.world.getBottomY() + ((ServerWorld) user.world).getLogicalHeight() - 1));
            double j = user.getZ() + (user.getRandom().nextDouble() - 0.5) * range;
            if (user.hasVehicle()) {
                user.stopRiding();
            }

            Vec3d vec3d = user.getPos();
            if (user.teleport(g, h, j, true)) {
                user.world.emitGameEvent(GameEvent.TELEPORT, vec3d, GameEvent.Emitter.of(user));
                SoundEvent soundEvent = SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT;
                user.world.playSound(null, d, e, f, soundEvent, SoundCategory.PLAYERS, 1.0F, 1.0F);
                user.playSound(soundEvent, 1.0F, 1.0F);
                break;
            }
        }
    }

    public static Hand otherHand(Hand hand) {
        return hand == Hand.MAIN_HAND ? Hand.OFF_HAND : Hand.MAIN_HAND;
    }
}

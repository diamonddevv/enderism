package net.diamonddev.enderism.util;

import net.diamonddev.enderism.registry.InitConfig;
import net.diamonddev.enderism.registry.InitResourceListener;
import net.diamonddev.enderism.resource.type.CharmRecipeResourceType;
import net.diamonddev.libgenetics.common.api.v1.dataloader.DataLoaderResource;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.event.GameEvent;

import java.util.UUID;

public class EnderismUtil {

    public static final UUID UUID_ZERO = new UUID(0, 0);

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

    public static Vec3d toVec(BlockPos pos) {
        return new Vec3d(pos.getX(), pos.getY(), pos.getZ());
    }

    public static <T> T registryGetOrElse(Registry<T> registry, Identifier id, T elseReturned) {
        if (registry.containsId(id)) return registry.get(id); else return elseReturned;
    }

    public static boolean isValidCharmCraftIngredient(ItemStack ingredient) {
        if (InitConfig.ENDERISM.charmConfig.charmCraftsUsePotions) {
            return false; // skip because it uses potions
        } else {
            for (DataLoaderResource res : InitResourceListener.ENDERISM_CHARMS.getManager().CACHE.get(InitResourceListener.CHARM_TYPE)) {
                ItemConvertible item = registryGetOrElse(Registries.ITEM, res.getIdentifier(CharmRecipeResourceType.INGREDIENT), null);
                if (item != null) {
                    if (ingredient.getItem() == item) return true;
                } else return false;
            }
        }
        return false;
    }
}

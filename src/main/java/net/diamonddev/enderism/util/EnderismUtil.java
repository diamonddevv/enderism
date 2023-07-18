package net.diamonddev.enderism.util;

import net.diamonddev.enderism.registry.InitConfig;
import net.diamonddev.enderism.registry.InitResourceListener;
import net.diamonddev.enderism.resource.type.CharmRecipeResourceType;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionDataResource;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ChorusFruitItem;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
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
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.event.GameEvent;

import java.util.UUID;

public class EnderismUtil {

    public static final UUID UUID_ZERO = new UUID(0, 0);

    public static void chorusTeleport(LivingEntity user) {
        chorusTeleport(user, 16, 16.0);
    }
    public static void chorusTeleport(LivingEntity user, int chance, double range) { // literally copied from chorus fruit code lol

        double d = user.getX();
        double e = user.getY();
        double f = user.getZ();

        for(int i = 0; i < chance; ++i) {
            double g = user.getX() + (user.getRandom().nextDouble() - 0.5) * range;
            double h = MathHelper.clamp(user.getY() + (double)(user.getRandom().nextInt(16) - 8), (double)user.getWorld().getBottomY(), (double)(user.getWorld().getBottomY() + ((ServerWorld)user.getWorld()).getLogicalHeight() - 1));
            double j = user.getZ() + (user.getRandom().nextDouble() - 0.5) * range;
            if (user.hasVehicle()) {
                user.stopRiding();
            }

            Vec3d vec3d = user.getPos();
            if (user.teleport(g, h, j, true)) {
                user.getWorld().emitGameEvent(GameEvent.TELEPORT, vec3d, GameEvent.Context.create(user));
                SoundEvent soundEvent = user instanceof FoxEntity ? SoundEvents.ENTITY_FOX_TELEPORT : SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT;
                user.getWorld().playSound((PlayerEntity)null, d, e, f, soundEvent, SoundCategory.PLAYERS, 1.0F, 1.0F);
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
            return ingredient.getItem() instanceof PotionItem;
        } else {
            for (CognitionDataResource res : InitResourceListener.ENDERISM_CHARMS.getManager().CACHE.get(InitResourceListener.CHARM_TYPE)) {
                ItemConvertible item = registryGetOrElse(Registries.ITEM, res.getIdentifier(CharmRecipeResourceType.INGREDIENT), null);
                if (item != null) {
                    if (ingredient.getItem() == item) return true;
                } else return false;
            }
        }
        return false;
    }
}

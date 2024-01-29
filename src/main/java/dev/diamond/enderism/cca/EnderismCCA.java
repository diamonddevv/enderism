package dev.diamond.enderism.cca;

import dev.diamond.enderism.EnderismMod;
import dev.diamond.enderism.cca.entity.BooleanComponent;
import dev.diamond.enderism.cca.entity.DoubleComponent;
import dev.diamond.enderism.cca.entity.VectorComponent;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.util.math.Vec3d;

public class EnderismCCA implements EntityComponentInitializer {

    // ENTITY COMPONENTS
    public static final ComponentKey<DoubleComponent> RETRIBUTIONAL_DAMAGE =
            ComponentRegistryV3.INSTANCE.getOrCreate(EnderismMod.id("retributional_damage"), DoubleComponent.class);

    public static final ComponentKey<BooleanComponent> RETRIBUTIVE_ARROW =
            ComponentRegistryV3.INSTANCE.getOrCreate(EnderismMod.id("retributive"), BooleanComponent.class);

    public static final ComponentKey<VectorComponent> SNIPING_ARROW_ORIGIN =
            ComponentRegistryV3.INSTANCE.getOrCreate(EnderismMod.id("sniping_arrow_origin"), VectorComponent.class);

    public static final ComponentKey<BooleanComponent> SNIPING_ARROW =
            ComponentRegistryV3.INSTANCE.getOrCreate(EnderismMod.id("sniping"), BooleanComponent.class);

    public static final ComponentKey<DoubleComponent> SNIPING_SPEED_REF =
            ComponentRegistryV3.INSTANCE.getOrCreate(EnderismMod.id("sniping_speed_ref"), DoubleComponent.class);

    public static final ComponentKey<DoubleComponent> SNIPING_DIVERGENCE_REF =
            ComponentRegistryV3.INSTANCE.getOrCreate(EnderismMod.id("sniping_divergence_ref"), DoubleComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerFor(LivingEntity.class, RETRIBUTIONAL_DAMAGE, livingEntity -> new DoubleComponent("retributionalDamage"));
        registry.registerFor(PersistentProjectileEntity.class, RETRIBUTIVE_ARROW, persProj -> new BooleanComponent("isRetributive", false));

        registry.registerFor(PersistentProjectileEntity.class, SNIPING_ARROW_ORIGIN, persProj -> new VectorComponent("originVector"));
        registry.registerFor(PersistentProjectileEntity.class, SNIPING_ARROW, persProj -> new BooleanComponent("isSniping", false));
        registry.registerFor(PersistentProjectileEntity.class, SNIPING_SPEED_REF, persProj -> new DoubleComponent("snipingSpeedReference"));
        registry.registerFor(PersistentProjectileEntity.class, SNIPING_DIVERGENCE_REF, persProj -> new DoubleComponent("snipingDivergenceReference"));
    }

    public static class RetributionalDamageManager {
        public static double getDmg(LivingEntity target) {
            return RETRIBUTIONAL_DAMAGE.get(target).getValue();
        }

        public static void resetDmg(LivingEntity target) {
            RETRIBUTIONAL_DAMAGE.get(target).setValue(0d);
        }

        public static void addDmg(LivingEntity target, double additive) {
            RETRIBUTIONAL_DAMAGE.get(target).setValue(RETRIBUTIONAL_DAMAGE.get(target).getValue() + additive);
        }

    }
    public static class RetributiveArrowManager {
        public static boolean isRetributive(PersistentProjectileEntity target) {
            return RETRIBUTIVE_ARROW.get(target).isComponentTrue();
        }

        public static void setRetributive(PersistentProjectileEntity target, boolean set) {
            RETRIBUTIVE_ARROW.get(target).setComponent(set);
        }
    }

    public static class SnipingArrowManager {

        public static boolean is(PersistentProjectileEntity target) {
            return SNIPING_ARROW.get(target).isComponentTrue();
        }

        public static void setIs(PersistentProjectileEntity target, boolean val) {
            SNIPING_ARROW.get(target).setComponent(val);
        }

        public static Vec3d get(PersistentProjectileEntity target) {
            return SNIPING_ARROW_ORIGIN.get(target).get();
        }

        public static void set(PersistentProjectileEntity target, Vec3d vec) {
            SNIPING_ARROW_ORIGIN.get(target).set(vec);
        }

        public static double getSpeed(PersistentProjectileEntity target) {
            return SNIPING_SPEED_REF.get(target).getValue();
        }

        public static void setSpeedReference(PersistentProjectileEntity target, double speed) {
            SNIPING_SPEED_REF.get(target).setValue(speed);
        }

        public static double getDivergence(PersistentProjectileEntity target) {
            return SNIPING_SPEED_REF.get(target).getValue();
        }

        public static void setDivergenceReference(PersistentProjectileEntity target, double divergence) {
            SNIPING_SPEED_REF.get(target).setValue(divergence);
        }
    }
}

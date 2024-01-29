package dev.diamond.enderism.registry;

import dev.diamond.enderism.EnderismMod;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class InitDamageSources implements RegistryInitializer {
    public static RegistryKey<DamageType>
            BAMBOO_SPIKE, RETRIBUTION;


    @Override
    public void register() {
        BAMBOO_SPIKE = create(EnderismMod.id("bamboo_spike"));
        RETRIBUTION = create(EnderismMod.id("retribution"));
    }

    private static RegistryKey<DamageType> create(Identifier id) {
        return RegistryKey.of(RegistryKeys.DAMAGE_TYPE, id);
    }

    public static DamageSource get(Entity entity, RegistryKey<DamageType> type, @Nullable Entity sourceOrProjectile, @Nullable Entity attacker) {
        if (attacker != null) return entity.getDamageSources().create(type, sourceOrProjectile, attacker);
        if (sourceOrProjectile != null) return entity.getDamageSources().create(type, sourceOrProjectile);
        return entity.getDamageSources().create(type);
    }
}

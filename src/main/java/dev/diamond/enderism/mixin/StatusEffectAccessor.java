package dev.diamond.enderism.mixin;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(StatusEffect.class)
public interface StatusEffectAccessor {
    @Accessor("category")
    StatusEffectCategory accessType();
}

package dev.diamond.enderism.effect;


import dev.diamond.enderism.cca.EnderismCCA;
import dev.diamond.enderism.registry.InitDamageSources;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class RetributionEffect extends StatusEffect {
    public RetributionEffect() {
        super(StatusEffectCategory.HARMFUL, 0x520e05);
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        super.onRemoved(entity, attributes, amplifier);

        // Apply Retibutional Damage
        double attributedDamage = EnderismCCA.RetributionalDamageManager.getDmg(entity);
        entity.damage(InitDamageSources.get(entity, InitDamageSources.RETRIBUTION, null, null), (float) attributedDamage);
        EnderismCCA.RetributionalDamageManager.resetDmg(entity); // Reset Retributional Damage
    }
}

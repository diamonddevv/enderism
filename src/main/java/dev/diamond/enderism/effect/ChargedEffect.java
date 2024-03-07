package dev.diamond.enderism.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;


public class ChargedEffect extends StatusEffect {

    public ChargedEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xe6dc27);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {}

    public static float calculateDamage(float origin, float amplifier, float durationInTicks, float base, boolean enemyHasChargedEffect) {
        if (enemyHasChargedEffect) {
            return origin;
        } else {
            return (base * ((durationInTicks / 20) * amplifier));
        }

        // 5s remain, amp 1, attacked w/out charged = 0.5dmg attack bonus
        // 60s remain, amp 1, attacked w/out charged = 6.0dmg attack bonus
        // 60s remain, amp 1, attacked w/ charged = 0 dmg attack bonus
    }


}

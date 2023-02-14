package net.diamonddev.enderism.nbt;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;

public class EnderismNbt {

    private static final NbtStatusEffectInstanceComponent CHARM_EFFECT = new NbtStatusEffectInstanceComponent("effect");
    private static final NbtBooleanComponent CHARM_HAS_EFFECT = new NbtBooleanComponent("hasEffect");

    public static class CharmEffectManager {
        public static void set(ItemStack stack, StatusEffectInstance effect) {
            CHARM_EFFECT.write(stack.getOrCreateNbt(), effect);
            setHas(stack, true);
        }
        public static StatusEffectInstance get(ItemStack stack) {
            return CHARM_EFFECT.read(stack.getOrCreateNbt());
        }

        public static boolean has(ItemStack stack) {
            return CHARM_HAS_EFFECT.read(stack.getOrCreateNbt());
        }

        public static void setHas(ItemStack stack, boolean bl) {
            CHARM_HAS_EFFECT.write(stack.getOrCreateNbt(), bl);
        }

    }

}

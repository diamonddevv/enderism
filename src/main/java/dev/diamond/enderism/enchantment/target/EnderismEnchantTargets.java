package dev.diamond.enderism.enchantment.target;

import com.chocohead.mm.api.ClassTinkerers;
import dev.diamond.enderism.impl.AsmEarlyRiser;
import net.minecraft.enchantment.EnchantmentTarget;

public class EnderismEnchantTargets {
    public static EnchantmentTarget ELYTRA = ClassTinkerers.getEnum(EnchantmentTarget.class, AsmEarlyRiser.ENCHTARGET_ELYTRA);
}

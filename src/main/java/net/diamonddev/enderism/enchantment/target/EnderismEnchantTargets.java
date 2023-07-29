package net.diamonddev.enderism.enchantment.target;

import com.chocohead.mm.api.ClassTinkerers;
import net.diamonddev.enderism.impl.AsmEarlyRiser;
import net.minecraft.enchantment.EnchantmentTarget;

public class EnderismEnchantTargets {
    public static EnchantmentTarget ELYTRA = ClassTinkerers.getEnum(EnchantmentTarget.class, AsmEarlyRiser.ENCHTARGET_ELYTRA);
}

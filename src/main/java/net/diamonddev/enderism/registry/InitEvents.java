package net.diamonddev.enderism.registry;

import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.diamonddev.libgenetics.common.api.v1.util.helper.EnchantHelper;
import net.fabricmc.fabric.api.entity.event.v1.EntityElytraEvents;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ElytraItem;

public class InitEvents implements RegistryInitializer {
    @Override
    public void register() {
        EntityElytraEvents.ALLOW.register(entity -> !EnchantHelper.hasEnchantment(InitEnchants.SHACKLING_CURSE, entity.getEquippedStack(EquipmentSlot.CHEST)));
    }
}

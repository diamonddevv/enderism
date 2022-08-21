package net.diamonddev.enderism.enchantment.target;

import net.diamonddev.enderism.api.IdentifierBuilder;
import net.diamonddev.enderism.mixin.EnchantmentTargetMixin;
import net.fabricmc.fabric.api.entity.event.v1.FabricElytraItem;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.Item;

import static net.diamonddev.enderism.api.AbstractModIntegration.getItem;
import static net.diamonddev.enderism.integration.BetterEndIntegration.betterEndElytras;

public class ElytraEnchantTarget extends EnchantmentTargetMixin {

    public IdentifierBuilder idb = new IdentifierBuilder("betterend");

    public static boolean isEnchantableElytraItem(Item item) {
        return item instanceof ElytraItem || item instanceof FabricElytraItem || betterEndElytras.contains(item);
    }
    @Override
    public boolean isAcceptableItem(Item item) {
        return isEnchantableElytraItem(item); //todo: FIX PLS
    }
}


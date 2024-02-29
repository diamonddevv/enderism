package dev.diamond.enderism.advancement;

import dev.diamond.enderism.EnderismMod;
import dev.diamond.enderism.advancement.abstraction.AbstractManualTriggerAdvancementCriterion;
import dev.diamond.enderism.registry.InitEnchants;
import net.diamonddev.libgenetics.common.api.v1.util.helper.EnchantHelper;
import net.minecraft.item.CrossbowItem;
import net.minecraft.util.Identifier;

public class EmptyMulticlipAdvancementCriterion extends AbstractManualTriggerAdvancementCriterion {
    private static final String LEVEL_KEY = "level";

    @Override
    public boolean canTrigger(Conditions conditionsAccess, TriggerContext context) {
        if (context.hasUsedItem() &&
                context.getUsedItem().getItem() instanceof CrossbowItem &&
                EnchantHelper.hasEnchantment(InitEnchants.MULTICLIP, context.getUsedItem())) {

            if (conditionsAccess.json.has(LEVEL_KEY)) {
                return EnchantHelper.getEnchantmentLevel(context.getUsedItem(), InitEnchants.MULTICLIP) == conditionsAccess.json.get(LEVEL_KEY).getAsInt();
            }

            return true;
        } else return false;
    }

    @Override
    public Identifier getId() {
        return EnderismMod.id("empty_multiclip");
    }
}

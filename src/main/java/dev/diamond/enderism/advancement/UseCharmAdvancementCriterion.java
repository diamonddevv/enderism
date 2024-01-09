package dev.diamond.enderism.advancement;

import dev.diamond.enderism.EnderismMod;
import dev.diamond.enderism.advancement.abstraction.AbstractManualTriggerAdvancementCriterion;
import dev.diamond.enderism.item.CharmItem;
import dev.diamond.enderism.nbt.EnderismNbt;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import static dev.diamond.enderism.util.EnderismUtil.test;

public class UseCharmAdvancementCriterion extends AbstractManualTriggerAdvancementCriterion {

    public static final String CHARM_ITEM = "charm_item";
    public static final String EFFECT_KEY = "effect";

    @Override
    public Identifier getId() {
        return EnderismMod.id("use_charm");
    }

    @Override
    public boolean canTrigger(Conditions conditionsAccess, TriggerContext context) {
        if (context.hasUsedItem()) {
            if (conditionsAccess.json.has(CHARM_ITEM)) {
                if (!(Registries.ITEM.getId(context.getUsedItem().getItem()).toString()
                        .equals(conditionsAccess.json.get(CHARM_ITEM).getAsString()))) {
                    return false;
                }
            }
        }

        if (conditionsAccess.json.has(EFFECT_KEY)) {
            if (context.hasUsedItem()) {
                if (context.getUsedItem().getItem() instanceof CharmItem) {

                    var sei = EnderismNbt.CharmEffectManager.get(context.getUsedItem());
                    var fct = Registries.STATUS_EFFECT.getId(sei.getEffectType());
                    if (fct != null) return fct.toString().equals(conditionsAccess.json.get(EFFECT_KEY).getAsString());
                    else return false;
                } else return false;
            } else return false;
        } else {
            return true;
        }
    }
}

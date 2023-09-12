package net.diamonddev.enderism.advancement;

import net.diamonddev.enderism.EnderismMod;
import net.diamonddev.enderism.advancement.abstraction.AbstractManualTriggerAdvancementCriterion;
import net.diamonddev.enderism.item.CharmItem;
import net.diamonddev.enderism.nbt.EnderismNbt;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class UseCharmAdvancementCriterion extends AbstractManualTriggerAdvancementCriterion {
    @Override
    public Identifier getId() {
        return EnderismMod.id("use_charm");
    }

    @Override
    public boolean canTrigger(Conditions conditionsAccess, TriggerContext context) {
        if (conditionsAccess.json.has("effect")) {
            if (context.hasUsedItem()) {
                if (context.getUsedItem().getItem() instanceof CharmItem) {
                    var sei = EnderismNbt.CharmEffectManager.get(context.getUsedItem());
                    var fct = Registries.STATUS_EFFECT.getId(sei.getEffectType());
                    if (fct != null) return fct.toString().equals(conditionsAccess.json.get("effect").getAsString());
                    else return false;
                } else return false;
            } else return false;
        } else {
            return true;
        }
    }
}

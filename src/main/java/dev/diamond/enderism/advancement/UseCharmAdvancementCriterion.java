package dev.diamond.enderism.advancement;

import dev.diamond.enderism.EnderismMod;
import dev.diamond.enderism.advancement.abstraction.AbstractManualTriggerAdvancementCriterion;
import dev.diamond.enderism.item.CharmItem;
import dev.diamond.enderism.nbt.EnderismNbt;
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

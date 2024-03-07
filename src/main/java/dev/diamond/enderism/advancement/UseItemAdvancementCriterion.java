package dev.diamond.enderism.advancement;

import dev.diamond.enderism.EnderismMod;
import dev.diamond.enderism.advancement.abstraction.AbstractManualTriggerAdvancementCriterion;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class UseItemAdvancementCriterion extends AbstractManualTriggerAdvancementCriterion {
    @Override
    public boolean canTrigger(Conditions conditionsAccess, TriggerContext context) {
        if (context.hasUsedItem()) {
            String id = conditionsAccess.json.get("identifier").getAsString();
            return Registries.ITEM.getId(context.getUsedItem().getItem()).toString().equals(id);
        } else return false;
    }

    @Override
    public Identifier getId() {
        return EnderismMod.id("use_item");
    }
}

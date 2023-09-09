package net.diamonddev.enderism.advancement;

import net.diamonddev.enderism.EnderismMod;
import net.diamonddev.enderism.advancement.abstraction.AbstractManualTriggerAdvancementCriterion;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class UseInstrumentAdvancementCriterion extends AbstractManualTriggerAdvancementCriterion {
    @Override
    public Identifier getId() {
        return EnderismMod.id("use_instrument");
    }


    @Override
    public boolean canTrigger(Conditions conditionsAccess, TriggerContext context) {
        if (conditionsAccess.json.has("item_id")) {
            if (context.hasUsedItem()) {
                return Registries.ITEM.getId(context.getUsedItem().getItem()).toString()
                        .equals(conditionsAccess.json.get("item_id").getAsString()); // check equipped item id matches condition id
            } else return false;
        } else {
            return true;
        }
    }
}

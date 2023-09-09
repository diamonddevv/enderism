package net.diamonddev.enderism.advancement;

import net.diamonddev.enderism.EnderismMod;
import net.diamonddev.enderism.advancement.abstraction.AbstractManualTriggerAdvancementCriterion;
import net.minecraft.util.Identifier;

public class EatBoundCursedChorusAdvancementCriterion extends AbstractManualTriggerAdvancementCriterion {
    @Override
    public Identifier getId() {
        return EnderismMod.id("eat_bound_cursed_chorus");
    }

    @Override
    public boolean canTrigger(Conditions conditionsAccess, TriggerContext context) {
        return true;
    }
}

package dev.diamond.enderism.advancement;

import dev.diamond.enderism.EnderismMod;
import dev.diamond.enderism.advancement.abstraction.AbstractManualTriggerAdvancementCriterion;
import net.minecraft.util.Identifier;

public class BounceOnFibrousChorusAdvancementCriterion extends AbstractManualTriggerAdvancementCriterion {
    @Override
    public Identifier getId() {
        return EnderismMod.id("bounce_on_fibrous_chorus");
    }

    @Override
    public boolean canTrigger(Conditions conditionsAccess, TriggerContext context) {
        return true;
    }
}

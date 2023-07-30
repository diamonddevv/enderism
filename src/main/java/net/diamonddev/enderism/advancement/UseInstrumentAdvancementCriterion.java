package net.diamonddev.enderism.advancement;

import net.diamonddev.enderism.EnderismMod;
import net.diamonddev.enderism.advancement.abstraction.AbstractManualTriggerAdvancmentCriterion;
import net.minecraft.util.Identifier;

public class UseInstrumentAdvancementCriterion extends AbstractManualTriggerAdvancmentCriterion {
    @Override
    public Identifier getId() {
        return EnderismMod.id("use_instrument");
    }
}

package dev.diamond.enderism.registry;

import dev.diamond.enderism.advancement.BounceOnFibrousChorusAdvancementCriterion;
import dev.diamond.enderism.advancement.EatCursedChorusAdvancementCriterion;
import dev.diamond.enderism.advancement.UseCharmAdvancementCriterion;
import dev.diamond.enderism.advancement.UseInstrumentAdvancementCriterion;
import dev.diamond.enderism.advancement.abstraction.AbstractManualTriggerAdvancementCriterion;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.minecraft.advancement.criterion.Criteria;

public class InitAdvancementCriteria implements RegistryInitializer {

    public static final UseCharmAdvancementCriterion USE_CHARM = new UseCharmAdvancementCriterion();
    public static final UseInstrumentAdvancementCriterion USE_INSTRUMENT = new UseInstrumentAdvancementCriterion();
    public static final EatCursedChorusAdvancementCriterion EAT_BOUND_CURSED_CHORUS = new EatCursedChorusAdvancementCriterion();
    public static final BounceOnFibrousChorusAdvancementCriterion BOUNCE_ON_FIBROUS_CHORUS = new BounceOnFibrousChorusAdvancementCriterion();

    @Override
    public void register() {
        reg(USE_CHARM);
        reg(USE_INSTRUMENT);
        reg(EAT_BOUND_CURSED_CHORUS);
        reg(BOUNCE_ON_FIBROUS_CHORUS);
    }

    private static void reg(AbstractManualTriggerAdvancementCriterion adv) {
        Criteria.register(adv);
    }
}

package net.diamonddev.enderism.registry;

import net.diamonddev.enderism.advancement.UseCharmAdvancementCriterion;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.advancement.criterion.Criterion;

public class InitAdvancementCriterions implements RegistryInitializer {

    public static final Criterion<?> USE_CHARM = new UseCharmAdvancementCriterion();

    @Override
    public void register() {
        Criteria.register(USE_CHARM);
    }
}

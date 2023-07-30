package net.diamonddev.enderism.registry;

import net.diamonddev.enderism.advancement.UseAllInstrumentsAdvancementCriterion;
import net.diamonddev.enderism.advancement.UseCharmAdvancementCriterion;
import net.diamonddev.enderism.advancement.UseInstrumentAdvancementCriterion;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.minecraft.advancement.criterion.Criteria;

public class InitAdvancementCriteria implements RegistryInitializer {

    public static final UseCharmAdvancementCriterion USE_CHARM = new UseCharmAdvancementCriterion();
    public static final UseInstrumentAdvancementCriterion USE_INSTRUMENT = new UseInstrumentAdvancementCriterion();
    public static final UseAllInstrumentsAdvancementCriterion USE_ALL_INSTRUMENTS = new UseAllInstrumentsAdvancementCriterion();

    @Override
    public void register() {
        Criteria.register(USE_CHARM);
        Criteria.register(USE_INSTRUMENT);
        Criteria.register(USE_ALL_INSTRUMENTS);
    }
}

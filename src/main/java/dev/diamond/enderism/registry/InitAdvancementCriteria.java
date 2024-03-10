package dev.diamond.enderism.registry;

import dev.diamond.enderism.EnderismMod;
import dev.diamond.enderism.advancement.*;
import dev.diamond.enderism.advancement.abstraction.AbstractManualTriggerAdvancementCriterion;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.util.Identifier;

public class InitAdvancementCriteria implements RegistryInitializer {

    public static final UseCharmAdvancementCriterion USE_CHARM = new UseCharmAdvancementCriterion();
    public static final UseInstrumentAdvancementCriterion USE_INSTRUMENT = new UseInstrumentAdvancementCriterion();
    public static final EatCursedChorusAdvancementCriterion EAT_BOUND_CURSED_CHORUS = new EatCursedChorusAdvancementCriterion();
    public static final BounceOnFibrousChorusAdvancementCriterion BOUNCE_ON_FIBROUS_CHORUS = new BounceOnFibrousChorusAdvancementCriterion();
    public static final EmptyMulticlipAdvancementCriterion EMPTY_MULTICLIP = new EmptyMulticlipAdvancementCriterion();
    public static final AbstractManualTriggerAdvancementCriterion USE_STATIC_CORE = new AbstractManualTriggerAdvancementCriterion() {
        @Override
        public boolean canTrigger(Conditions conditionsAccess, TriggerContext context) {
            return true;
        }

        @Override
        public Identifier getId() {
            return EnderismMod.id("use_static_core");
        }
    };
    public static final AbstractManualTriggerAdvancementCriterion CAPTURE_LIGHTNING = new AbstractManualTriggerAdvancementCriterion() {
        @Override
        public boolean canTrigger(Conditions conditionsAccess, TriggerContext context) {
            return true;
        }

        @Override
        public Identifier getId() {
            return EnderismMod.id("capture_lightning");
        }
    };

    @Override
    public void register() {
        reg(USE_CHARM);
        reg(USE_INSTRUMENT);
        reg(EAT_BOUND_CURSED_CHORUS);
        reg(BOUNCE_ON_FIBROUS_CHORUS);
        reg(EMPTY_MULTICLIP);
        reg(USE_STATIC_CORE);
        reg(CAPTURE_LIGHTNING);
    }

    private static void reg(AbstractManualTriggerAdvancementCriterion adv) {
        Criteria.register(adv);
    }
}

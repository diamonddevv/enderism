package net.diamonddev.enderism.advancement.abstraction;

import com.google.gson.JsonObject;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.class_5258;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public abstract class AbstractManualTriggerAdvancmentCriterion extends AbstractCriterion<AbstractManualTriggerAdvancmentCriterion.Conditions> {


    @Override
    protected AbstractManualTriggerAdvancmentCriterion.Conditions conditionsFromJson(JsonObject json, class_5258 arg, AdvancementEntityPredicateDeserializer advancementEntityPredicateDeserializer) {
        return new Conditions(getId(), arg);
    }

    public void trigger(ServerPlayerEntity player) {
        this.trigger(player, conditions -> true);
    }

    public static class Conditions extends AbstractCriterionConditions {
        public Conditions(Identifier id, class_5258 playerPredicate) {
            super(id, playerPredicate);
        }
    }
}

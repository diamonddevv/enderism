package net.diamonddev.enderism.advancement;


import com.google.gson.JsonObject;
import net.diamonddev.enderism.EnderismMod;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.class_5258;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.function.Predicate;

public class UseCharmAdvancementCriterion extends AbstractCriterion<UseCharmAdvancementCriterion.Conditions> {

    @Override
    protected Conditions conditionsFromJson(JsonObject json, class_5258 arg, AdvancementEntityPredicateDeserializer advancementEntityPredicateDeserializer) {
        return null;
    }

    @Override
    public Identifier getId() {
        return EnderismMod.id("use_charm");
    }

    @Override
    protected void trigger(ServerPlayerEntity player, Predicate<Conditions> predicate) {
        super.trigger(player, predicate);
    }

    public static class Conditions extends AbstractCriterionConditions {
        public Conditions(Identifier id, class_5258 playerPredicate) {
            super(id, playerPredicate);
        }
    }
}

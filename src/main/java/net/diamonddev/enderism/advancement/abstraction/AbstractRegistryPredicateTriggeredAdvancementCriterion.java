package net.diamonddev.enderism.advancement.abstraction;

import com.google.gson.JsonObject;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.class_5258;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.registry.Registry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.function.Predicate;

import static net.diamonddev.enderism.advancement.abstraction.AbstractRegistryPredicateTriggeredAdvancementCriterion.*;

public abstract class AbstractRegistryPredicateTriggeredAdvancementCriterion<T> extends AbstractCriterion<Conditions<T>> {

    public abstract Registry<T> getRegistry();
    public abstract Predicate<T> getPredicate();

    @Override
    protected Conditions conditionsFromJson(JsonObject json, class_5258 arg, AdvancementEntityPredicateDeserializer advancementEntityPredicateDeserializer) {
        return new Conditions(getId(), arg);
    }

    public void trigger(ServerPlayerEntity player, T obj) {
        this.trigger(player, conditions -> conditions.hasMetAll(getRegistry(), getPredicate(), obj));
    }

    public static class Conditions<T> extends AbstractCriterionConditions {

        private ArrayList<T> objsMet;

        public Conditions(Identifier id, class_5258 playerPredicate) {
            super(id, playerPredicate);
            objsMet = new ArrayList<>();
        }

        public boolean hasMetAll(Registry<T> registry, Predicate<T> predicate, T obj) {
            if (!objsMet.contains(obj)) {
                objsMet.add(obj);
                return objsMet.containsAll(registry.stream().filter(predicate).toList());
            } return false;
        }
    }
}

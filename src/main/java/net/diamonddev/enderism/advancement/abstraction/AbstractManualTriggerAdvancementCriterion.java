package net.diamonddev.enderism.advancement.abstraction;

import com.google.gson.JsonObject;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.class_5258;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.Optional;

public abstract class AbstractManualTriggerAdvancementCriterion extends AbstractCriterion<AbstractManualTriggerAdvancementCriterion.Conditions> {

    public abstract boolean canTrigger(Conditions conditionsAccess, TriggerContext context);


    @Override
    protected AbstractManualTriggerAdvancementCriterion.Conditions conditionsFromJson(JsonObject json, class_5258 arg, AdvancementEntityPredicateDeserializer advancementEntityPredicateDeserializer) {
        return new Conditions(getId(), arg, json);
    }

    public void trigger(ServerPlayerEntity player) {

        TriggerContext ctx = new TriggerContext();
        ctx.player = player;
        ctx.usedItem = Optional.empty();

        this.trigger(player, conditions -> canTrigger(conditions, ctx));
    }
    public void trigger(ServerPlayerEntity player, ItemStack stack) {

        TriggerContext ctx = new TriggerContext();
        ctx.player = player;
        ctx.usedItem = Optional.of(stack);

        this.trigger(player, conditions -> canTrigger(conditions, ctx));
    }

    public static class Conditions extends AbstractCriterionConditions {
        public final JsonObject json;

        public Conditions(Identifier id, class_5258 playerPredicate, JsonObject json) {
            super(id, playerPredicate);
            this.json = json;
        }
    }

    public static class TriggerContext {
        private ServerPlayerEntity player;

        private Optional<ItemStack> usedItem;

        public ServerPlayerEntity getPlayer() {
            return player;
        }

        public ItemStack getUsedItem() {
            return usedItem.get();
        }
        public boolean hasUsedItem() {
            return usedItem.isPresent();
        }

    }
}

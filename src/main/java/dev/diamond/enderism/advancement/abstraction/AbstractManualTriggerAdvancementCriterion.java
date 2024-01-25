package dev.diamond.enderism.advancement.abstraction;

import com.google.gson.JsonObject;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.Optional;

public abstract class AbstractManualTriggerAdvancementCriterion extends AbstractCriterion<AbstractManualTriggerAdvancementCriterion.Conditions> {

    public abstract boolean canTrigger(Conditions conditionsAccess, TriggerContext context);

    public abstract Identifier getId();

    @Override
    protected AbstractManualTriggerAdvancementCriterion.Conditions conditionsFromJson(JsonObject json, LootContextPredicate playerPredicate, AdvancementEntityPredicateDeserializer predicateDeserializer) {
        return new Conditions(getId(), playerPredicate, json);
    }

    public void trigger(ServerPlayerEntity player) {

        TriggerContext ctx = new TriggerContext();
        ctx.player = player;
        ctx.usedItem = null;

        this.trigger(player, conditions -> canTrigger(conditions, ctx));
    }
    public void trigger(ServerPlayerEntity player, ItemStack stack) {

        TriggerContext ctx = new TriggerContext();
        ctx.player = player;
        ctx.usedItem = stack;

        this.trigger(player, conditions -> canTrigger(conditions, ctx));
    }

    public void trigger(ServerPlayerEntity player, JsonObject json) {

        TriggerContext ctx = new TriggerContext();
        ctx.player = player;
        ctx.jsonData = json;

        this.trigger(player, conditions -> canTrigger(conditions, ctx));
    }

    public void trigger(ServerPlayerEntity player, ItemStack stack, JsonObject json) {

        TriggerContext ctx = new TriggerContext();
        ctx.player = player;
        ctx.usedItem = stack;
        ctx.jsonData = json;

        this.trigger(player, conditions -> canTrigger(conditions, ctx));
    }

    public static class Conditions extends AbstractCriterionConditions {
        public final JsonObject json;

        public Conditions(Identifier id, LootContextPredicate playerPredicate, JsonObject json) {
            super(id, playerPredicate);
            this.json = json;
        }
    }

    public static class TriggerContext {
        private ServerPlayerEntity player;
        private ItemStack usedItem;
        private JsonObject jsonData;

        public ServerPlayerEntity getPlayer() {
            return player;
        }

        public ItemStack getUsedItem() {
            return usedItem;
        }
        public boolean hasUsedItem() {
            return usedItem != null;
        }

        public JsonObject getJson() {
            return jsonData;
        }

    }
}

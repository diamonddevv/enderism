package dev.diamond.enderism.advancement;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import dev.diamond.enderism.EnderismMod;
import dev.diamond.enderism.advancement.abstraction.AbstractManualTriggerAdvancementCriterion;
import dev.diamond.enderism.item.CharmItem;
import dev.diamond.enderism.nbt.EnderismNbt;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import static dev.diamond.enderism.util.EnderismUtil.test;

public class UseCharmAdvancementCriterion extends AbstractManualTriggerAdvancementCriterion {

    public static final String CHARM_ITEM = "charm_item";
    public static final String EFFECT_KEY = "effect";
    public static final String SELF_KEY = "self";

    @Override
    public Identifier getId() {
        return EnderismMod.id("use_charm");
    }

    @Override
    public boolean canTrigger(Conditions conditionsAccess, TriggerContext context) {
        boolean ambiguous = !conditionsAccess.json.has(SELF_KEY);

        if (!ambiguous) {
            if (conditionsAccess.json.has(SELF_KEY)) {
                if (context.getJson() != null) {
                    if (context.getJson().has(SELF_KEY)) {
                        boolean contextJson = context.getJson().get(SELF_KEY).getAsBoolean();
                        boolean conditionJson = conditionsAccess.json.get(SELF_KEY).getAsBoolean();

                        if (!(contextJson && conditionJson)) {
                            return false;
                        }

                    } else return false;
                } else return false;
            }
        }

        if (context.hasUsedItem()) {
            if (conditionsAccess.json.has(CHARM_ITEM)) {
                if (!(Registries.ITEM.getId(context.getUsedItem().getItem()).toString()
                        .equals(conditionsAccess.json.get(CHARM_ITEM).getAsString()))) {
                    return false;
                }
            }
        }

        if (conditionsAccess.json.has(EFFECT_KEY)) {
            if (context.hasUsedItem()) {
                if (context.getUsedItem().getItem() instanceof CharmItem) {

                    var sei = EnderismNbt.CharmEffectManager.get(context.getUsedItem());
                    var fct = Registries.STATUS_EFFECT.getId(sei.getEffectType());
                    if (fct != null) return fct.toString().equals(conditionsAccess.json.get(EFFECT_KEY).getAsString());
                    else return false;
                } else return false;
            } else return false;
        } else {
            return true;
        }
    }


    private static final Gson gson = new Gson();

    public static JsonObject buildContextJson(boolean selfAfflicted) {
        ContextJsonBean ctx = new ContextJsonBean();
        ctx.isSelfAfflicted = selfAfflicted;

        return gson.fromJson(gson.toJson(ctx, ContextJsonBean.class), JsonObject.class);
    }

    public static class ContextJsonBean {
        @SerializedName(SELF_KEY)
        public boolean isSelfAfflicted;
    }
}

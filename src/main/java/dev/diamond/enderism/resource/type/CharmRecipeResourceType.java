package dev.diamond.enderism.resource.type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import dev.diamond.enderism.EnderismMod;
import dev.diamond.enderism.util.EnderismUtil;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionResourceType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

public class CharmRecipeResourceType implements CognitionResourceType {

    public static StatusEffectInstance parseStatusEffectInstance(JsonElement element) {
        JsonObject json = element.getAsJsonObject();
        StatusEffect effect = EnderismUtil.registryGetOrElse(Registries.STATUS_EFFECT, new Identifier(json.get("effect").getAsString()), StatusEffects.INSTANT_DAMAGE);
        int amplifier = json.get("amplifier").getAsInt();
        int duration = json.get("duration").getAsInt();
        return new StatusEffectInstance(effect, duration, amplifier);
    }

    public static class CharmRecipeResourceBean {
        @SerializedName(INGREDIENT)
        public String ingredient;

        @SerializedName(OUTEFFECT)
        public JsonElement effect;
    }

    public static final String INGREDIENT = "ingredient";
    public static final String OUTEFFECT = "out_effect";
    @Override
    public Identifier getId() {
        return EnderismMod.id("charm_recipe");
    }

    @Override
    public void addJsonKeys(ArrayList<String> keys) {
        keys.add(INGREDIENT);
        keys.add(OUTEFFECT);
    }
}

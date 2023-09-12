package net.diamonddev.enderism.advancement;

import com.google.gson.JsonObject;
import net.diamonddev.enderism.EnderismMod;
import net.diamonddev.enderism.advancement.abstraction.AbstractManualTriggerAdvancementCriterion;
import net.diamonddev.enderism.nbt.EnderismNbt;
import net.minecraft.util.Identifier;

public class EatCursedChorusAdvancementCriterion extends AbstractManualTriggerAdvancementCriterion {
    @Override
    public Identifier getId() {
        return EnderismMod.id("eat_cursed_chorus");
    }

    private static final String
            KEY_BIND = "bind",
            KEY_IS_BOUND = "is_bound",
            KEY_IS_PLAYERBOUND = "is_playerbound";

    @Override
    public boolean canTrigger(Conditions conditionsAccess, TriggerContext context) {
        if (conditionsAccess.json.has(KEY_BIND)) {
            JsonObject json = conditionsAccess.json.getAsJsonObject(KEY_BIND);
            boolean checkForBound = json.get(KEY_IS_BOUND).getAsBoolean();

            boolean bound = EnderismNbt.CursedChorusBindManager.isBound(context.getUsedItem());

            if (checkForBound) {
                boolean typeMatters = false;
                boolean playerbindType = false;
                if (json.has(KEY_IS_PLAYERBOUND)) {
                    typeMatters = true;
                    playerbindType = json.get(KEY_IS_PLAYERBOUND).getAsBoolean();
                }
                return typeMatters ? (playerbindType == EnderismNbt.CursedChorusBindManager.isPlayerbound(context.getUsedItem())) && bound : bound;
            } else {
                return !bound;
            }


        } return true;
    }
}

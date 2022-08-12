package net.diamonddev.enderism.init;

import net.diamonddev.enderism.api.Registerable;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public class GameruleInit implements Registerable {

    public static GameRules.Key<GameRules.BooleanRule> UPTHRUST_NO_DRAG;
    public static GameRules.Key<GameRules.BooleanRule> ELYTRA_FIREWORKS;


    @Override
    public void register() {
        UPTHRUST_NO_DRAG = GameRuleRegistry.register("upthrustNoDrag", GameRules.Category.PLAYER, GameRuleFactory.createBooleanRule(true));
        ELYTRA_FIREWORKS = GameRuleRegistry.register("shouldFireworksBoostElytra", GameRules.Category.PLAYER, GameRuleFactory.createBooleanRule(false));
    }
}

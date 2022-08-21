package net.diamonddev.enderism.init;

import net.diamonddev.enderism.api.Registerable;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public class GameruleInit implements Registerable {

    public static GameRules.Key<GameRules.BooleanRule> UPTHRUST_NO_DRAG;
    public static GameRules.Key<GameRules.IntRule> UPTHRUST_BOOSTS_PER_LEVEL;
    public static GameRules.Key<GameRules.BooleanRule> ELYTRA_FIREWORKS;


    @Override
    public void register() {
        UPTHRUST_NO_DRAG = GameRuleRegistry.register("upthrustNoDrag", GameRules.Category.PLAYER, GameRuleFactory.createBooleanRule(true));
        UPTHRUST_BOOSTS_PER_LEVEL = GameRuleRegistry.register("upthrustBoostsPerLevel", GameRules.Category.PLAYER, GameRuleFactory.createIntRule(1));
        ELYTRA_FIREWORKS = GameRuleRegistry.register("shouldFireworksBoostElytra", GameRules.Category.PLAYER, GameRuleFactory.createBooleanRule(false));
    }
}

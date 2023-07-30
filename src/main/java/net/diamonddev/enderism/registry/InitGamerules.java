package net.diamonddev.enderism.registry;

import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public class InitGamerules implements RegistryInitializer {

    public static GameRules.Key<GameRules.BooleanRule> ELYTRA_FIREWORKS;
    public static GameRules.Key<GameRules.IntRule> CHORUSKIRMISH_CHANCE;

    @Override
    public void register() {
        ELYTRA_FIREWORKS = GameRuleRegistry.register("fireworksBoostElytra", GameRules.Category.PLAYER, GameRuleFactory.createBooleanRule(false));
        CHORUSKIRMISH_CHANCE = GameRuleRegistry.register("choruskirmishChance", GameRules.Category.MISC, GameRuleFactory.createIntRule(25));
    }
}

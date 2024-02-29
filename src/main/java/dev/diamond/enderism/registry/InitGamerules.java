package dev.diamond.enderism.registry;

import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public class InitGamerules implements RegistryInitializer {

    public static GameRules.Key<GameRules.BooleanRule> ELYTRA_FIREWORKS;
    public static GameRules.Key<GameRules.IntRule> CHORUSKIRMISH_CHANCE;
    public static GameRules.Key<GameRules.BooleanRule> CURSED_CHORUS_PLAYERBINDING;
    public static GameRules.Key<GameRules.IntRule> RETRIBUTION_LENGTH;
    public static GameRules.Key<GameRules.IntRule> RETRIBUTION_STRENGTH;
    public static GameRules.Key<GameRules.IntRule> FIBROUS_CHORUS_BOUNCE_POWER_TIMES_HUNDRED;
    public static GameRules.Key<GameRules.IntRule> FIBROUS_CHORUS_MAX_BOUNCE_POWER_TIMES_HUNDRED;

    @Override
    public void register() {
        ELYTRA_FIREWORKS = GameRuleRegistry.register("fireworksBoostElytra", GameRules.Category.PLAYER, GameRuleFactory.createBooleanRule(false));
        CHORUSKIRMISH_CHANCE = GameRuleRegistry.register("choruskirmishChance", GameRules.Category.MISC, GameRuleFactory.createIntRule(25));
        CURSED_CHORUS_PLAYERBINDING = GameRuleRegistry.register("cursedChorusFruitCanPlayerbind", GameRules.Category.PLAYER, GameRuleFactory.createBooleanRule(true));
        RETRIBUTION_LENGTH = GameRuleRegistry.register("retributionLength", GameRules.Category.MOBS, GameRuleFactory.createIntRule(400));
        RETRIBUTION_STRENGTH = GameRuleRegistry.register("retributionStrength", GameRules.Category.MOBS, GameRuleFactory.createIntRule(0));
        FIBROUS_CHORUS_BOUNCE_POWER_TIMES_HUNDRED = GameRuleRegistry.register("fibrousChorusBouncePowerTimesHundred", GameRules.Category.MOBS, GameRuleFactory.createIntRule(150));
        FIBROUS_CHORUS_MAX_BOUNCE_POWER_TIMES_HUNDRED = GameRuleRegistry.register("fibrousChorusMaxBouncePowerTimesHundred", GameRules.Category.MOBS, GameRuleFactory.createIntRule(500));
    }
}

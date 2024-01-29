package dev.diamond.enderism.registry;

import dev.diamond.enderism.EnderismMod;
import dev.diamond.enderism.effect.ChoruskirmishEffect;
import dev.diamond.enderism.effect.RetributionEffect;
import dev.diamond.enderism.effect.SilencingEffect;
import dev.diamond.enderism.effect.VoidRecallEffect;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class InitEffects implements RegistryInitializer {

    public static StatusEffect VOID_RECALL = new VoidRecallEffect();
    public static StatusEffect CHORUSKIRMISH = new ChoruskirmishEffect();
    public static StatusEffect SILENCING = new SilencingEffect();
    public static StatusEffect RETRIBUTION = new RetributionEffect();

    @Override
    public void register() {
        Registry.register(Registries.STATUS_EFFECT, EnderismMod.id("void_recall"), VOID_RECALL);
        Registry.register(Registries.STATUS_EFFECT, EnderismMod.id("choruskirmish"), CHORUSKIRMISH);
        Registry.register(Registries.STATUS_EFFECT, EnderismMod.id("silencing"), SILENCING);
        Registry.register(Registries.STATUS_EFFECT, EnderismMod.id("retribution"), RETRIBUTION);
    }
}

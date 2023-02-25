package net.diamonddev.enderism.registry;

import net.diamonddev.enderism.EnderismMod;
import net.diamonddev.enderism.effect.ChoruskirmishEffect;
import net.diamonddev.enderism.effect.VoidRecallEffect;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class InitEffects implements RegistryInitializer {

    public static StatusEffect VOID_RECALL = new VoidRecallEffect();
    public static StatusEffect CHORUSKIRMISH = new ChoruskirmishEffect();

    @Override
    public void register() {
        Registry.register(Registries.STATUS_EFFECT, EnderismMod.id("void_recall"), VOID_RECALL);
        Registry.register(Registries.STATUS_EFFECT, EnderismMod.id("choruskirmish"), CHORUSKIRMISH);
    }
}

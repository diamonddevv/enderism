package net.diamonddev.enderism.init;

import net.diamonddev.enderism.EnderismMod;
import net.diamonddev.enderism.effect.ChoruskirmishEffect;
import net.diamonddev.enderism.effect.EndPoisonEffect;
import net.diamonddev.enderism.effect.VoidRecallEffect;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.registry.Registry;

public class EffectInit implements RegistryInitializer {

    public static StatusEffect VOID_RECALL = new VoidRecallEffect();
    public static StatusEffect CHORUSKIRMISH = new ChoruskirmishEffect();
    public static StatusEffect END_POISON = new EndPoisonEffect();

    @Override
    public void register() {
        Registry.register(Registry.STATUS_EFFECT, EnderismMod.id.build("void_recall"), VOID_RECALL);
        Registry.register(Registry.STATUS_EFFECT, EnderismMod.id.build("choruskirmish"), CHORUSKIRMISH);
        Registry.register(Registry.STATUS_EFFECT, EnderismMod.id.build("end_poison"), END_POISON);
    }
}

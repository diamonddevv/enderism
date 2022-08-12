package net.diamonddev.enderism.init;

import net.diamonddev.enderism.api.Identifier;
import net.diamonddev.enderism.api.Registerable;
import net.diamonddev.enderism.effect.VoidRecallEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.registry.Registry;

public class EffectInit implements Registerable {

    public static StatusEffect VOID_RECALL = new VoidRecallEffect();

    @Override
    public void register() {
        Registry.register(Registry.STATUS_EFFECT, new Identifier("void_recall"), VOID_RECALL);
    }
}

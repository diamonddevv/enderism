package net.diamonddev.enderism.client;

import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import ladysnake.satin.api.managed.uniform.Uniform1f;
import net.diamonddev.enderism.EnderismMod;
import net.diamonddev.enderism.init.EffectInit;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.effect.StatusEffect;

public class EnderismClient implements ClientModInitializer {

    // End Poison Shader
    private static final ManagedShaderEffect END_POISON_SHADER = ShaderEffectManager.getInstance()
            .manage(EnderismMod.id.build("shaders/post/end_poison.json"));
    private static final Uniform1f sTime = END_POISON_SHADER.findUniform1f("STime");

    @Override
    public void onInitializeClient() {
        ShaderEffectRenderCallback.EVENT.register(tickDelta -> {
            if (MinecraftClient.getInstance().player != null && MinecraftClient.getInstance().player.hasStatusEffect(EffectInit.END_POISON) && MinecraftClient.getInstance().world != null) {
                END_POISON_SHADER.render(tickDelta);
                sTime.set((MinecraftClient.getInstance().world.getTime() + tickDelta) / 20);
            }
        });
    }
}

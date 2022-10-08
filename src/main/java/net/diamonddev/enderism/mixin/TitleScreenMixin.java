package net.diamonddev.enderism.mixin;

import net.diamonddev.enderism.integration.AbstractModIntegration;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
	@Inject(at = @At("HEAD"), method = "init()V")
	private void enderism$callIntegrationInitializations(CallbackInfo info) {
		AbstractModIntegration.integrations.forEach(AbstractModIntegration::onModsLoaded);
	}
}

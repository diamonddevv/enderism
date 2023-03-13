package net.diamonddev.enderism.resource;

import net.diamonddev.enderism.EnderismMod;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionDataListener;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionDataResource;
import net.minecraft.util.Identifier;

public class EnderismCharmRecipeListener extends CognitionDataListener {
    public EnderismCharmRecipeListener() {
        super("Enderism Charm Recipes", EnderismMod.id("enderism_charm_recipes"), "charm_recipes");
    }

    @Override
    public void onReloadForEachResource(CognitionDataResource resource, Identifier path) {
    }
}

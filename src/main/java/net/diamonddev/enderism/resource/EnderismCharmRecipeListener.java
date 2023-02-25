package net.diamonddev.enderism.resource;

import net.diamonddev.enderism.EnderismMod;
import net.diamonddev.libgenetics.common.api.v1.dataloader.DataLoaderListener;

public class EnderismCharmRecipeListener extends DataLoaderListener {
    public EnderismCharmRecipeListener() {
        super("Enderism Charm Recipes", EnderismMod.id("enderism_charm_recipes"), "charm_recipes");
    }
}

package dev.diamond.enderism.resource.type;

import dev.diamond.enderism.EnderismMod;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionResourceType;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

public class LightningRecipeResourceType implements CognitionResourceType {


    public static final String
            FROM = "from",
            TO = "to";

    @Override
    public Identifier getId() {
        return EnderismMod.id("lightning_recipe");
    }

    @Override
    public void addJsonKeys(ArrayList<String> keys) {
        keys.add(FROM);
        keys.add(TO);
    }
}

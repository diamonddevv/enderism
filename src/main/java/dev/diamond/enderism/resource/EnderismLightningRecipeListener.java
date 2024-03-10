package dev.diamond.enderism.resource;

import com.google.gson.JsonObject;
import dev.diamond.enderism.EnderismMod;
import dev.diamond.enderism.network.SendJsonObject;
import dev.diamond.enderism.registry.InitEvents;
import dev.diamond.enderism.registry.InitResourceListener;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionDataListener;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionDataResource;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionResourceManager;
import net.minecraft.util.Identifier;

import java.util.List;

public class EnderismLightningRecipeListener extends CognitionDataListener {
    public EnderismLightningRecipeListener() {
        super("Enderism Lightning Recipe Manager", EnderismMod.id("enderism_lightning_recipes"), "lightning_recipes");
    }

    @Override
    public void onReloadForEachResource(CognitionDataResource resource, Identifier path) {
    }

    @Override
    public void onFinishReload() {
        List<JsonObject> arr = this.getManager().CACHE.getOrCreateKey(InitResourceListener.LIGHTNING_TYPE).stream().map(cdr -> cdr.getAsClass(JsonObject.class)).toList();
        arr.forEach(json -> json.addProperty(CognitionResourceManager.IDPARAM, InitResourceListener.LIGHTNING_TYPE.getId().toString()));
        InitEvents.dataSetBeans.add(new SendJsonObject.DataSetBean(arr));
    }

    @Override
    public void onClearCachePhase() {

    }
}

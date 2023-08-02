package net.diamonddev.enderism.resource;

import com.google.gson.JsonObject;
import net.diamonddev.enderism.EnderismMod;
import net.diamonddev.enderism.network.SendJsonObject;
import net.diamonddev.enderism.registry.InitEvents;
import net.diamonddev.enderism.registry.InitResourceListener;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionDataListener;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionDataResource;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionResourceManager;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.networking.api.ServerPlayConnectionEvents;

import java.util.ArrayList;
import java.util.List;

public class EnderismCharmRecipeListener extends CognitionDataListener {

    public EnderismCharmRecipeListener() {
        super("Enderism Charm Recipes", EnderismMod.id("enderism_charm_recipes"), "charm_recipes");
    }

    @Override
    public void onReloadForEachResource(CognitionDataResource resource, Identifier path) {
    }

    @Override
    public void onFinishReload() {
        List<JsonObject> arr = this.getManager().CACHE.getOrCreateKey(InitResourceListener.CHARM_TYPE).stream().map(cdr -> cdr.getAsClass(JsonObject.class)).toList();
        arr.forEach(json -> json.addProperty(CognitionResourceManager.IDPARAM, InitResourceListener.CHARM_TYPE.getId().toString()));
        InitEvents.dataSetBeans.add(new SendJsonObject.DataSetBean(arr));
    }

    @Override
    public void onClearCachePhase() {

    }
}

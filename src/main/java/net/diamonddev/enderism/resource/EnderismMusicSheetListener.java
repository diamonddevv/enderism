package net.diamonddev.enderism.resource;

import com.google.gson.JsonObject;
import net.diamonddev.enderism.EnderismMod;
import net.diamonddev.enderism.network.SendJsonObject;
import net.diamonddev.enderism.registry.InitEvents;
import net.diamonddev.enderism.registry.InitResourceListener;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionDataListener;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionDataResource;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionRegistry;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionResourceManager;
import net.minecraft.util.Identifier;

import java.util.List;

public class EnderismMusicSheetListener extends CognitionDataListener {
    public EnderismMusicSheetListener() {
        super("Enderism Music Sheets", EnderismMod.id("enderism_music_sheets"), "music_sheets");
    }

    @Override
    public void onReloadForEachResource(CognitionDataResource resource, Identifier path) {
    }

    @Override
    public void onFinishReload() {
        List<JsonObject> arr = this.getManager().CACHE.getOrCreateKey(InitResourceListener.MUSIC_TYPE).stream().map(cdr -> cdr.getAsClass(JsonObject.class)).toList();
        arr.forEach(json -> json.addProperty(CognitionResourceManager.IDPARAM, InitResourceListener.MUSIC_TYPE.getId().toString()));
        InitEvents.dataSetBeans.add(new SendJsonObject.DataSetBean(arr));
    }

    @Override
    public void onClearCachePhase() {

    }
}

package dev.diamond.enderism.resource;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dev.diamond.enderism.item.music.InstrumentWrapper;
import dev.diamond.enderism.network.SendJsonObject;
import dev.diamond.enderism.resource.type.MusicInstrumentResourceType;
import dev.diamond.enderism.EnderismMod;
import dev.diamond.enderism.item.music.InstrumentBean;
import dev.diamond.enderism.registry.InitEvents;
import dev.diamond.enderism.registry.InitResourceListener;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionDataListener;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionDataResource;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionResourceManager;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.function.BiFunction;

public class EnderismMusicalInstrumentListener extends CognitionDataListener {
    public EnderismMusicalInstrumentListener() {
        super("MusicalInstrumentDataListenerManager", EnderismMod.id("musical_instrument_listener"),
                "instruments");
    }

    @Override
    public void onReloadForEachResource(CognitionDataResource resource, Identifier path) {
    }

    private static final String TRANSIENT_ID_FIELD = "transientId";
    public static final BiFunction<JsonObject, Gson, InstrumentWrapper> REMAPPER = (obj, gson) -> {
        InstrumentBean bean = gson.fromJson(obj, InstrumentBean.class); // new epic client based remapping
        bean.nonSerializedIdentifier = obj.get("transientId").getAsString(); // we need to re-add the transient field because it's transient
        return new InstrumentWrapper(bean);
    };

    @Override
    public void onFinishReload() {
        List<JsonObject> arr = this.getManager().CACHE.getOrCreateKey(InitResourceListener.INSTRUMENT_TYPE).stream().map(cdr -> {
            JsonObject json = cdr.getAsClass(JsonObject.class);
            boolean add = json.has(MusicInstrumentResourceType.ADDITIVE);

            if (!add) json.addProperty("transientId", MusicInstrumentResourceType.getNSI(cdr.getId())); // we need to add the transient id because it is transient
            else json.addProperty("transientId", json.get(MusicInstrumentResourceType.ADDITIVE).getAsString());
            return json;
        }).toList();
        arr.forEach(json -> json.addProperty(CognitionResourceManager.IDPARAM, InitResourceListener.INSTRUMENT_TYPE.getId().toString()));

        InitEvents.dataSetBeans.add(new SendJsonObject.DataSetBean(arr));
    }

    @Override
    public void onClearCachePhase() {

    }
}

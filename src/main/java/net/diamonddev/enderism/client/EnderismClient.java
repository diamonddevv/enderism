package net.diamonddev.enderism.client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.diamonddev.enderism.item.music.MusicSheetBean;
import net.diamonddev.enderism.network.InitPackets;
import net.diamonddev.enderism.util.EnderismUtil;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionResourceType;
import net.diamonddev.libgenetics.common.api.v1.network.nerve.NervePacketRegistry;
import net.diamonddev.libgenetics.common.api.v1.util.KeyedArrayCache;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class EnderismClient implements ClientModInitializer {
    private static final Gson GSON = new Gson();


    public static KeyedArrayCache<CognitionResourceType, JsonObject> NETWORKED_COGNITION_DATA = new KeyedArrayCache<>();
    public static <T> List<T> getAllAsT(CognitionResourceType type, BiFunction<JsonObject, Gson, T> remapper) {
        ArrayList<JsonObject> objs = NETWORKED_COGNITION_DATA.getOrCreateKey(type);
        ArrayList<T> newArr = new ArrayList<>();
        objs.forEach(obj -> newArr.add(remapper.apply(obj, GSON)));
        return newArr;
    }

    @Override
    public void onInitializeClient(ModContainer mod) {
        NervePacketRegistry.initClientS2CReciever(NervePacketRegistry.getRegistryReference(InitPackets.JSON));
        NervePacketRegistry.initClientS2CReciever(NervePacketRegistry.getRegistryReference(InitPackets.SHCI));
    }
}

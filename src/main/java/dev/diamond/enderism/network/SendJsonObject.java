package dev.diamond.enderism.network;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dev.diamond.enderism.EnderismMod;
import dev.diamond.enderism.client.EnderismClient;
import dev.diamond.enderism.impl.GeneralModCompat;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.*;
import net.diamonddev.libgenetics.common.api.v1.network.nerve.NerveNetworker;
import net.diamonddev.libgenetics.common.api.v1.network.nerve.NerveS2CPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class SendJsonObject implements NerveS2CPacket<SendJsonObject, SendJsonObject.ActualData> {

    private static final Gson GSON = new Gson();

    @Override
    public ClientPlayNetworking.PlayChannelHandler receive(Identifier channel) {
        return ((client, handler, buf, responseSender) -> {
            AtomicInteger count = new AtomicInteger();
            AtomicLong size = new AtomicLong();

            EnderismClient.NETWORKED_COGNITION_DATA.clear();
            readDelegate(read(buf).buf).jsons.forEach(json -> {
                size.addAndGet(json.getBytes().length);
                JsonObject obj = GSON.fromJson(json, JsonObject.class);
                CognitionResourceType type = null;
                for (CognitionDataListener listener : CognitionRegistry.listeners) {
                    if (obj.has(CognitionResourceManager.IDPARAM)) {
                        type = listener.getManager().getType(new Identifier(obj.get(CognitionResourceManager.IDPARAM).getAsString()));
                        if (type != null) {
                            break;
                        }
                    }
                }
                if (type == null) return;

                EnderismClient.NETWORKED_COGNITION_DATA.getOrCreateKey(type).add(obj);

                count.addAndGet(1);
            });
            EnderismMod.logger.info("Received {} JsonObjects in a packet of size {} kb.", count.get(), ((float)size.get())/1000f);
        });
    }

    @Override
    public PacketByteBuf write(ActualData data) {
        return data.buf;
    }

    @Override
    public ActualData read(PacketByteBuf buf) {
        return new ActualData(buf);
    }

    public record ActualData(PacketByteBuf buf) implements NerveS2CPacket.NervePacketData {
    }

    // Delegates

    private static final long MAX_SIZE = GeneralModCompat.hasXXLPackets ? (long)2e+9 : (long) 2e+6; // 2mb, or 2gb with XXL Packets
    private static final int INT_BYTES = 4;

    public static void delegatedSend(ServerPlayerEntity spe, DataSetBean... dataSetBeans) {
        NetworkData data = netDataFromDSBs(dataSetBeans);

        for (PacketByteBuf buf : writeDelegate(data))
            NerveNetworker.send(spe, InitPackets.JSON, new ActualData(buf));
    }
    private static PacketByteBuf[] writeDelegate(NetworkData data) {
        ArrayList<PacketByteBuf> bufs = new ArrayList<>();

        ArrayList<String> currentJsons = new ArrayList<>();
        long remainingSize = MAX_SIZE;

        for (String s : data.jsons) {
            remainingSize -= INT_BYTES; // size

            remainingSize -= s.getBytes(StandardCharsets.UTF_8).length;

            if (remainingSize >= 0) {
                currentJsons.add(s);
            } else {
                bufs.add(writeOneDelegate(currentJsons));
                currentJsons.clear();
                remainingSize = MAX_SIZE;
            }
        }
        bufs.add(writeOneDelegate(currentJsons));

        return bufs.toArray(new PacketByteBuf[0]);
    }
    private static PacketByteBuf writeOneDelegate(ArrayList<String> jsons) {

        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(jsons.size());

        for (String s : jsons) {
            buf.writeString(s);
        }

        return buf;
    }
    private static NetworkData netDataFromDSBs(DataSetBean... beans) {
        ArrayList<String> jsons = new ArrayList<>();
        for (var bean : beans) {
            for (var obj : bean.objs) {
                jsons.add(obj.toString());
            }
        }

        var data = new NetworkData();
        data.jsons = jsons;
        return data;
    }
    private static NetworkData readDelegate(PacketByteBuf buf) {
        NetworkData data = new NetworkData();
        ArrayList<String> jsons = new ArrayList<>();
        int i = buf.readInt();

        for (int j = 0; j < i; j++) {
            jsons.add(buf.readString());
        }

        data.jsons = jsons;

        return data;
    }

    public record DataSetBean(Collection<JsonObject> objs) {
    }
    public static class NetworkData {
        public ArrayList<String> jsons;
    }
}

// hi! hope you have good day! :)
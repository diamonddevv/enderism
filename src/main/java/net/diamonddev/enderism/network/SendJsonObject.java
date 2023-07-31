package net.diamonddev.enderism.network;

import net.diamonddev.libgenetics.common.api.v1.network.nerve.NerveS2CPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.networking.api.PacketByteBufs;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class SendJsonObject implements NerveS2CPacket<SendJsonObject, SendJsonObject.Data> {

    // todo: allow predetermining so that it wont overflow and will send multiple packets - static delegate NerveNetworker#send hook ig

    private static final long MAX_SIZE = (long) 2e+6; // 2mb
    private static final int INT_FLOAT_SIZE = 4, CHAR_SHORT_SIZE = 2, LONG_DOUBLE_SIZE = 8, BYTE_BOOL_SIZE = 1; // bools are actually 1/8th of a byte, but its easier this way

    @Override
    public ClientPlayNetworking.PlayChannelHandler receive(Identifier channel) {
        return ((client, handler, buf, responseSender) -> {

        });
    }

    @Override
    public PacketByteBuf write(Data data) {
        long remainingSize = MAX_SIZE;

        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(data.sets.size());

        remainingSize -= INT_FLOAT_SIZE; // 1 int written

        for (Data.Set s : data.sets) {
            remainingSize -= s.identifier.getBytes(StandardCharsets.UTF_8).length;
            remainingSize -= s.stringifiedJson.getBytes(StandardCharsets.UTF_8).length;

            if (remainingSize >= 0) {
                buf.writeString(s.identifier);
                buf.writeString(s.stringifiedJson);
            }
        }

        return buf;
    }

    @Override
    public Data read(PacketByteBuf buf) {
        Data data = new Data();
        ArrayList<Data.Set> sets = new ArrayList<>();
        int i = buf.readInt();

        for (int j = 0; j < i; j++) {
            Data.Set set = new Data.Set();
            set.identifier = buf.readString();
            set.stringifiedJson = buf.readString();
        }

        data.sets = sets;

        return data;
    }

    public static class Data implements NerveS2CPacket.NervePacketData {
        public static class Set {
            public String identifier;
            public String stringifiedJson;
        }

        public ArrayList<Set> sets;
    }
}

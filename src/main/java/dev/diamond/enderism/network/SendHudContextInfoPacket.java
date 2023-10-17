package dev.diamond.enderism.network;

import net.diamonddev.libgenetics.common.api.v1.network.nerve.NerveNetworker;
import net.diamonddev.libgenetics.common.api.v1.network.nerve.NerveS2CPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class SendHudContextInfoPacket implements NerveS2CPacket<SendHudContextInfoPacket, SendHudContextInfoPacket.SHCIPacketData> {


    @Override
    public ClientPlayNetworking.PlayChannelHandler receive(Identifier channel) {
        return (client, handler, buf, responseSender) -> {
            SHCIPacketData data = read(buf);
            client.inGameHud.setOverlayMessage(data.isTranslated ? Text.translatable(data.contextData) : Text.literal(data.contextData), false);
        };
    }

    @Override
    public PacketByteBuf write(SHCIPacketData data) {
        PacketByteBuf buf = NerveNetworker.getNewBuf();

        buf.writeString(data.contextData);
        buf.writeBoolean(data.isTranslated);

        return buf;
    }

    @Override
    public SHCIPacketData read(PacketByteBuf buf) {
        SHCIPacketData data = new SHCIPacketData();
        data.contextData = buf.readString();
        data.isTranslated = buf.readBoolean();
        return data;
    }

    public static class SHCIPacketData implements NervePacketData {

        public SHCIPacketData() {
            contextData = "";
            this.isTranslated = false;
        }
        public SHCIPacketData(String data, boolean isTranslated) {
            contextData = data;
            this.isTranslated = isTranslated;
        }

        public String contextData;
        public boolean isTranslated;
    }
}

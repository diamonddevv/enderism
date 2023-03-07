package net.diamonddev.enderism.network;

import net.diamonddev.enderism.EnderismMod;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.diamonddev.libgenetics.common.api.v1.network.nerve.NervePacketRegistry;

public class InitPackets implements RegistryInitializer {

    public static NervePacketRegistry.NervePacketRegistryEntry<SendHudContextInfoPacket, SendHudContextInfoPacket.SHCIPacketData> SHCI;

    @Override
    public void register() {
        SHCI = NervePacketRegistry.register(EnderismMod.id("shci_packet"), new SendHudContextInfoPacket());
    }
}

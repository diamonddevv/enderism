package net.diamonddev.enderism.client;

import net.diamonddev.enderism.network.InitPackets;
import net.diamonddev.libgenetics.common.api.v1.network.nerve.NervePacketRegistry;
import net.fabricmc.api.ClientModInitializer;

public class EnderismClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        NervePacketRegistry.initClientS2CReciever(NervePacketRegistry.getRegistryReference(InitPackets.SHCI));
    }
}

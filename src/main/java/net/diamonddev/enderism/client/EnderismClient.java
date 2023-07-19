package net.diamonddev.enderism.client;

import net.diamonddev.enderism.network.InitPackets;
import net.diamonddev.libgenetics.common.api.v1.network.nerve.NervePacketRegistry;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

public class EnderismClient implements ClientModInitializer {


    @Override
    public void onInitializeClient(ModContainer mod) {
        NervePacketRegistry.initClientS2CReciever(NervePacketRegistry.getRegistryReference(InitPackets.SHCI));
    }
}

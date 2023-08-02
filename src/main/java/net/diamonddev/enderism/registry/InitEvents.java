package net.diamonddev.enderism.registry;

import net.diamonddev.enderism.network.SendJsonObject;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.diamonddev.libgenetics.common.api.v1.util.helper.EnchantHelper;
import net.fabricmc.fabric.api.entity.event.v1.EntityElytraEvents;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.ArrayList;

public class InitEvents implements RegistryInitializer {

    public static ArrayList<SendJsonObject.DataSetBean> dataSetBeans = new ArrayList<>();


    @Override
    public void register() {
        EntityElytraEvents.ALLOW.register(entity -> !EnchantHelper.hasEnchantment(InitEnchants.SHACKLING_CURSE, entity.getEquippedStack(EquipmentSlot.CHEST)));
        OnPlayerConnectCallback.EVENT.register((player, server, connection) -> {
            SendJsonObject.delegatedSend(player, dataSetBeans.toArray(new SendJsonObject.DataSetBean[0]));
        });
    }

    /**
     * Invoked when a player connects to a server.
     */
    @FunctionalInterface
    public interface OnPlayerConnectCallback {
        void onConnect(ServerPlayerEntity player, MinecraftServer server, ClientConnection connection);

        Event<OnPlayerConnectCallback> EVENT = EventFactory.createArrayBacked(OnPlayerConnectCallback.class,
                (listeners) -> ((player, server, connection) -> {
                    for (var listener : listeners) {
                        listener.onConnect(player, server, connection);
                    }
                }));
    }
}

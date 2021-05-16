package dev.the_fireplace.lib.impl.network.client;

import dev.the_fireplace.lib.api.network.client.ClientPacketReceiver;
import dev.the_fireplace.lib.api.network.client.ClientPacketReceiverRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

@Environment(EnvType.CLIENT)
public final class ClientPacketReceiverRegistryImpl implements ClientPacketReceiverRegistry {
    @Deprecated
    public static final ClientPacketReceiverRegistry INSTANCE = new ClientPacketReceiverRegistryImpl();

    private ClientPacketReceiverRegistryImpl() {}

    @Override
    public void register(ClientPacketReceiver receiver) {
        ClientPlayNetworking.registerGlobalReceiver(receiver.getId(), receiver);
    }
}

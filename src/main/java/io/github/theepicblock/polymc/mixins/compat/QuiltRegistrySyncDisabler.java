package io.github.theepicblock.polymc.mixins.compat;

import com.mojang.authlib.minecraft.client.MinecraftClient;
//import io.github.theepicblock.polymc.api.misc.PolyMapProvider;
//import io.github.theepicblock.polymc.impl.Util;
//import net.minecraft.network.ClientConnection;
//import net.minecraft.server.network.ServerPlayerEntity;
//import org.quiltmc.qsl.registry.impl.sync.server.ServerRegistrySync;
import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class QuiltRegistrySyncDisabler {
    /*@SuppressWarnings("MixinAnnotationTarget")
    @Inject(method = "sendSyncPackets(Lnet/minecraft/network/ClientConnection;Lnet/minecraft/server/network/ServerPlayerEntity;I)V", at = @At("HEAD"), cancellable = true)
    private static void sendPacketInject(ClientConnection connection, ServerPlayerEntity player, int syncVersion, CallbackInfo ci) {
       PolyMapProvider.get(connection).refreshUsedPolyMap(); // Refresh it earlier
        if (Util.isPolyMapVanillaLike(connection)) {
            ci.cancel();
        }
    }*/
}

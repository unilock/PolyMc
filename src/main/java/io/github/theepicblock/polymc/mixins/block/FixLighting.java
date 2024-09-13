package io.github.theepicblock.polymc.mixins.block;

import io.github.theepicblock.polymc.impl.Util;
import io.github.theepicblock.polymc.mixins.TACSAccessor;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ChunkHolder;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.AbstractChunkHolder;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(ChunkHolder.class)
public abstract class FixLighting extends AbstractChunkHolder {

    @Shadow @Final private ChunkHolder.PlayersWatchingChunkProvider playersWatchingChunkProvider;

    public FixLighting(ChunkPos pos) {
        super(pos);
    }

    /**
     * Minecraft usually only sends lighting packets when a chunk is on the watch distance edge.
     * This mixin forces lighting packets to be sent regardless, to make sure vanilla clients are kept in sync.
     */
    @Redirect(method = "flushUpdates", at = @At(value="INVOKE", target = "Lnet/minecraft/server/world/ChunkHolder$PlayersWatchingChunkProvider;getPlayersWatchingChunk(Lnet/minecraft/util/math/ChunkPos;Z)Ljava/util/List;"))
    private List<ServerPlayerEntity> onGetPlayersWatchingChunk(ChunkHolder.PlayersWatchingChunkProvider watchProvider, ChunkPos chunkPos, boolean onlyOnWatchDistanceEdge) {

        // Get all the watchers anyway
        List<ServerPlayerEntity> watchers = watchProvider.getPlayersWatchingChunk(this.pos, false);

        if (!onlyOnWatchDistanceEdge) {
            // This will be sent to everyone regardless. Just use the normal method
            return watchers;
        }

        if (!(watchProvider instanceof TACSAccessor accessor)) {
            // Safety in case someone replaces the provider (TODO: should probably warn)
            return watchers;
        }

        return watchers.stream()
                .filter(watcher -> {
                    var polymap = Util.tryGetPolyMap(watcher);
                    if (polymap.isVanillaLikeMap()) {
                        // Always update vanilla clients
                        return true;
                    }

                    var isOnEdge = watcher.getChunkFilter().isWithinDistance(pos) && !watcher.getChunkFilter().isWithinDistanceExcludingEdge(pos.x, pos.z);

                    return isOnEdge;
                })
                .toList();
    }
}

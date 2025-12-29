package net.ramixin.redstonelantern.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.ramixin.redstonelantern.RedstoneLantern;

public class RedstoneLanternClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.putBlock(RedstoneLantern.REDSTONE_LANTERN, ChunkSectionLayer.TRANSLUCENT);
    }
}

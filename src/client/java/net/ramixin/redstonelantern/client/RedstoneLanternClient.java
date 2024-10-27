package net.ramixin.redstonelantern.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import net.ramixin.redstonelantern.RedstoneLantern;

public class RedstoneLanternClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(RedstoneLantern.REDSTONE_LANTERN, RenderLayer.getTranslucent());
    }
}

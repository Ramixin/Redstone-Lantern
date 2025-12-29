package net.ramixin.redstonelantern;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RedstoneTorchBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class RedstoneLantern implements ModInitializer {

    public static final ResourceKey<Block> BLOCK_REGISTRY_KEY = ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("redstonelantern","redstone_lantern"));
    public static final ResourceKey<Item> ITEM_REGISTRY_KEY = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("redstonelantern","redstone_lantern"));


    public static final RedstoneLanternBlock REDSTONE_LANTERN = new RedstoneLanternBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN).lightLevel((state) -> state.getValue(RedstoneTorchBlock.LIT) ? 7 : 0).setId(BLOCK_REGISTRY_KEY));

    @Override
    public void onInitialize() {
        Registry.register(BuiltInRegistries.BLOCK, BLOCK_REGISTRY_KEY, REDSTONE_LANTERN);
        Registry.register(BuiltInRegistries.ITEM, ITEM_REGISTRY_KEY, new BlockItem(REDSTONE_LANTERN, new Item.Properties().useBlockDescriptionPrefix().setId(ITEM_REGISTRY_KEY)));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.REDSTONE_BLOCKS).register(fabricItemGroupEntries -> fabricItemGroupEntries.addAfter(Blocks.REDSTONE_TORCH, REDSTONE_LANTERN));
    }
}

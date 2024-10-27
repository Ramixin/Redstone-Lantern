package net.ramixin.redstonelantern;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.RedstoneTorchBlock;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class RedstoneLantern implements ModInitializer {

    public static final RegistryKey<Block> BLOCK_REGISTRY_KEY = RegistryKey.of(RegistryKeys.BLOCK, Identifier.of("redstonelantern","redstone_lantern"));
    public static final RegistryKey<Item> ITEM_REGISTRY_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of("redstonelantern","redstone_lantern"));


    public static final RedstoneLanternBlock REDSTONE_LANTERN = new RedstoneLanternBlock(AbstractBlock.Settings.copy(Blocks.LANTERN).luminance((state) -> state.get(RedstoneTorchBlock.LIT) ? 7 : 0).registryKey(BLOCK_REGISTRY_KEY));

    @Override
    public void onInitialize() {
        Registry.register(Registries.BLOCK, BLOCK_REGISTRY_KEY, REDSTONE_LANTERN);
        Registry.register(Registries.ITEM, ITEM_REGISTRY_KEY, new BlockItem(REDSTONE_LANTERN, new Item.Settings().useBlockPrefixedTranslationKey().registryKey(ITEM_REGISTRY_KEY)));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(fabricItemGroupEntries -> fabricItemGroupEntries.addAfter(Blocks.REDSTONE_TORCH, REDSTONE_LANTERN));
    }
}

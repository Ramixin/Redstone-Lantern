package net.ramixin.redstonelantern;

import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.RedstoneTorchBlock;

import java.util.List;
import java.util.Map;

public interface RedstoneTorchBlockDuck {

    Map<BlockGetter, List<RedstoneTorchBlock.Toggle>> redstoneLantern$getRecentToggles();

    static RedstoneTorchBlockDuck get(RedstoneTorchBlock block) {
        return (RedstoneTorchBlockDuck) block;
    }

}

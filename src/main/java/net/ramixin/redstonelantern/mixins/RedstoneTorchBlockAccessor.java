package net.ramixin.redstonelantern.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RedstoneTorchBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;
import java.util.Map;

@Mixin(RedstoneTorchBlock.class)
public interface RedstoneTorchBlockAccessor {

    @Accessor("RECENT_TOGGLES")
    static Map<BlockGetter, List<RedstoneTorchBlock.Toggle>> accessRecentTokens() {
        throw new AssertionError();
    }

    @Invoker("isToggledTooFrequently")
    static boolean invokeIsToggledTooFrequently(Level level, BlockPos blockPos, boolean bl) {
        throw new AssertionError();
    }

}

package net.ramixin.redstonelantern;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.RedstoneTorchBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.redstone.Orientation;
import net.ramixin.redstonelantern.mixins.RedstoneTorchBlockAccessor;
import net.ramixin.redstonelantern.mixins.ToggleAccessor;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.List;

import static net.minecraft.world.level.block.RedstoneTorchBlock.LIT;

public class RedstoneLanternBlock extends LanternBlock {

    public RedstoneLanternBlock(BlockBehaviour.Properties settings) {
        super(settings);
    }

    @Override
    protected int getSignal(@NonNull BlockState state, @NonNull BlockGetter blockGetter, @NonNull BlockPos blockPos, @NonNull Direction direction) {
        return direction != Direction.UP && direction != Direction.DOWN && state.getValue(LIT) ? 15 : 0;
    }

    @Override
    protected int getDirectSignal(@NonNull BlockState state, @NonNull BlockGetter blockGetter, @NonNull BlockPos blockPos, @NonNull Direction direction) {
        return 0;
    }

    @Override
    public void setPlacedBy(@NonNull Level level, @NonNull BlockPos pos, @NonNull BlockState state, @Nullable LivingEntity livingEntity, @NonNull ItemStack itemStack) {
        super.setPlacedBy(level, pos, state, livingEntity, itemStack);
        if(!level.isClientSide()) tick(state, (ServerLevel) level, pos, level.random);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NonNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(LIT);
    }

    @Override
    protected void tick(@NonNull BlockState state, @NonNull ServerLevel level, @NonNull BlockPos pos, @NonNull RandomSource randomSource) {
        List<RedstoneTorchBlock.Toggle> list = RedstoneTorchBlockAccessor.accessRecentTokens().get(level);
        while(list != null && !list.isEmpty() && level.getGameTime() - ((ToggleAccessor) list.getFirst()).accessWhen() > 60L) list.removeFirst();
        if(state.getValue(LIT)) {
            if (shouldBeOff(state, level, pos)) level.setBlock(pos, state.setValue(LIT, false), 3);
            if (RedstoneTorchBlockAccessor.invokeIsToggledTooFrequently(level, pos, true)) {
                level.levelEvent(1502, pos, 0);
                level.scheduleTick(pos, level.getBlockState(pos).getBlock(), 160);
            }
        } else
        if(!shouldBeOff(state, level, pos) && !RedstoneTorchBlockAccessor.invokeIsToggledTooFrequently(level, pos, false)) level.setBlock(pos, state.setValue(LIT, true), 3);
    }

    @Override
    protected void neighborChanged(BlockState state, @NonNull Level level, @NonNull BlockPos pos, @NonNull Block sourceBlock, @Nullable Orientation wireOrientation, boolean notify) {
        if(state.getValue(LIT) == this.shouldBeOff(state, level, pos))
            level.scheduleTick(pos, this, 2);
        super.neighborChanged(state, level, pos, sourceBlock, wireOrientation, notify);
    }

    protected boolean shouldBeOff(BlockState state, Level level, BlockPos pos) {
        if(state.getValue(HANGING)) return level.hasSignal(pos.above(), Direction.UP);
        else return level.hasSignal(pos.below(), Direction.DOWN);

    }
}

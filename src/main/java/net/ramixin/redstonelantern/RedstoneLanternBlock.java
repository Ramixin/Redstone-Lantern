package net.ramixin.redstonelantern;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LanternBlock;
import net.minecraft.block.RedstoneTorchBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.block.WireOrientation;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.minecraft.block.RedstoneTorchBlock.LIT;

public class RedstoneLanternBlock extends LanternBlock {

    public RedstoneLanternBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return direction != Direction.UP && direction != Direction.DOWN && state.get(LIT) ? 15 : 0;
    }

    @Override
    protected int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return 0;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        if(!world.isClient()) scheduledTick(state, (ServerWorld) world, pos, world.random);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(LIT);
    }

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        List<RedstoneTorchBlock.BurnoutEntry> list = RedstoneTorchBlock.BURNOUT_MAP.get(world);
        while(list != null && !list.isEmpty() && world.getTime() - (list.getFirst()).time > 60L) list.removeFirst();

        if(state.get(LIT)) {
            if (shouldBeOff(state, world, pos)) world.setBlockState(pos, state.with(LIT, false), 3);
            if (RedstoneTorchBlock.isBurnedOut(world, pos, true)) {
                world.syncWorldEvent(1502, pos, 0);
                world.scheduleBlockTick(pos, world.getBlockState(pos).getBlock(), 160);
            }
        } else
            if(!shouldBeOff(state, world, pos) && !RedstoneTorchBlock.isBurnedOut(world, pos, false)) world.setBlockState(pos, state.with(LIT, true), 3);
    }

    @Override
    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, @Nullable WireOrientation wireOrientation, boolean notify) {
        if(state.get(LIT) == this.shouldBeOff(state, world, pos))
            world.scheduleBlockTick(pos, this, 2);
        super.neighborUpdate(state, world, pos, sourceBlock, wireOrientation, notify);
    }

    protected boolean shouldBeOff(BlockState state,World world, BlockPos pos) {
        if(state.get(HANGING)) return world.isEmittingRedstonePower(pos.up(), Direction.UP);
        else return world.isEmittingRedstonePower(pos.down(), Direction.DOWN);

    }
}

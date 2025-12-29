package net.ramixin.redstonelantern.mixins;

import net.minecraft.world.level.block.RedstoneTorchBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(RedstoneTorchBlock.Toggle.class)
public interface ToggleAccessor {

    @Accessor("when")
    long accessWhen();

}

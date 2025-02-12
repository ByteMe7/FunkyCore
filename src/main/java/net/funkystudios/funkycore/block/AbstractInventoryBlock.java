package net.funkystudios.funkycore.block;

import net.funkystudios.funkycore.block.entity.AbstractInventoryBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public abstract class AbstractInventoryBlock extends Block implements BlockEntityProvider {
    private final BlockEntityType<?> blockEntityType;
    public AbstractInventoryBlock(Supplier<BlockEntityType<?>> entityTypeSupplier, Settings settings) {
        super(settings);
        this.blockEntityType = entityTypeSupplier.get();
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if(!world.isClient){
            if(world.getBlockEntity(pos) instanceof AbstractInventoryBlockEntity inventoryBlockEntity){
                player.openHandledScreen(inventoryBlockEntity);
            }
        }
        return ActionResult.success(world.isClient);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return blockEntityType.instantiate(pos, state);
    }
}

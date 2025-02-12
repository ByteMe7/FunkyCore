package net.funkystudios.funkycore.event;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.funkystudios.funkycore.init.FCEnchantmentInit;
import net.funkystudios.funkycore.item.HammerItem;
import net.funkystudios.funkycore.utils.FCItemUtils;
import net.funkystudios.funkycore.utils.FCTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public class ExpandedMiningEvent implements PlayerBlockBreakEvents.Before{
    private static final Set<BlockPos> HARVESTED_BLOCKS = new HashSet<>();
    @Override
    public boolean beforeBlockBreak(World world, PlayerEntity player, BlockPos pos,
                                    BlockState state, @Nullable BlockEntity blockEntity) {
        ItemStack mainHandItem = player.getMainHandStack();


        if(isExpandable(mainHandItem)
                && player instanceof ServerPlayerEntity serverPlayer
                && mainHandItem.getItem() instanceof MiningToolItem miningToolItem){
            if(HARVESTED_BLOCKS.contains(pos)){
                return true;
            }

            for (BlockPos position : HammerItem.getBlocksToBeDestroyed(getExpandedLvl(mainHandItem), pos, serverPlayer)){
                if(pos == position
                        || miningToolItem.isCorrectForDrops(mainHandItem, world.getBlockState(position))){
                    continue;
                }

                HARVESTED_BLOCKS.add(position);
                serverPlayer.interactionManager.tryBreakBlock(position);
                HARVESTED_BLOCKS.remove(position);
            }
        }
        return true;
    }


    public static boolean isExpandable(ItemStack stack){
        return stack.isIn(FCTags.Items.EXPANDABLE_MINING_TOOL);
    }

    public static int getExpandedLvl(ItemStack stack){
        int baseLvl = stack.isIn(FCTags.Items.HAMMER) ? 1 : 0;
        if(!stack.hasEnchantments()){
            return baseLvl;
        }
        return FCItemUtils.getLevelOfEnchantment(stack, FCEnchantmentInit.EXPANDED_KEY) + baseLvl;
    }
}

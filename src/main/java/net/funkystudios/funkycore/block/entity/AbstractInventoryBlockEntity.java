package net.funkystudios.funkycore.block.entity;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.funkystudios.funkycore.FunkyCore;
import net.funkystudios.funkycore.network.BlockPosPayload;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractInventoryBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<BlockPosPayload> {
    public final Text TITLE;
    private final int SIZE;
    private final SimpleInventory inventory;
    private final InventoryStorage inventoryStorage;
    private int numPlayersOpen;

    public AbstractInventoryBlockEntity(BlockEntityType<?> type,
                                        BlockPos pos, BlockState state,
                                        String key, int rows, int columns) {
        super(type, pos, state);
        this.TITLE = createTitle(key);
        this.SIZE = rows * columns;
        this.inventory = createInventory(this.SIZE);
        this.inventoryStorage = InventoryStorage.of(inventory, null);
    }

    private  SimpleInventory createInventory(int size){
        return new SimpleInventory(size){
            @Override
            public void markDirty() {
                super.markDirty();
                update();
            }

            @Override
            public void onOpen(PlayerEntity player) {
                super.onOpen(player);
                AbstractInventoryBlockEntity.this.numPlayersOpen++;
                update();
            }

            @Override
            public void onClose(PlayerEntity player) {
                super.onClose(player);
                AbstractInventoryBlockEntity.this.numPlayersOpen--;
                update();
            }
        };
    }
    private static Text createTitle(String key){
        return Text.translatable("container." + FunkyCore.MOD_ID + "." + key + "-inventory");
    }

    @Override
    public Text getDisplayName() {
        return TITLE;
    }

    private void update() {
        markDirty();
        if(world != null){
            world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
        }
    }

    @Override
    public BlockPosPayload getScreenOpeningData(ServerPlayerEntity serverPlayerEntity) {
        return new BlockPosPayload(this.pos);
    }
}

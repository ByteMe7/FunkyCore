package net.funkystudios.funkycore.screen.handler;

import net.funkystudios.funkycore.block.entity.FoundryBlockEntity;
import net.funkystudios.funkycore.init.FCBlockInit;
import net.funkystudios.funkycore.init.FCScreenHandlerTypeInit;
import net.funkystudios.funkycore.network.BlockPosPayload;
import net.funkystudios.funkycore.utils.ImplementedInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;

public class FoundryScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    private final FoundryBlockEntity blockEntity;
    private final ScreenHandlerContext context;

    public FoundryScreenHandler(int syncId, PlayerInventory playerInventory, BlockPosPayload payload){
        this(syncId, playerInventory,
                (FoundryBlockEntity) playerInventory.player.getWorld().getBlockEntity(payload.pos()),
                new ArrayPropertyDelegate(2));
    }

    // Main Constructor
    public FoundryScreenHandler(int syncId, PlayerInventory playerInventory, FoundryBlockEntity blockEntity,
                                PropertyDelegate arrayPropertyDelegate) {
        super(FCScreenHandlerTypeInit.FOUNDRY_SCREEN_HANDLER, syncId);

        this.blockEntity = blockEntity;
        this.context = ScreenHandlerContext.create(this.blockEntity.getWorld(), this.blockEntity.getPos());

        this.inventory = (Inventory) this.blockEntity;
        this.propertyDelegate = arrayPropertyDelegate;

        checkSize(this.inventory, 3);
        inventory.onOpen(playerInventory.player);

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
        addBlockInventory(inventory);

        addProperties(arrayPropertyDelegate);
    }

    public boolean isCrafting() {
        return propertyDelegate.get(0) > 0;
    }

    public int getScaledProgress() {
        int progress = this.propertyDelegate.get(0);
        int maxProgress = this.propertyDelegate.get(1);
        int progressArrowSize = 24; // Arrow Size in Pixels

        return maxProgress != 0 && progress != 0 ? MathHelper.clamp((progress * progressArrowSize) / maxProgress, 0, progressArrowSize) : 0;
    }

    private void addPlayerInventory(PlayerInventory playerInventory){
        for(int row = 0; row < 3; row++){
            for (int column = 0; column < 9; column++) {
                addSlot(new Slot(
                        playerInventory,
                        9 + (column + (row * 9)),
                        15 + (column * 18) + 32,
                        152 + (row * 18)
                ));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory){
        for (int column = 0; column < 9; column++){
            addSlot(new Slot(
                    playerInventory,
                    column,
                    15 + (column * 18) + 32,
                    152 + 54 + 3
            ));
        }
    }

    private void addBlockInventory(Inventory inventory){
        addSlot(new Slot(inventory, 0, 36 + 32,77));
        addSlot(new Slot(inventory, 1, 36 + 32, 95));
        addSlot(new Slot(inventory, 2, 96 + 32, 86));
    }



    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return null;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
       return canUse(this.context, player, FCBlockInit.FOUNDRY);
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        this.blockEntity.onClose(player);
    }
}

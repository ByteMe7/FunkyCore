package net.funkystudios.funkycore.block.entity;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.funkystudios.funkycore.init.FCBlockEntityTypeInit;
import net.funkystudios.funkycore.init.FCRecipeTypeInit;
import net.funkystudios.funkycore.network.BlockPosPayload;
import net.funkystudios.funkycore.recipe.custom.FoundryRecipe;
import net.funkystudios.funkycore.recipe.input.FoundryRecipeInput;
import net.funkystudios.funkycore.screen.handler.FoundryScreenHandler;
import net.funkystudios.funkycore.utils.TickableBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FoundryBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<BlockPosPayload>, SidedInventory, TickableBlockEntity {
    private static final Map<Block, Float> heatValues = new HashMap<>();
    protected DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);



    private static final int INPUT_SLOT = 0;
    private static final int ADDITION_SLOT = 1;
    private static final int OUTPUT_SLOT = 2;

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int cookTime;
    private float speed;
    private int ticks = 0;



    public FoundryBlockEntity(BlockPos pos, BlockState state) {
        super(FCBlockEntityTypeInit.FOUNDRY_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index){
                    case 0 -> FoundryBlockEntity.this.progress;
                    case 1 -> FoundryBlockEntity.this.cookTime;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index){
                    case 0 -> FoundryBlockEntity.this.progress = value;
                    case 1 -> FoundryBlockEntity.this.cookTime = value;
                }
            }

            @Override
            public int size() {
                return 2;
            }
        };
        addDefaultHeatSources();
    }


    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    private void update() {
        markDirty();
        if(world != null){
            world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
        }
    }




    @Override
    public void tick() {
        if(this.world == null || this.world.isClient){
            return;
        }

        BlockState state = this.world.getBlockState(pos);
        BlockState beneath = this.world.getBlockState(pos.down());

        if (isOutputSlotEmptyOrReceivable() && isHeated(beneath)) {
            if(this.hasRecipe()){
                this.increaseCraftingProgress();
                this.updateSpeed(beneath);
                markDirty();

                if(hasFinishedCrafting()){
                    this.craftItem();
                    this.resetProgress();
                }
                update();
            } else {
                this.resetProgress();
            }
        } else {
            this.resetProgress();
            markDirty();
        }
    }


    private boolean isHeated(BlockState beneath){
        return heatValues.containsKey(beneath.getBlock());
    }

    private void updateSpeed(BlockState beneath){
        this.speed = heatValues.get(beneath.getBlock());
    }


    private void craftItem(){
        Optional<RecipeEntry<FoundryRecipe>> recipe = getCurrentRecipe();
        if(recipe.isEmpty()) return;

        this.removeStack(INPUT_SLOT, 1);
        this.removeStack(ADDITION_SLOT, 1);
        this.setStack(OUTPUT_SLOT, new ItemStack(recipe.get().value().getOutput().getItem(),
                getStack(OUTPUT_SLOT).getCount() + recipe.get().value().getOutput().getMaxCount()));

    }

    private boolean hasRecipe(){
        Optional<RecipeEntry<FoundryRecipe>> recipe = getCurrentRecipe();
        if(recipe.isEmpty()){
            return false;
        }
        this.cookTime = recipe.get().value().getTime();

        return canInsertItemIntoOutputSlot(recipe.get().value().getOutput().getItem())
                && canInsertAmountIntoOutputSlot(recipe.get().value().getOutput());
    }

    private Optional<RecipeEntry<FoundryRecipe>> getCurrentRecipe() {
        if(getWorld() == null) return Optional.empty();
        FoundryRecipeInput inv = new FoundryRecipeInput(this.getStack(INPUT_SLOT), this.getStack(ADDITION_SLOT));
        return getWorld().getRecipeManager().getFirstMatch(FCRecipeTypeInit.FOUNDRY, inv, getWorld());
    }


    private boolean canInsertItemIntoOutputSlot(Item item){
        return this.getStack(OUTPUT_SLOT).getItem() == item || this.getStack(OUTPUT_SLOT).isEmpty();
    }

    private boolean canInsertAmountIntoOutputSlot(ItemStack result){
        return this.getStack(OUTPUT_SLOT).getCount() + result.getCount() <= getStack(OUTPUT_SLOT).getMaxCount();
    }

    private boolean isOutputSlotEmptyOrReceivable(){
        return this.getStack(OUTPUT_SLOT).isEmpty() || this.getStack(OUTPUT_SLOT).getCount() < this.getStack(OUTPUT_SLOT).getMaxCount();
    }

    private void increaseCraftingProgress(){
        this.progress += (int) (1 * (1 + this.speed));
    }

    private void resetProgress(){
        this.progress = 0;
    }

    private boolean hasFinishedCrafting(){
        return this.progress >= this.cookTime;
    }

    private static void addDefaultHeatSources(){
        addHeatSource(Blocks.TORCH, 1);
        addHeatSource(Blocks.FIRE, 2);
        addHeatSource(Blocks.SOUL_TORCH, 2);
        addHeatSource(Blocks.CAMPFIRE, 3);
        addHeatSource(Blocks.SOUL_FIRE, 5);
        addHeatSource(Blocks.SOUL_CAMPFIRE, 8);
        addHeatSource(Blocks.LAVA, 8);
    }

    public static void addHeatSource(Block source, float multiplier){
        heatValues.put(source, multiplier);
    }



    @Override
    public BlockPosPayload getScreenOpeningData(ServerPlayerEntity serverPlayerEntity) {
        return new BlockPosPayload(this.pos);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("title.blockEntity.foundry-blockEntity");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new FoundryScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, inventory, registryLookup);
        nbt.putInt("foundry.progress", this.progress);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt, inventory, registryLookup);
        progress = nbt.getInt("foundry.progress");
    }

    public DefaultedList<ItemStack> getHeldStacks(){
        return this.inventory;
    }


    @Override
    public int[] getAvailableSlots(Direction side) {
        return switch (side){
            case UP -> new int[]{0};
            case EAST, WEST, NORTH, SOUTH -> new int[]{1};
            default -> new int[0];
        };
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return this.isValid(slot, stack);
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return false;
    }

    @Override
    public int size() {
        return this.inventory.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : this.getHeldStacks()){
            if(!stack.isEmpty()){
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getStack(int slot) {
        return this.getHeldStacks().get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack itemStack = Inventories.splitStack(this.getHeldStacks(), slot, amount);
        if(!itemStack.isEmpty()){
            this.markDirty();
        }
        return itemStack;
    }

    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(this.getHeldStacks(), slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        ItemStack itemStack = this.inventory.get(slot);
        boolean bl = !stack.isEmpty() && ItemStack.areItemsAndComponentsEqual(itemStack, stack);
        this.inventory.set(slot, stack);
        if(slot != 2 && !bl){
            this.markDirty();
        }
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return Inventory.canPlayerUse(this, player);
    }

    @Override
    public void clear() {
        this.getHeldStacks().clear();
    }

    @Override
    public boolean isValid(int slot, ItemStack stack) {
        if(slot == 2) {
            return false;
        } else {
            ItemStack itemStack = this.inventory.get(slot);
            return stack.getItem() == itemStack.getItem() && (stack.getCount() + itemStack.getCount()) < itemStack.getMaxCount();
        }
    }


}

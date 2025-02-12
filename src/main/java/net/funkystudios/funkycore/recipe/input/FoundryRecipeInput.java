package net.funkystudios.funkycore.recipe.input;

import net.funkystudios.funkycore.utils.ImplementedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.util.collection.DefaultedList;

public record FoundryRecipeInput(ItemStack input, ItemStack carbon) implements RecipeInput {

    @Override
    public ItemStack getStackInSlot(int slot) {
        return switch (slot){
            case 0 -> this.input;
            case 1 -> this.carbon;
            default ->  throw new IllegalArgumentException("Recipe does not contain slot " + slot);
        };
    }

    @Override
    public int getSize() {
        return 2;
    }
}

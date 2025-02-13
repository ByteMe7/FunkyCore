package net.funkystudios.funkycore.compat.rei.dislpay;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.funkystudios.funkycore.compat.rei.category.FoundryCategory;
import net.funkystudios.funkycore.recipe.custom.FoundryRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FoundryDisplay extends BasicDisplay {
    public FoundryDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
    }

    public FoundryDisplay(FoundryRecipe recipe){
        super(getInputList(recipe), List.of(EntryIngredient.of(EntryStacks.of(recipe.getOutput()))));
    }



    private static List<EntryIngredient> getInputList(FoundryRecipe recipe){
        if (recipe == null) return Collections.emptyList();
        List<EntryIngredient> list = new ArrayList<>();
        for (Ingredient ingredient : recipe.getIngredients()){
            list.add(EntryIngredients.ofIngredient(ingredient));
        }
        return list;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return FoundryCategory.FOUNDRY;
    }
}

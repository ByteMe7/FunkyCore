package net.funkystudios.funkycore.init;

import net.funkystudios.funkycore.recipe.custom.FoundryRecipe;
import net.minecraft.recipe.RecipeSerializer;

public class FCRecipeSerializerInit {

    public static final RecipeSerializer<FoundryRecipe> FOUNDRY_RECIPE = RecipeSerializer.register("foundry", new FoundryRecipe.Serializer(100));

    public static void load(){}
}

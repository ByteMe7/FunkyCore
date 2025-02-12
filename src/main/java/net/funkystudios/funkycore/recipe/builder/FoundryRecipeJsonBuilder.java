package net.funkystudios.funkycore.recipe.builder;

import net.funkystudios.funkycore.FunkyCore;
import net.funkystudios.funkycore.recipe.custom.FoundryRecipe;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public class FoundryRecipeJsonBuilder {
    private Ingredient input;
    private Ingredient addition;
    private final Item result;
    private final int count;
    private int time = -1;
    private final Map<String, AdvancementCriterion<?>> criteria = new LinkedHashMap<>();

    public FoundryRecipeJsonBuilder(Item result, int count){
        this.result = result;
        this.count = count;
    }

    public static FoundryRecipeJsonBuilder create(ItemConvertible result){
        return create(result, 1);
    }

    public static FoundryRecipeJsonBuilder create(ItemConvertible result, int count){
        return new FoundryRecipeJsonBuilder(result.asItem(), count);
    }
    public FoundryRecipeJsonBuilder input(ItemConvertible input){
        this.input = Ingredient.ofItems(input);
        return this;
    }

    public FoundryRecipeJsonBuilder input(TagKey<Item> input){
        this.input = Ingredient.fromTag(input);
        return this;
    }

    public FoundryRecipeJsonBuilder addition(ItemConvertible addition){
        this.addition = Ingredient.ofItems(addition);
        return this;
    }

    public FoundryRecipeJsonBuilder addition(TagKey<Item> addition){
        this.addition = Ingredient.fromTag(addition);
        return this;
    }

    public FoundryRecipeJsonBuilder time(int time){
        this.time = time;
        return this;
    }

    public FoundryRecipeJsonBuilder criterion(String string, AdvancementCriterion<?> advancementCriterion){
        this.criteria.put(string, advancementCriterion);
        return this;
    }
    public void offerTo(RecipeExporter exporter, String id){
        Identifier recipeId = FunkyCore.id(id);
        FoundryRecipe foundryRecipe = this.validate(recipeId);
        Advancement.Builder builder  = exporter.getAdvancementBuilder()
                        .criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeId))
                                .rewards(AdvancementRewards.NONE);
        this.criteria.forEach(builder::criterion);
        exporter.accept(recipeId, foundryRecipe, builder.build(recipeId.withPrefixedPath("recipes/foundry/")));
    }

    private FoundryRecipe validate(Identifier recipeId) {
        if(this.input.isEmpty()){
            throw new IllegalStateException("No input for recipe" + recipeId);
        }
        if(this.criteria.isEmpty()){
            throw new IllegalStateException("No way of obtaining recipe" + recipeId);
        }
        if(this.addition.isEmpty()) {
            throw new IllegalStateException("Invalid recipe " + recipeId + " has no addition");
        }
        if(this.time <= 0){
            throw new IllegalStateException("Invalid time for " + recipeId + "time needs to be greater than 0 got " + this.time);
        }
        return new FoundryRecipe(this.input, this.addition, new ItemStack(result, count), this.time);
    }
}

package net.funkystudios.funkycore.item;

import net.funkystudios.funkycore.utils.FCTags;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.TagKey;


import java.util.function.Supplier;

public enum FCToolMaterials implements ToolMaterial {
    TURQUOISE(184, 9.5F, 1.75F,
            FCTags.Blocks.INCORRECT_FOR_TURQUOISE_TOOL, 35, FCTags.Items.TURQUOISE_REPAIR),
    STEEL(974, 5.0F, 2.5F,
            FCTags.Blocks.INCORRECT_FOR_STEEL_TOOL, 17, FCTags.Items.STEEL_REPAIR);


    private final int durability;
    private final float miningSpeed;
    private final float attackDamage;
    private final TagKey<Block> inverseTag;
    private final int enchantability;
    private final Supplier<Ingredient> repairIngredient;
    FCToolMaterials(int durability, float miningSpeed, float attackDamage,
                    TagKey<Block> inverseTag, int enchantability, TagKey<Item> repairTag){
        this.durability = durability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.inverseTag = inverseTag;
        this.enchantability = enchantability;
        this.repairIngredient = () -> Ingredient.fromTag(repairTag);
    }



    @Override
    public int getDurability() {
        return this.durability;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return this.miningSpeed;
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public TagKey<Block> getInverseTag() {
        return this.inverseTag;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}

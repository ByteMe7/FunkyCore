package net.funkystudios.funkycore.utils;

import net.funkystudios.funkycore.FunkyCore;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class FCTags {
    public static class Blocks {


        // Tool Tags
        public static final TagKey<Block> INCORRECT_FOR_TURQUOISE_TOOL = createTag("incorrect_for_turquoise_tool");
        public static final TagKey<Block> NEEDS_TURQUOISE_TOOL = createTag("needs_turquoise_tool");
        public static final TagKey<Block> INCORRECT_FOR_STEEL_TOOL = createTag("incorrect_for_steel_tool");
        public static final TagKey<Block> NEEDS_STEEL_TOOL = createTag("needs_steel_tool");



        private static TagKey<Block> createTag(String name){
            return TagKey.of(RegistryKeys.BLOCK, FunkyCore.id(name));
        }
    }

    public static class Items {

        // Tool Tags
        public static final TagKey<Item> TURQUOISE_REPAIR = createTag("turquoise_repair");
        public static final TagKey<Item> STEEL_REPAIR = createTag("steel_repair");
        public static final TagKey<Item> HAMMER = createTag("hammer");
        public static final TagKey<Item> EXPANDABLE_MINING_TOOL = createTag("expanded_mining_tool");
        public static final TagKey<Item> CARBON_INPUT = createTag("carbon_input");

        private static TagKey<Item> createTag(String name){
            return TagKey.of(RegistryKeys.ITEM, FunkyCore.id(name));
        }
    }
}

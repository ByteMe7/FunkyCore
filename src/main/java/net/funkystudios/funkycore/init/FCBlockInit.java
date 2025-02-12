package net.funkystudios.funkycore.init;

import net.funkystudios.funkycore.FunkyCore;
import net.funkystudios.funkycore.block.FoundryBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class FCBlockInit {
    public static final List<Block> NOT_SIMPLE = new ArrayList<>();
    public static final Block RAW_TURQUOISE_BLOCK = registerWithItem("raw_turquoise_block", new Block(
            AbstractBlock.Settings.create().requiresTool().strength(5F, 5F)
    ));

    public static final Block TURQUOISE_BLOCK = registerWithItem("turquoise_block", new Block(
            AbstractBlock.Settings.create().requiresTool().strength(5F, 5F)
    ));

    public static final Block STEEL_BLOCK = registerWithItem("steel_block", new Block(
            AbstractBlock.Settings.create().requiresTool().strength(5F, 5F)
    ));

    public static final FoundryBlock FOUNDRY = registerNonSimpleWithItem("foundry", new FoundryBlock(
            AbstractBlock.Settings.create().requiresTool().strength(5F, 5F)
    ));

    public static <T extends Block> T register(String name, T block){
        return Registry.register(Registries.BLOCK, FunkyCore.id(name), block);
    }
    public static <T extends Block> T registerNonSimple(String name, T block){
        T registered = register(name, block);
        NOT_SIMPLE.add(registered);
        return registered;
    }

    public static <T extends Block> T registerNonSimpleWithItem(String name, T block){
        T registered = registerWithItem(name, block);
        NOT_SIMPLE.add(registered);
        return registered;
    }
    public static <T extends Block> T registerNonSimpleWithItem(String name, T block, Item.Settings settings){
        T registered = registerWithItem(name, block, settings);
        NOT_SIMPLE.add(registered);
        return registered;
    }

    public static <T extends Block> T registerWithItem(String name, T block){
        T registered = register(name, block);
        FCItemInit.register(name, new BlockItem(registered, new Item.Settings()));
        return registered;
    }
    public static <T extends Block> T registerWithItem(String name, T block, Item.Settings settings){
        T registered = register(name, block);
        FCItemInit.register(name, new BlockItem(registered, settings));
        return registered;
    }

    public static void load(){}
}

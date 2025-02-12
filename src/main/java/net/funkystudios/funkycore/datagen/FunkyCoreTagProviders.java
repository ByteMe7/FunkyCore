package net.funkystudios.funkycore.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.funkystudios.funkycore.FunkyCore;
import net.funkystudios.funkycore.init.FCBlockInit;
import net.funkystudios.funkycore.init.FCItemInit;
import net.funkystudios.funkycore.item.HammerItem;
import net.funkystudios.funkycore.utils.FCTags;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class FunkyCoreTagProviders {
    public FunkyCoreTagProviders(FabricDataGenerator.Pack pack){
        pack.addProvider(BlockTagProvider::new);
        pack.addProvider(ItemTagProvider::new);
    }

    private static class ItemTagProvider extends FabricTagProvider.ItemTagProvider {

        public ItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
            final var swords = getOrCreateTagBuilder(ItemTags.SWORDS);
            final var pickaxes = getOrCreateTagBuilder(ItemTags.PICKAXES);
            final var axes = getOrCreateTagBuilder(ItemTags.AXES);
            final var shovels = getOrCreateTagBuilder(ItemTags.SHOVELS);
            final var hoes = getOrCreateTagBuilder(ItemTags.HOES);
            final var hammers = getOrCreateTagBuilder(FCTags.Items.HAMMER);

            getOrCreateTagBuilder(FCTags.Items.TURQUOISE_REPAIR)
                    .add(FCItemInit.TURQUOISE);

            getOrCreateTagBuilder(FCTags.Items.STEEL_REPAIR)
                    .add(FCItemInit.STEEL_INGOT);

            Registries.ITEM.getIds()
                    .stream()
                    .filter(key -> key.getNamespace().equals(FunkyCore.MOD_ID))
                    .map(Registries.ITEM::getOrEmpty)
                    .map(Optional::orElseThrow)
                    .filter(item -> item instanceof SwordItem)
                    .forEach(swords::add);

            Registries.ITEM.getIds()
                    .stream()
                    .filter(key -> key.getNamespace().equals(FunkyCore.MOD_ID))
                    .map(Registries.ITEM::getOrEmpty)
                    .map(Optional::orElseThrow)
                    .filter(item -> item instanceof PickaxeItem)
                    .forEach(pickaxes::add);

            Registries.ITEM.getIds()
                    .stream()
                    .filter(key -> key.getNamespace().equals(FunkyCore.MOD_ID))
                    .map(Registries.ITEM::getOrEmpty)
                    .map(Optional::orElseThrow)
                    .filter(item -> item instanceof AxeItem)
                    .forEach(axes::add);

            Registries.ITEM.getIds()
                    .stream()
                    .filter(key -> key.getNamespace().equals(FunkyCore.MOD_ID))
                    .map(Registries.ITEM::getOrEmpty)
                    .map(Optional::orElseThrow)
                    .filter(item ->  item instanceof ShovelItem)
                    .forEach(shovels::add);

            Registries.ITEM.getIds()
                    .stream()
                    .filter(key -> key.getNamespace().equals(FunkyCore.MOD_ID))
                    .map(Registries.ITEM::getOrEmpty)
                    .map(Optional::orElseThrow)
                    .filter(item -> item instanceof HoeItem)
                    .forEach(hoes::add);

            Registries.ITEM.getIds()
                    .stream()
                    .map(Registries.ITEM::getOrEmpty)
                    .map(Optional::orElseThrow)
                    .filter(item -> item instanceof HammerItem)
                    .forEach(hammers::add);

            getOrCreateTagBuilder(FCTags.Items.EXPANDABLE_MINING_TOOL)
                    .addTag(FCTags.Items.HAMMER)
                    .addTag(ItemTags.PICKAXES)
                    .addTag(ItemTags.SHOVELS);

        }

    }

    private static class BlockTagProvider extends  FabricTagProvider.BlockTagProvider {

        public BlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
            getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL)
                    .add(FCBlockInit.FOUNDRY);
            getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL);
            getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
                    .add(FCBlockInit.STEEL_BLOCK, FCBlockInit.TURQUOISE_BLOCK, FCBlockInit.RAW_TURQUOISE_BLOCK);
            getOrCreateTagBuilder(BlockTags.INCORRECT_FOR_IRON_TOOL);
            getOrCreateTagBuilder(BlockTags.INCORRECT_FOR_DIAMOND_TOOL);

            getOrCreateTagBuilder(FCTags.Blocks.INCORRECT_FOR_TURQUOISE_TOOL)
                    .addTag(BlockTags.INCORRECT_FOR_IRON_TOOL);

            getOrCreateTagBuilder(FCTags.Blocks.NEEDS_TURQUOISE_TOOL)
                    .addTag(BlockTags.NEEDS_IRON_TOOL);

            getOrCreateTagBuilder(FCTags.Blocks.INCORRECT_FOR_STEEL_TOOL)
                    .addTag(BlockTags.INCORRECT_FOR_DIAMOND_TOOL);

            getOrCreateTagBuilder(FCTags.Blocks.NEEDS_STEEL_TOOL)
                    .addTag(BlockTags.NEEDS_DIAMOND_TOOL);

        }
    }
}

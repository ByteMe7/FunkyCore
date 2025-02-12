package net.funkystudios.funkycore.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.funkystudios.funkycore.init.FCBlockInit;
import net.funkystudios.funkycore.init.FCItemInit;
import net.funkystudios.funkycore.recipe.builder.FoundryRecipeJsonBuilder;
import net.funkystudios.funkycore.utils.FCTags;
import net.funkystudios.funkycore.utils.ToolSet;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.data.server.recipe.StonecuttingRecipeJsonBuilder;
import net.minecraft.item.*;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class FunkyCoreRecipeProviders {
    public FunkyCoreRecipeProviders(FabricDataGenerator.Pack pack){
        pack.addProvider(CraftingRecipe::new);
        pack.addProvider(StoneCuttingRecipe::new);
        pack.addProvider(FoundryRecipe::new);
    }

    public static @NotNull String hasTag(@NotNull TagKey<Item> tag){
        return "has_" + tag.id().toString();
    }
    public static @NotNull String getStonecuttingRecipeName(@NotNull ItemConvertible item){
        return getItemPath(item) + "_from_stonecutting";
    }

    public static @NotNull String getFoundryRecipeName(@NotNull ItemConvertible item){
        return getItemPath(item) + "_from_alloying";
    }

    public static @NotNull String getReversed(@NotNull String normal){
        return normal + "_reversed";
    }

    public static @NotNull String getCompactingFromRecipeName(@NotNull ItemConvertible packed, @NotNull ItemConvertible unpacked){
        return getItemPath(packed) + "_from_compacting_" + getItemPath(unpacked);
    }

    public static @NotNull String getUnpackingFromRecipeName(@NotNull ItemConvertible unpacked, @NotNull ItemConvertible packed){
        return getItemPath(unpacked) + "_from_unpacking_" + getItemPath(packed);
    }

    public static @NotNull String getCraftingRecipeName(@NotNull ItemConvertible item){
        return getItemPath(item) + "_from_crafting";
    }

    private static @NotNull String getItemPath(@NotNull ItemConvertible item){
        return Registries.ITEM.getId(item.asItem()).getPath();
    }

    private static class CraftingRecipe extends FabricRecipeProvider {

        public CraftingRecipe(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);

        }

        @Override
        public void generate(RecipeExporter exporter) {
            offer2TierReversibleCompacting(exporter, RecipeCategory.BUILDING_BLOCKS, FCBlockInit.RAW_TURQUOISE_BLOCK, RecipeCategory.MISC, FCItemInit.RAW_TURQUOISE);
            offer2TierReversibleCompacting(exporter, RecipeCategory.BUILDING_BLOCKS, FCBlockInit.TURQUOISE_BLOCK, RecipeCategory.MISC, FCItemInit.TURQUOISE);
            offer3TierReversibleCompacting(exporter, FCBlockInit.STEEL_BLOCK, FCItemInit.STEEL_INGOT, FCItemInit.STEEL_NUGGET);

            var turquoiseSet = new ToolSet.Builder()
                    .sword(FCItemInit.TURQUOISE_SWORD)
                    .pickaxe(FCItemInit.TURQUOISE_PICKAXE)
                    .axe(FCItemInit.TURQUOISE_AXE)
                    .shovel(FCItemInit.TURQUOISE_SHOVEL)
                    .hoe(FCItemInit.TURQUOISE_HOE)
                    .hammer(FCItemInit.TURQUOISE_HAMMER)
                    .helmet(FCItemInit.TURQUOISE_HELMET)
                    .chestplate(FCItemInit.TURQUOISE_CHESTPLATE)
                    .leggings(FCItemInit.TURQUOISE_LEGGINGS)
                    .boots(FCItemInit.TURQUOISE_BOOTS)
                    .build();

            generateToolSet(exporter, turquoiseSet, FCBlockInit.TURQUOISE_BLOCK, FCItemInit.TURQUOISE);

            var steelSet = new ToolSet.Builder()
                    .helmet(FCItemInit.STEEL_HELMET)
                    .chestplate(FCItemInit.STEEL_CHESTPLATE)
                    .leggings(FCItemInit.STEEL_LEGGINGS)
                    .boots(FCItemInit.STEEL_BOOTS)
                    .sword(FCItemInit.STEEL_SWORD)
                    .pickaxe(FCItemInit.STEEL_PICKAXE)
                    .axe(FCItemInit.STEEL_AXE)
                    .shovel(FCItemInit.STEEL_SHOVEL)
                    .hoe(FCItemInit.STEEL_HOE)
                    .hammer(FCItemInit.STEEL_HAMMER)
                    .build();

            generateToolSet(exporter, steelSet, FCBlockInit.STEEL_BLOCK, FCItemInit.STEEL_INGOT);
        }


        private static void generateToolSet(RecipeExporter exporter, ToolSet set, ItemConvertible block, ItemConvertible ingot){
            set.getVariants()
                    .forEach((variant, tool) -> {
                        switch (variant){
                            case SWORD -> offerSwordRecipe(exporter, tool, ingot, Items.STICK);
                            case PICKAXE -> offerPickaxeRecipe(exporter,tool, ingot, Items.STICK);
                            case AXE -> offerAxeRecipe(exporter, tool,ingot, Items.STICK);
                            case SHOVEL -> offerShovelRecipe(exporter, tool, ingot, Items.STICK);
                            case HOE -> offerHoeRecipe(exporter, tool, ingot, Items.STICK);
                            case HAMMER -> offerHammerRecipe(exporter, tool, ingot, block, Items.STICK);
                            case HELMET -> offerHelmetRecipe(exporter, tool, ingot);
                            case CHESTPLATE -> offerChestplateRecipe(exporter, tool, ingot);
                            case LEGGINGS -> offerLeggingsRecipe(exporter, tool, ingot);
                            case BOOTS -> offerBootsRecipe(exporter, tool, ingot);
                            default -> throw new IllegalArgumentException("Not valid variant");
                        }
                    });
        }


        private static void offerSwordRecipe(RecipeExporter exporter, ItemConvertible sword, ItemConvertible material, ItemConvertible handle){
            ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, sword)
                    .pattern("#")
                    .pattern("#")
                    .pattern("|")
                    .input('#', material)
                    .input('|', handle)
                    .criterion(hasItem(sword), conditionsFromItem(sword))
                    .criterion(hasItem(material), conditionsFromItem(material))
                    .offerTo(exporter, getCraftingRecipeName(sword));
        }

        private static void offerPickaxeRecipe(RecipeExporter exporter, ItemConvertible pickaxe, ItemConvertible material, ItemConvertible handle){
            ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, pickaxe)
                    .pattern("###")
                    .pattern(" | ")
                    .pattern(" | ")
                    .input('#', material)
                    .input('|', handle)
                    .criterion(hasItem(pickaxe), conditionsFromItem(pickaxe))
                    .criterion(hasItem(material), conditionsFromItem(material))
                    .offerTo(exporter, getCraftingRecipeName(pickaxe));
        }

        private static void offerAxeRecipe(RecipeExporter exporter, ItemConvertible axe, ItemConvertible material, ItemConvertible handle){
            ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, axe)
                    .pattern("##")
                    .pattern("#|")
                    .pattern(" |")
                    .input('#', material)
                    .input('|', handle)
                    .criterion(hasItem(axe), conditionsFromItem(axe))
                    .criterion(hasItem(material), conditionsFromItem(material))
                    .offerTo(exporter, getCraftingRecipeName(axe));
        }

        private static void offerShovelRecipe(RecipeExporter exporter, ItemConvertible shovel, ItemConvertible material, ItemConvertible handle){
            ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, shovel)
                    .pattern("#")
                    .pattern("|")
                    .pattern("|")
                    .input('#', material)
                    .input('|', handle)
                    .criterion(hasItem(shovel), conditionsFromItem(shovel))
                    .criterion(hasItem(material), conditionsFromItem(material))
                    .offerTo(exporter, getCraftingRecipeName(shovel));
        }

        private static void offerHoeRecipe(RecipeExporter exporter, ItemConvertible hoe, ItemConvertible material, ItemConvertible handle){
            ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, hoe)
                    .pattern("##")
                    .pattern("| ")
                    .pattern("| ")
                    .input('#', material)
                    .input('|', handle)
                    .criterion(hasItem(hoe), conditionsFromItem(hoe))
                    .criterion(hasItem(material), conditionsFromItem(material))
                    .offerTo(exporter, getCraftingRecipeName(hoe));
        }

        private static void offerHammerRecipe(RecipeExporter exporter, ItemConvertible hammer,
                                              ItemConvertible ingot, ItemConvertible block, ItemConvertible handle){
            ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, hammer)
                    .pattern("-#-")
                    .pattern(" | ")
                    .pattern(" | ")
                    .input('-', ingot)
                    .input('#', block)
                    .input('|', handle)
                    .criterion(hasItem(hammer), conditionsFromItem(hammer))
                    .criterion(hasItem(ingot), conditionsFromItem(ingot))
                    .offerTo(exporter, getCraftingRecipeName(hammer));
        }

        private static void offerHelmetRecipe(RecipeExporter exporter, ItemConvertible helmet, ItemConvertible material){
            ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, helmet)
                    .pattern("###")
                    .pattern("# #")
                    .input('#', material)
                    .criterion(hasItem(helmet), conditionsFromItem(helmet))
                    .criterion(hasItem(material), conditionsFromItem(material))
                    .offerTo(exporter, getCraftingRecipeName(helmet));
        }

        private static void offerChestplateRecipe(RecipeExporter exporter, ItemConvertible chestplate, ItemConvertible material){
            ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, chestplate)
                    .pattern("# #")
                    .pattern("###")
                    .pattern("###")
                    .input('#', material)
                    .criterion(hasItem(chestplate), conditionsFromItem(chestplate))
                    .criterion(hasItem(material), conditionsFromItem(material))
                    .offerTo(exporter, getCraftingRecipeName(chestplate));
        }

        private static void offerLeggingsRecipe(RecipeExporter exporter, ItemConvertible leggings, ItemConvertible material){
            ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, leggings)
                    .pattern("###")
                    .pattern("# #")
                    .pattern("# #")
                    .input('#', material)
                    .criterion(hasItem(leggings), conditionsFromItem(leggings))
                    .criterion(hasItem(material), conditionsFromItem(material))
                    .offerTo(exporter, getCraftingRecipeName(leggings));
        }

        private static void offerBootsRecipe(RecipeExporter exporter, ItemConvertible boots, ItemConvertible material){
            ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, boots)
                    .pattern("# #")
                    .pattern("# #")
                    .input('#', material)
                    .criterion(hasItem(boots), conditionsFromItem(boots))
                    .criterion(hasItem(material), conditionsFromItem(material))
                    .offerTo(exporter, getCraftingRecipeName(boots));
        }

        private static void offer2TierReversibleCompacting(RecipeExporter exporter,
                                                           RecipeCategory packedCategory, ItemConvertible packed,
                                                           RecipeCategory unpackedCategory, ItemConvertible unpacked){
            ShapedRecipeJsonBuilder.create(packedCategory, packed)
                    .pattern("###")
                    .pattern("###")
                    .pattern("###")
                    .input('#', unpacked)
                    .criterion(hasItem(packed), conditionsFromItem(packed))
                    .criterion(hasItem(unpacked), conditionsFromItem(unpacked))
                    .offerTo(exporter, getCompactingFromRecipeName(packed, unpacked));

            ShapelessRecipeJsonBuilder.create(unpackedCategory, unpacked, 9)
                    .input(packed)
                    .criterion(hasItem(unpacked), conditionsFromItem(unpacked))
                    .criterion(hasItem(packed), conditionsFromItem(packed))
                    .offerTo(exporter, getUnpackingFromRecipeName(unpacked, packed));

        }

        private static void offer3TierReversibleCompacting(RecipeExporter exporter, ItemConvertible block, ItemConvertible ingot, ItemConvertible nugget){
            offer2TierReversibleCompacting(exporter, RecipeCategory.BUILDING_BLOCKS, block, RecipeCategory.MISC, ingot);
            offer2TierReversibleCompacting(exporter, RecipeCategory.MISC, ingot, RecipeCategory.MISC, nugget);
        }

        @Override
        public String getName() {
            return "crafting provider";
        }
    }

    private static class StoneCuttingRecipe extends FabricRecipeProvider {

        public StoneCuttingRecipe(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        public void generate(RecipeExporter exporter) {
            offerStonecuttingRecipe(exporter, RecipeCategory.MISC, FCItemInit.TURQUOISE, () -> Ingredient.ofItems(FCItemInit.RAW_TURQUOISE));
        }

        private static void offerStonecuttingRecipe(RecipeExporter exporter, RecipeCategory category,
                                                    ItemConvertible output, Supplier<Ingredient> input){
            offerStonecuttingRecipe(exporter, category, output, 1, input);
        }
        private static void offerStonecuttingRecipe(RecipeExporter exporter, RecipeCategory category,
                                                  ItemConvertible output, int count, Supplier<Ingredient> ingredient){
                StonecuttingRecipeJsonBuilder.createStonecutting(ingredient.get(), category, output, count)
                        .criterion(hasItem(output), conditionsFromItem(output))
                        .offerTo(exporter, getStonecuttingRecipeName(output));
        }

        @Override
        public String getName() {
            return "stonecutting provider";
        }
    }

    private static class FoundryRecipe extends FabricRecipeProvider{

        public FoundryRecipe(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        public void generate(RecipeExporter exporter) {
            FoundryRecipeJsonBuilder.create(FCItemInit.STEEL_INGOT, 2)
                    .input(Items.IRON_INGOT)
                    .addition(FCTags.Items.CARBON_INPUT)
                    .time(100)
                    .criterion(hasItem(FCBlockInit.FOUNDRY), conditionsFromItem(FCBlockInit.FOUNDRY))
                    .offerTo(exporter,getFoundryRecipeName(FCItemInit.STEEL_INGOT) );
        }

        @Override
        public String getName() {
            return "foundry provider";
        }
    }
}

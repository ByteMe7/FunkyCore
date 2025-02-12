package net.funkystudios.funkycore.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.funkystudios.funkycore.FunkyCore;
import net.funkystudios.funkycore.init.FCBlockInit;
import net.funkystudios.funkycore.utils.FCModels;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.registry.Registries;

import java.util.Optional;

public class FunkyCoreModelProvider extends FabricModelProvider {
    public FunkyCoreModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        Registries.BLOCK.getIds()
                .stream()
                .filter(key -> key.getNamespace().equals(FunkyCore.MOD_ID))
                .map(Registries.BLOCK::getOrEmpty)
                .map(Optional::orElseThrow)
                .filter(block -> !FCBlockInit.NOT_SIMPLE.contains(block))
                .forEach(blockStateModelGenerator::registerSimpleCubeAll);

        FCModels.registerHorizontalFacingModel(FCBlockInit.FOUNDRY, blockStateModelGenerator);

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {

    }


}

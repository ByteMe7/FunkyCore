package net.funkystudios.funkycore.utils;

import net.funkystudios.funkycore.FunkyCore;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import java.util.Optional;

public class FCModels {

    public static final Model FRONT_FACE = block("front_face", TextureKey.LAYER0, TextureKey.PARTICLE);

    private static Model block(String parent, TextureKey... requiredTextureKeys){
        return new Model(Optional.of(FunkyCore.id("block/" + parent)), Optional.empty(), requiredTextureKeys);
    }
    public static void registerHorizontalFacingModel(Block block, BlockStateModelGenerator blockStateModelGenerator){
        blockStateModelGenerator.blockStateCollector.accept(createHorizontalFacing(block, blockStateModelGenerator));
    }

    private static BlockStateSupplier createHorizontalFacing(Block block, BlockStateModelGenerator blockStateModelGenerator){
        Identifier id = FRONT_FACE.upload(block, TextureMap.layer0(block).put(TextureKey.PARTICLE, TextureMap.getId(block)), blockStateModelGenerator.modelCollector);
        return VariantsBlockStateSupplier.create(block)
                .coordinate(
                        BlockStateVariantMap.create(Properties.HORIZONTAL_FACING)
                                .register(Direction.NORTH, BlockStateVariant.create().put(VariantSettings.MODEL, id))
                                .register(
                                        Direction.SOUTH,
                                        BlockStateVariant.create()
                                                .put(VariantSettings.MODEL, id)
                                                .put(VariantSettings.Y, VariantSettings.Rotation.R180)
                                )
                                .register(
                                        Direction.EAST,
                                        BlockStateVariant.create()
                                                .put(VariantSettings.MODEL, id)
                                                .put(VariantSettings.Y, VariantSettings.Rotation.R90)
                                )
                                .register(
                                        Direction.WEST,
                                        BlockStateVariant.create()
                                                .put(VariantSettings.MODEL, id)
                                                .put(VariantSettings.Y, VariantSettings.Rotation.R270)
                                )
                );
    }
}

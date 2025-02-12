package net.funkystudios.funkycore.init;

import net.funkystudios.funkycore.FunkyCore;
import net.funkystudios.funkycore.block.entity.FoundryBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class FCBlockEntityTypeInit {

    public static final BlockEntityType<FoundryBlockEntity> FOUNDRY_BLOCK_ENTITY = register("foundry_block_entity",
            BlockEntityType.Builder.create(FoundryBlockEntity::new, FCBlockInit.FOUNDRY).build());

    public static <T extends BlockEntity>BlockEntityType<T> register(String  name, BlockEntityType<T> type){
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, FunkyCore.id(name), type);
    }
    public static void load(){}
}

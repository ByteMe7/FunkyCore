package net.funkystudios.funkycore.init;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.funkystudios.funkycore.FunkyCore;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

import java.util.Optional;

public class FCItemGroupInit {

    public static final ItemGroup FUNKY_CORE_GROUP = register("funky_core", FabricItemGroup.builder()
            .displayName(createTitle("funky-core"))
            .icon(FCItemInit.TURQUOISE::getDefaultStack)
            .entries((displayContext, entries) -> Registries.ITEM.getIds()
                    .stream()
                    .filter(key -> key.getNamespace().equals(FunkyCore.MOD_ID))
                    .map(Registries.ITEM::getOrEmpty)
                    .map(Optional::orElseThrow)
                    .filter(item -> !FCItemInit.BLACKLIST.contains(item))
                    .forEach(entries::add))
            .build());


    public static <T extends ItemGroup> T register(String name, T itemGroup){
        return Registry.register(Registries.ITEM_GROUP, FunkyCore.id(name), itemGroup);
    }


    private static Text createTitle(String name){
        return Text.translatable("itemGroup." + FunkyCore.MOD_ID + "." + name);
    }

    public static void load(){}
}

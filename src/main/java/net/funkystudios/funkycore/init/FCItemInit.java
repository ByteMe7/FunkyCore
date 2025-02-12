package net.funkystudios.funkycore.init;

import net.funkystudios.funkycore.FunkyCore;
import net.funkystudios.funkycore.item.FCToolMaterials;
import net.funkystudios.funkycore.item.HammerItem;
import net.funkystudios.funkycore.utils.FCItemUtils;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class FCItemInit {
    public static final List<ItemConvertible> BLACKLIST = new ArrayList<>();

    // Vanilla+ items
    public static final HammerItem WOODEN_HAMMER = registerBlacklisted("wooden_hammer", new HammerItem(ToolMaterials.WOOD, new Item.Settings()));
    public static final HammerItem STONE_HAMMER = registerBlacklisted("stone_hammer", new HammerItem(ToolMaterials.STONE, new Item.Settings()));
    public static final HammerItem IRON_HAMMER = registerBlacklisted("iron_hammer", new HammerItem(ToolMaterials.IRON, new Item.Settings()));
    public static final HammerItem GOLD_HAMMER = registerBlacklisted("gold_hammer", new HammerItem(ToolMaterials.GOLD, new Item.Settings()));
    public static final HammerItem DIAMOND_HAMMER = registerBlacklisted("diamond_hammer", new HammerItem(ToolMaterials.DIAMOND, new Item.Settings()));
    public static final HammerItem NETHERITE_HAMMER = registerBlacklisted("netherite_hammer", new HammerItem(ToolMaterials.NETHERITE, new Item.Settings().fireproof()));

    // Materials
    public static final Item RAW_TURQUOISE = register("raw_turquoise", new Item(new Item.Settings()));
    public static final Item TURQUOISE = register("turquoise", new Item(new Item.Settings()));
    public static final Item STEEL_INGOT = register("steel_ingot", new Item(new Item.Settings()));
    public static final Item STEEL_NUGGET = register("steel_nugget", new Item(new Item.Settings()));


    public static final ArmorItem TURQUOISE_HELMET = register("turquoise_helmet", new ArmorItem(FCArmorMaterialInit.TURQUOISE,
            ArmorItem.Type.HELMET, new Item.Settings()));
    public static final ArmorItem TURQUOISE_CHESTPLATE = register("turquoise_chestplate", new ArmorItem(FCArmorMaterialInit.TURQUOISE,
            ArmorItem.Type.CHESTPLATE, new Item.Settings()));
    public static final ArmorItem TURQUOISE_LEGGINGS = register("turquoise_leggings", new ArmorItem(FCArmorMaterialInit.TURQUOISE,
            ArmorItem.Type.LEGGINGS, new Item.Settings()));
    public static final ArmorItem TURQUOISE_BOOTS = register("turquoise_boots", new ArmorItem(FCArmorMaterialInit.TURQUOISE,
            ArmorItem.Type.BOOTS, new Item.Settings()));
    public static final SwordItem TURQUOISE_SWORD = register("turquoise_sword", new SwordItem(FCToolMaterials.TURQUOISE,
            new Item.Settings().attributeModifiers(FCItemUtils.createSwordAttributes(FCToolMaterials.TURQUOISE))));
    public static final PickaxeItem TURQUOISE_PICKAXE = register("turquoise_pickaxe", new PickaxeItem(FCToolMaterials.TURQUOISE,
            new Item.Settings().attributeModifiers(FCItemUtils.createPickaxeAttributes(FCToolMaterials.TURQUOISE))));
    public static final AxeItem TURQUOISE_AXE = register("turquoise_axe", new AxeItem(FCToolMaterials.TURQUOISE,
            new Item.Settings().attributeModifiers(FCItemUtils.createAxeAttributes(FCToolMaterials.TURQUOISE))));
    public static final ShovelItem TURQUOISE_SHOVEL = register("turquoise_shovel", new ShovelItem(FCToolMaterials.TURQUOISE,
            new Item.Settings().attributeModifiers(FCItemUtils.createShovelAttributes(FCToolMaterials.TURQUOISE))));
    public static final HoeItem TURQUOISE_HOE =  register("turquoise_hoe", new HoeItem(FCToolMaterials.TURQUOISE,
            new Item.Settings().attributeModifiers(FCItemUtils.createSwordAttributes(FCToolMaterials.TURQUOISE))));
    public static final HammerItem TURQUOISE_HAMMER = register("turquoise_hammer", new HammerItem(FCToolMaterials.TURQUOISE,
            new Item.Settings().attributeModifiers(FCItemUtils.createHammerAttributes(FCToolMaterials.TURQUOISE))));

    public static final ArmorItem STEEL_HELMET = register("steel_helmet", new ArmorItem(FCArmorMaterialInit.STEEL,
            ArmorItem.Type.HELMET, new Item.Settings()));
    public static final ArmorItem STEEL_CHESTPLATE = register("steel_chestplate", new ArmorItem(FCArmorMaterialInit.STEEL,
            ArmorItem.Type.CHESTPLATE, new Item.Settings()));
    public static final ArmorItem STEEL_LEGGINGS = register("steel_leggings", new ArmorItem(FCArmorMaterialInit.STEEL,
            ArmorItem.Type.LEGGINGS, new Item.Settings()));
    public static final ArmorItem STEEL_BOOTS = register("steel_boots", new ArmorItem(FCArmorMaterialInit.STEEL,
            ArmorItem.Type.BOOTS, new Item.Settings()));
    public static final SwordItem STEEL_SWORD = register("steel_sword", new SwordItem(FCToolMaterials.STEEL,
            new Item.Settings().attributeModifiers(FCItemUtils.createSwordAttributes(FCToolMaterials.STEEL))));
    public static final PickaxeItem STEEL_PICKAXE = register("steel_pickaxe", new PickaxeItem(FCToolMaterials.STEEL,
            new Item.Settings().attributeModifiers(FCItemUtils.createPickaxeAttributes(FCToolMaterials.STEEL))));
    public static final AxeItem STEEL_AXE = register("steel_axe", new AxeItem(FCToolMaterials.STEEL,
            new Item.Settings().attributeModifiers(FCItemUtils.createAxeAttributes(FCToolMaterials.STEEL))));
    public static ShovelItem STEEL_SHOVEL = register("steel_shovel", new ShovelItem(FCToolMaterials.STEEL,
            new Item.Settings().attributeModifiers(FCItemUtils.createShovelAttributes(FCToolMaterials.STEEL))));
    public static HoeItem STEEL_HOE = register("steel_hoe", new HoeItem(FCToolMaterials.STEEL,
            new Item.Settings().attributeModifiers(FCItemUtils.createHoeAttributes(FCToolMaterials.STEEL))));
    public static HammerItem STEEL_HAMMER = register("steel_hammer", new HammerItem(FCToolMaterials.STEEL,
            new Item.Settings().attributeModifiers(FCItemUtils.createHammerAttributes(FCToolMaterials.STEEL))));



    public static <T extends Item> T registerBlacklisted(String name, T item){
        T registered = register(name, item);
        BLACKLIST.add(registered);
        return registered;
    }

    public static <T extends Item> T register(String name, T item){
        return Registry.register(Registries.ITEM, FunkyCore.id(name), item);
    }

    public static void load(){}
}

package net.funkystudios.funkycore.init;

import net.funkystudios.funkycore.FunkyCore;
import net.funkystudios.funkycore.utils.FCTags;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class FCArmorMaterialInit {
    public static final RegistryEntry<ArmorMaterial> TURQUOISE = register("turquoise", Map.of(
            ArmorItem.Type.HELMET, 3,
            ArmorItem.Type.CHESTPLATE, 8,
            ArmorItem.Type.LEGGINGS, 6,
            ArmorItem.Type.BOOTS, 3
        ),
            15,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC,
            () -> Ingredient.fromTag(FCTags.Items.TURQUOISE_REPAIR),
            0.5F,
            0.1F,
            false
    );
    public static final RegistryEntry<ArmorMaterial> STEEL = register("steel", Map.of(
            ArmorItem.Type.HELMET, 4,
            ArmorItem.Type.CHESTPLATE, 6,
            ArmorItem.Type.LEGGINGS, 6,
            ArmorItem.Type.BOOTS, 4

        ),
            15,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC,
            () -> Ingredient.fromTag(FCTags.Items.STEEL_REPAIR),
            0.5F,
            0.1F,
            false
    );

    public static RegistryEntry<ArmorMaterial> register(String id, Map<ArmorItem.Type, Integer> defensePoints,
                                                        int enchantability, RegistryEntry<SoundEvent> equipSound,
                                                        Supplier<Ingredient> repairIngredient, float toughness,
                                                        float kbResistance, boolean dyeable){
        List<ArmorMaterial.Layer> layers = List.of(
                new ArmorMaterial.Layer(FunkyCore.id(id), "", dyeable)
        );
        var material = new ArmorMaterial(defensePoints, enchantability, equipSound, repairIngredient, layers,
                toughness, kbResistance);
        material = Registry.register(Registries.ARMOR_MATERIAL, FunkyCore.id(id), material);
        return RegistryEntry.of(material);
    }

    public static void load(){}
}

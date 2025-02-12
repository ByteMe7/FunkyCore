package net.funkystudios.funkycore.init;

import com.mojang.serialization.MapCodec;
import net.funkystudios.funkycore.FunkyCore;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public class FCEnchantmentInit {
    public static final RegistryKey<Enchantment> EXPANDED_KEY = registerKey("expanded");

    private static <T extends EnchantmentEntityEffect> MapCodec<T> register(String name, MapCodec<T> codec){
        return Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, FunkyCore.id(name), codec);
    }

    private static RegistryKey<Enchantment> registerKey(String name){
        return RegistryKey.of(RegistryKeys.ENCHANTMENT, FunkyCore.id(name));
    }

    public static void load(){}
}

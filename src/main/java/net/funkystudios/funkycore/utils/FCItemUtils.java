package net.funkystudios.funkycore.utils;

import net.funkystudios.funkycore.item.HammerItem;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.*;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;

public class FCItemUtils {



    public static ItemStack getDamagedRemainder(ItemStack stack){
        return  getDamagedRemainder(stack, 1);
    }

    public static ItemStack getDamagedRemainder(ItemStack stack, int damage){
        ItemStack remainderStack = stack.copy();
        int newDamage = remainderStack.getDamage() + damage;
        if(newDamage >= remainderStack.getMaxDamage()){
            return ItemStack.EMPTY;
        }
        remainderStack.setDamage(newDamage);
        return remainderStack;
    }

    public static int getLevelOfEnchantment(ItemStack stack, RegistryKey<Enchantment> enchantmentKey){
        if(!stack.hasEnchantments()){
            return 0;
        }
        int lvl = 0;
        for(RegistryEntry<Enchantment> entry : stack.getEnchantments().getEnchantments()){
            if(entry.matchesKey(enchantmentKey)){
                lvl = EnchantmentHelper.getLevel(entry, stack);
                break;
            }
        }
        return lvl;
    }



    public static AttributeModifiersComponent createSwordAttributes(ToolMaterial material){
        return createSwordAttributes(material, 3, -2.4F);
    }

    public static AttributeModifiersComponent createSwordAttributes(ToolMaterial material, int baseDamage, float attackSpeed){
        return SwordItem.createAttributeModifiers(material, baseDamage, attackSpeed);
    }

    public static AttributeModifiersComponent createPickaxeAttributes(ToolMaterial material){
        return  createPickaxeAttributes(material, 1, -2.8F);
    }

    public static AttributeModifiersComponent createPickaxeAttributes(ToolMaterial material, float baseDamage, float attackSpeed){
        return PickaxeItem.createAttributeModifiers(material, baseDamage, attackSpeed);
    }

    public static AttributeModifiersComponent createAxeAttributes(ToolMaterial material){
        return createAxeAttributes(material, 5, -3.0F);
    }

    public static AttributeModifiersComponent createAxeAttributes(ToolMaterial material, float baseDamage, float attackSpeed){
        return AxeItem.createAttributeModifiers(material, baseDamage, attackSpeed);
    }

    public static AttributeModifiersComponent createShovelAttributes(ToolMaterial material){
        return createShovelAttributes(material, 1.5F, -3.0F);
    }

    public static AttributeModifiersComponent createShovelAttributes(ToolMaterial material, float baseDamage, float attackSpeed){
        return ShovelItem.createAttributeModifiers(material, baseDamage, attackSpeed);
    }

    public static AttributeModifiersComponent createHoeAttributes(ToolMaterial material){
        return createShovelAttributes(material, 0, -3.0F);
    }
    public static AttributeModifiersComponent createHoeAttributes(ToolMaterial material, float baseDamage, float attackSpeed){
        return HoeItem.createAttributeModifiers(material, baseDamage, attackSpeed);
    }

    public static AttributeModifiersComponent createHammerAttributes(ToolMaterial material){
        return createHammerAttributes(material, 7, -1.8F);
    }
    public static AttributeModifiersComponent createHammerAttributes(ToolMaterial material, float baseDamage, float attackSpeed){
        return HammerItem.createAttributeModifiers(material, baseDamage, attackSpeed);
    }
}

package net.funkystudios.funkycore.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.funkystudios.funkycore.init.FCEnchantmentInit;
import net.funkystudios.funkycore.utils.FCTags;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class FunkyCoreDynamicProviders {

    public FunkyCoreDynamicProviders(FabricDataGenerator.Pack pack) {
        pack.addProvider(FunkyCoreEnchantments::new);
    }

    private static class FunkyCoreEnchantments extends FabricDynamicRegistryProvider {

        public FunkyCoreEnchantments(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
            RegistryWrapper<Item> itemLookup = registries.getWrapperOrThrow(RegistryKeys.ITEM);

            register(entries, FCEnchantmentInit.EXPANDED_KEY, Enchantment.builder(
                    Enchantment.definition(
                            itemLookup.getOrThrow(FCTags.Items.EXPANDABLE_MINING_TOOL),
                            15,
                            3,
                            Enchantment.leveledCost(1, 10),
                            Enchantment.leveledCost(1, 15),
                            2,
                            AttributeModifierSlot.MAINHAND
                    )
            ));


        }

        private static void register(Entries entries, RegistryKey<Enchantment> key, Enchantment.Builder builder,
                                     ResourceCondition... resourceConditions){
            entries.add(key, builder.build(key.getValue()), resourceConditions);
        }

        @Override
        public String getName() {
            return "Enchantment Provider";
        }
    }
}

package net.funkystudios.funkycore.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.funkystudios.funkycore.FunkyCore;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class FunkyCoreLangProviders {

    public FunkyCoreLangProviders(FabricDataGenerator.Pack pack){
        pack.addProvider(EnUsProvider::new);
    }

    private static class EnUsProvider extends FabricLanguageProvider {

        protected EnUsProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
            super(dataOutput, registryLookup);
        }

        @Override
        public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {

        }

        private static void addText(@NotNull Text text, @NotNull String value, @NotNull TranslationBuilder builder){
            if(text.getContent() instanceof TranslatableTextContent translatableTextContent){
                builder.add(translatableTextContent.getKey(), value);
            } else {
                FunkyCore.LOGGER.warn("Failed to add translation for text: {}", text.getString());
            }
        }
    }
}

package net.funkystudios.funkycore;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.funkystudios.funkycore.datagen.*;
import net.minecraft.registry.RegistryBuilder;

public class FunkyCoreDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		new FunkyCoreDynamicProviders(pack);
		new FunkyCoreRecipeProviders(pack);
		new FunkyCoreLangProviders(pack);
		new FunkyCoreTagProviders(pack);
		pack.addProvider(FunkyCoreModelProvider::new);
	}

	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {

	}
}

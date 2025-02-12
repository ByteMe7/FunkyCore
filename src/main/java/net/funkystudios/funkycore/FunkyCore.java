package net.funkystudios.funkycore;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.funkystudios.funkycore.event.ExpandedMiningEvent;
import net.funkystudios.funkycore.init.*;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FunkyCore implements ModInitializer {
	public static final String MOD_ID = "funky-core";


	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		FCItemInit.load();
		FCBlockInit.load();
		FCEnchantmentInit.load();
		FCArmorMaterialInit.load();
		FCItemGroupInit.load();
		FCScreenHandlerTypeInit.load();
		FCRecipeSerializerInit.load();
		FCRecipeTypeInit.load();
		FCBlockEntityTypeInit.load();


		PlayerBlockBreakEvents.BEFORE.register(new ExpandedMiningEvent());



		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> {
			entries.addAfter(Items.WOODEN_HOE, FCItemInit.WOODEN_HAMMER);
			entries.addAfter(Items.STONE_HOE, FCItemInit.STONE_HAMMER);
			entries.addAfter(Items.IRON_HOE, FCItemInit.IRON_HAMMER);
			entries.addAfter(Items.GOLDEN_HOE, FCItemInit.GOLD_HAMMER);
			entries.addAfter(Items.DIAMOND_HOE, FCItemInit.DIAMOND_HAMMER);
			entries.addAfter(Items.NETHERITE_HOE, FCItemInit.NETHERITE_HAMMER);
		});
	}

	public static Identifier id(String path){
		return Identifier.of(MOD_ID, path);
	}
}
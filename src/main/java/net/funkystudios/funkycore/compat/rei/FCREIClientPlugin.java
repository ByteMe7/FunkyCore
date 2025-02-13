package net.funkystudios.funkycore.compat.rei;

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.funkystudios.funkycore.compat.rei.category.FoundryCategory;
import net.funkystudios.funkycore.compat.rei.dislpay.FoundryDisplay;
import net.funkystudios.funkycore.init.FCBlockInit;
import net.funkystudios.funkycore.init.FCRecipeTypeInit;
import net.funkystudios.funkycore.recipe.custom.FoundryRecipe;

public class FCREIClientPlugin implements REIClientPlugin {

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new FoundryCategory());

        registry.addWorkstations(FoundryCategory.FOUNDRY, EntryStacks.of(FCBlockInit.FOUNDRY));

    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerRecipeFiller(FoundryRecipe.class, FCRecipeTypeInit.FOUNDRY, FoundryDisplay::new);
    }
}

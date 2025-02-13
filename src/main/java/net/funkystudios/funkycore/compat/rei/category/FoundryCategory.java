package net.funkystudios.funkycore.compat.rei.category;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.funkystudios.funkycore.FunkyCore;
import net.funkystudios.funkycore.compat.rei.dislpay.FoundryDisplay;
import net.funkystudios.funkycore.init.FCBlockInit;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.LinkedList;
import java.util.List;

public class FoundryCategory implements DisplayCategory<FoundryDisplay> {
    public static final Identifier TEXTURE = FunkyCore.id("textures/gui/machine/foundry_gui.png");
    public static final CategoryIdentifier<FoundryDisplay> FOUNDRY = CategoryIdentifier.of(FunkyCore.id("foundry"));
    @Override
    public CategoryIdentifier<? extends FoundryDisplay> getCategoryIdentifier() {
        return FOUNDRY;
    }

    @Override
    public Text getTitle() {
        return Text.translatable("title.blockEntity.foundry-blockEntity");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(FCBlockInit.FOUNDRY.asItem().getDefaultStack());
    }

    @Override
    public List<Widget> setupDisplay(FoundryDisplay display, Rectangle bounds) {
        final Point startPoint = new Point(bounds.getCenterX() - 87, bounds.getCenterY() - 35);
        List<Widget> widgets = new LinkedList<>();
        widgets.add(Widgets.createTexturedWidget(TEXTURE, new Rectangle(startPoint.x, startPoint.y, 175, 82)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 80, startPoint.y + 11))
                .entries(display.getInputEntries().get(0)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 80, startPoint.y - 7))
                .entries(display.getInputEntries().get(1)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 200, startPoint.y + 9))
                .markOutput().entries(display.getOutputEntries().get(0)));

        return widgets;
    }

    @Override
    public int getDisplayHeight() {
        return 90;
    }
}

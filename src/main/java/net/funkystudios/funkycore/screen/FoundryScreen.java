package net.funkystudios.funkycore.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.funkystudios.funkycore.FunkyCore;
import net.funkystudios.funkycore.screen.handler.FoundryScreenHandler;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class FoundryScreen extends HandledScreen<FoundryScreenHandler> {
    private static final Identifier TEXTURE = FunkyCore.id("textures/gui/machine/foundry_gui.png");
    public FoundryScreen(FoundryScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundWidth = 230;
        this.backgroundHeight = 256;
        this.playerInventoryTitleY = this.backgroundHeight - 124;
        this.playerInventoryTitleX += 32;
        this.titleX += 32;
        this.titleY += 40;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
        renderProgressArrow(context, x, y);
    }

    private void renderProgressArrow(DrawContext context, int x, int y){
        if(handler.isCrafting()){
            context.drawTexture(TEXTURE, x + 85, y + 30, 176, 0, handler.getScaledProgress(), 16);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}

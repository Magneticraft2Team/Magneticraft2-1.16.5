package com.magneticraft2.client.gui.screen;

import com.magneticraft2.client.gui.container.ContainerHeatGenerator;
import com.magneticraft2.common.magneticraft2;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;

import java.util.List;

public class ScreenHeatGenerator extends ContainerScreen<ContainerHeatGenerator> {
    private ResourceLocation GUI = new ResourceLocation(magneticraft2.MOD_ID + ":textures/gui/heatgeneratorgui.png");
    public ScreenHeatGenerator(ContainerHeatGenerator container, PlayerInventory inv, ITextComponent name) {
        super(container, inv, name);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
        if (mouseX > leftPos + 49 && mouseX < leftPos + 62 && mouseY > topPos + 27 && mouseY < topPos + 67)
            this.renderTooltip(matrixStack, new StringTextComponent( this.menu.getHeat() + " c"), mouseX, mouseY);
        if (mouseX > leftPos + 121 && mouseX < leftPos + 134 && mouseY > topPos + 27 && mouseY < topPos + 67)
            this.renderTooltip(matrixStack, new StringTextComponent(this.menu.getEnergy() + " RF"), mouseX, mouseY);
    }
    @Override
    protected void renderLabels(MatrixStack matrixStack, int mouseX, int mouseY) {

    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bind(GUI);
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        this.blit(matrixStack, relX, relY, 0, 0, this.imageWidth + 4, this.imageHeight + 25);
        int k = this.getEnergyStoredScaled(40);
        this.blit(matrixStack, this.leftPos + 121, this.topPos + 27, 180, 42, 16, 41 - k);
        int c = this.getHeatStoredScaled(40);
        this.blit(matrixStack, this.leftPos + 49, this.topPos + 27, 180, 0, 16 , 41 - c);
    }

    private int getEnergyStoredScaled(int pixels) {
        int i = this.menu.getEnergy();
        int j = this.menu.getEnergylimit();
        return i != 0 && j != 0 ? i * pixels / j : 0;
    }
    private int getHeatStoredScaled(int pixels) {
        int i = this.menu.getHeat();
        int j = this.menu.getHeatLimit();
        return i != 0 && j != 0 ? i * pixels / j : 0;
    }


}

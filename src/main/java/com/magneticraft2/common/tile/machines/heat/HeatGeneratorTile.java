package com.magneticraft2.common.tile.machines.heat;


import com.magneticraft2.client.gui.container.ContainerHeatGenerator;
import com.magneticraft2.common.registry.TileentityRegistry;
import com.magneticraft2.common.tile.TileEntityMagneticraft2;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

public class HeatGeneratorTile extends TileEntityMagneticraft2 {

    public HeatGeneratorTile() {
        super(TileentityRegistry.Tile_Heat_Generator.get());
        containerProvider = this;

    }

    @Override
    public void tick() {
        if (!level.isClientSide){
            if (getEnergyStorage() < 1) {
                if (level.getGameTime() % 15 == 0) {
                    if (this.getHeatStorage() > getStartHeat(level, getBlockPos())) {
                        this.removeHeatFromStorage(1);
                    }
                }
                return;
            }

            if (this.getHeatStorage() >= this.getMaxHeatStorage()){
                this.setHeatHeat(this.getMaxHeatStorage());
                this.removeHeatFromStorage(2);
            }else{
                this.removeEnergyFromStorage(300);
                this.addHeatToStorage(1);
            }
        }

    }


    @Override
    public void registerControllers(AnimationData data) {

    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("screen.magneticraft2.heatgenerator");
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new ContainerHeatGenerator(i,level,getBlockPos(),playerInventory,playerEntity);
    }

    @Override
    public Integer capacityE() {
        return 3000;
    }

    @Override
    public Integer maxtransferE() {
        return 200;
    }

    @Override
    public Integer capacityH() {
        return 100;
    }

    @Override
    public Integer maxtransferH() {
        return 200;
    }

    @Override
    public Integer capacityW() {
        return 0;
    }

    @Override
    public Integer maxtransferW() {
        return 0;
    }

    @Override
    public Integer capacityF() {
        return 0;
    }

    @Override
    public Integer tanks() {
        return 0;
    }

    @Override
    public Integer invsize() {
        return 0;
    }

    @Override
    public Integer capacityP() {
        return 0;
    }

    @Override
    public Integer maxtransferP() {
        return 0;
    }

    @Override
    public boolean itemcape() {
        return false;
    }

    @Override
    public boolean energycape() {
        return true;
    }

    @Override
    public boolean heatcape() {
        return true;
    }

    @Override
    public boolean wattcape() {
        return false;
    }

    @Override
    public boolean fluidcape() {
        return false;
    }

    @Override
    public boolean pressurecape() {
        return false;
    }

    @Override
    public boolean HeatCanReceive() {
        return false;
    }

    @Override
    public boolean HeatCanSend() {
        return true;
    }

    @Override
    public boolean WattCanReceive() {
        return false;
    }

    @Override
    public boolean WattCanSend() {
        return false;
    }

    @Override
    public boolean EnergyCanReceive() {
        return true;
    }

    @Override
    public boolean EnergyCanSend() {
        return false;
    }

    @Override
    public boolean PressureCanReceive() {
        return false;
    }

    @Override
    public boolean PressureCanSend() {
        return false;
    }
}

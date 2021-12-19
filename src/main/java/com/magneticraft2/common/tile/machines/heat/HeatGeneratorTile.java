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
        setTransferAndCapacity(3000,3000,3,100,3000,100,3000,3000,1,100,3000);
        shouldHaveCapability(false,true,true,false,false,false);
        setReceiveAndOrSend(true, false, false,false,false,true,false,false);
        containerProvider = this;

    }

    @Override
    public void tick() {
        if (!level.isClientSide){
            if (getEnergyStorage() < 1) {
                if (level.getGameTime() % 15 == 0) {
                    if (this.getHeatStorage() > 1) {
                        this.removeHeatFromStorage(1.3);
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
}

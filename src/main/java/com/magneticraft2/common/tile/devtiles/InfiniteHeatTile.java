package com.magneticraft2.common.tile.devtiles;

import com.magneticraft2.common.registry.TileentityRegistry;
import com.magneticraft2.common.tile.TileEntityMagneticraft2;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

public class InfiniteHeatTile extends TileEntityMagneticraft2 {
    public InfiniteHeatTile() {
        super(TileentityRegistry.Tile_DevT1_Heat_inf.get());
    }



    @Override
    public void tick() {
        if (!level.isClientSide){
            if (this.getHeatStorage() >= this.getMaxHeatStorage()){
                this.setHeatHeat(this.getMaxHeatStorage());
            }else{
                this.addHeatToStorage(Integer.MAX_VALUE);
            }
        }
    }

    @Override
    public void registerControllers(AnimationData data) {

    }

    @Override
    public AnimationFactory getFactory() {
        return null;
    }

    @Override
    public ITextComponent getDisplayName() {
        return null;
    }

    @Nullable
    @Override
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
        return null;
    }

    @Override
    public Integer capacityE() {
        return 0;
    }

    @Override
    public Integer maxtransferE() {
        return 0;
    }

    @Override
    public Integer capacityH() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Integer maxtransferH() {
        return 3000;
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
        return false;
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
        return false;
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

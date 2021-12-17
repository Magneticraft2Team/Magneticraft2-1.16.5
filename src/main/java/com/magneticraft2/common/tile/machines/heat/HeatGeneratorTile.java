package com.magneticraft2.common.tile.machines.heat;


import com.magneticraft2.common.registry.TileentityRegistry;
import com.magneticraft2.common.tile.TileEntityMagneticraft2;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class HeatGeneratorTile extends TileEntityMagneticraft2 {

    public HeatGeneratorTile() {
        super(TileentityRegistry.Tile_Heat_Generator.get());
        setTransferAndCapacity(3000,3000,3,100,3000,100,3000,3000,1,100,3000);
        shouldHaveCapability(false,true,true,false,false,false);
        setReceiveAndOrSend(true, false, false,false,false,true,false,false);


    }

    @Override
    public void tick() {
        if (getEnergyStorage() < 1) {
            return;
        }
        if (this.getHeatStorage() >= this.getMaxHeatStorage()){
            this.setHeatHeat(this.getMaxHeatStorage());
        }else{
            this.addHeatToStorage(1);
            this.removeEnergyFromStorage(300);
        }
    }


    @Override
    public void registerControllers(AnimationData data) {

    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}

package com.magneticraft2.common.tile.machines.heat;


import com.magneticraft2.common.registry.TileentityRegistry;
import com.magneticraft2.common.tile.TileEntityMagneticraft2;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class HeatGeneratorTile extends TileEntityMagneticraft2 {

    public HeatGeneratorTile() {
        super(TileentityRegistry.Tile_Heat_Generator.get());
        setTransferAndCapacity(3000,3000,3,3000,3000,3000,3000,3000,1,3000,3000);
        shouldHaveCapability(false,true,true,false,false,false);


    }

    @Override
    public void tick() {

    }

    @Override
    public void registerControllers(AnimationData data) {

    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}

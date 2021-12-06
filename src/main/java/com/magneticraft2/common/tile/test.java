package com.magneticraft2.common.tile;

import com.magneticraft2.common.registry.TileentityRegistry;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class test extends TileEntityMagneticraft2{


    public test() {
        super(TileentityRegistry.test.get());
        shouldHaveCapability(false,true,true);
        setCapacity(3000);
        setMaxtransfer(300);
        setInvsize(0);
        setHeatCapacity(3000);
        setMaxHeattransfer(300);
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

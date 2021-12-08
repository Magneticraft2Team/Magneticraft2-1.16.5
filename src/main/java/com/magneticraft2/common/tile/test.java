package com.magneticraft2.common.tile;

import com.magneticraft2.common.registry.TileentityRegistry;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class test extends TileEntityMagneticraft2{


    public test() {
        super(TileentityRegistry.test.get());
        /* Capabilities */
        shouldHaveCapability(false,true,true, true, false);
        /* Inv */
        setInvsize(0);
        /* Energy */
        setCapacity(3000);
        setMaxtransfer(300);
        /* Heat */
        setHeatCapacity(3000);
        setMaxHeattransfer(300);
        /* Watt */
        setWattCapacity(3000);
        setMaxWatttransfer(3000);
        /* Fluid */
        setFluidCapacity(3000);
        setFluidTanks(1);
        /* End */
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

package com.magneticraft2.common.tile;

import com.magneticraft2.common.systems.heating.IHeatCapacitor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.List;

public class test extends TileEntityMagneticraft2{
    public test(TileEntityType<?> p_i48289_1_) {
        super(p_i48289_1_);
        shouldHaveCapability(false,true,false);
        setCapacity(3000);
        setMaxtransfer(300);
        setInvsize(0);
    }



    @Override
    public void onContentChanged() {

    }

    @Override
    public List<IHeatCapacitor> getHeatCapacitors(@Nullable Direction side) {
        return null;
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

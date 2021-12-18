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
        setTransferAndCapacity(0,0,0,Integer.MAX_VALUE,3000,3000,3000,3000,1,3000,3000);
        shouldHaveCapability(false,false,true,false,false,false);
        setReceiveAndOrSend(false, false, false,false,false,true,false,false);
    }



    @Override
    public void tick() {

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
}

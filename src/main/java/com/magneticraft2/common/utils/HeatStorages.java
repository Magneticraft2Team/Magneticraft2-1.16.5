package com.magneticraft2.common.utils;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class HeatStorages extends com.magneticraft2.common.systems.heat.HeatStorage implements INBTSerializable<CompoundNBT> {
    public HeatStorages(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }
    protected void onHeatChanged() {
    }

    @Override
    public CompoundNBT serializeNBT() {
        return null;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {

    }
}

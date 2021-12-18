package com.magneticraft2.common.utils;

import com.magneticraft2.common.systems.heat.HeatStorage;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.INBTSerializable;

public class HeatStorages extends HeatStorage implements INBTSerializable<CompoundNBT> {
    public HeatStorages(long capacity, long maxTransfer) {
        super(capacity, maxTransfer);
    }
    protected void onHeatChanged() {
    }
    public void setHeat(double heat) {
        this.heat = heat;
        onHeatChanged();
    }
    public void addHeat(double heat) {
        this.heat += heat;
        if (this.heat > getMaxHeatStored()) {
            this.heat = getHeatStored();
        }
        onHeatChanged();
    }
    public void consumeHeat(double heat) {
        this.heat -= heat;
        if (this.heat < 0) {
            this.heat = 0;
        }
        onHeatChanged();
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT tag = new CompoundNBT();
        tag.putDouble("heat", getHeatStored());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        setHeat(nbt.getInt("heat"));
    }
}

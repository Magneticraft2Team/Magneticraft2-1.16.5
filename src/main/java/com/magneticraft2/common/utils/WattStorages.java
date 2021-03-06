package com.magneticraft2.common.utils;

import com.magneticraft2.common.systems.watt.WattStorage;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class WattStorages extends WattStorage implements INBTSerializable<CompoundNBT> {
    public WattStorages(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }
    protected void onWattChanged() {
    }

    public void setWatt(int watt) {
        this.watt = watt;
        onWattChanged();
    }
    public void addWatt(int watt) {
        this.watt += watt;
        if (this.watt > getMaxWattStored()) {
            this.watt = getWattStored();
        }
        onWattChanged();
    }
    public void consumeWatt(int watt) {
        this.watt -= watt;
        if (this.watt < 0) {
            this.watt = 0;
        }
        onWattChanged();
    }

    @Override
    public CompoundNBT serializeNBT() {
         CompoundNBT tag = new CompoundNBT();
         tag.putInt("heat", getWattStored());
         return tag;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        setWatt(nbt.getInt("watt"));
    }
}

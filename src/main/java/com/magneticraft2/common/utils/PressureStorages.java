package com.magneticraft2.common.utils;

import com.magneticraft2.common.systems.pressure.PressureStorage;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class PressureStorages extends PressureStorage implements INBTSerializable<CompoundNBT> {

    public PressureStorages(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }
    protected void onPressureChanged() {

    }
    public void setPressure(int pressure) {
        this.pressure = pressure;
        onPressureChanged();
    }
    public void addPressure(int pressure) {
        this.pressure += pressure;
        if (this.pressure > getMaxPressureStored()) {
            this.pressure = getPressureStored();
        }
        onPressureChanged();
    }
    public void consumePressure(int pressure) {
        this.pressure -= pressure;
        if (this.pressure < 0) {
            this.pressure = 0;
        }
        onPressureChanged();
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT tag = new CompoundNBT();
        tag.putInt("pressure", getPressureStored());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        setPressure(nbt.getInt("pressure"));
    }
}

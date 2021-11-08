package com.magneticraft2.common.systems.heating;

import com.magneticraft2.common.systems.IContentsListener;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public interface IHeatCapacitor extends INBTSerializable<CompoundNBT>, IContentsListener {

    double getTemp();

    double getConduction();

    double getInsulation();

    double getHeatCapacity();

    double getHeat();

    void setHeat(double heat);

    void handleHeat(double transfer);

    @Override
    default CompoundNBT serializeNBT(){
        CompoundNBT nbt = new CompoundNBT();
        nbt.putDouble("heatStored", getHeat());
        return nbt;
    }
}

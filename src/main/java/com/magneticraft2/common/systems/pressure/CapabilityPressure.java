package com.magneticraft2.common.systems.pressure;

import com.magneticraft2.common.systems.heat.HeatStorage;
import com.magneticraft2.common.systems.heat.IHeatStorage;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;

public class CapabilityPressure {
    @CapabilityInject(IPressureStorage.class)
    public static Capability<IPressureStorage> PRESSURE = null;

    public static void register(){
        CapabilityManager.INSTANCE.register(IPressureStorage.class, new Capability.IStorage<IPressureStorage>() {

            @Nullable
            @Override
            public INBT writeNBT(Capability<IPressureStorage> capability, IPressureStorage instance, Direction side) {
                return IntNBT.valueOf(instance.getPressureStored());
            }

            @Override
            public void readNBT(Capability<IPressureStorage> capability, IPressureStorage instance, Direction side, INBT nbt) {
                if (!(instance instanceof HeatStorage))
                    throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
                ((PressureStorage)instance).pressure = ((IntNBT)nbt).getAsInt();
            }
        }, () -> new PressureStorage(1000));
    }
}

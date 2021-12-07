package com.magneticraft2.common.systems.watt;

import com.magneticraft2.common.systems.heat.HeatStorage;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;

public class CapabilityWatt {
    @CapabilityInject(IWattStorage.class)
    public static Capability<IWattStorage> WATT = null;

    public static void register(){
        CapabilityManager.INSTANCE.register(IWattStorage.class, new Capability.IStorage<IWattStorage>() {
            @Nullable
            @Override
            public INBT writeNBT(Capability<IWattStorage> capability, IWattStorage instance, Direction side) {
                return IntNBT.valueOf(instance.getWattStored());
            }

            @Override
            public void readNBT(Capability<IWattStorage> capability, IWattStorage instance, Direction side, INBT nbt) {
                if (!(instance instanceof WattStorage))
                    throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
                ((WattStorage)instance).watt = ((IntNBT)nbt).getAsInt();
            }
        }, () -> new WattStorage(1000));
    }
}

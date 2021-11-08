package com.magneticraft2.common.systems.heating;

import net.minecraft.util.Direction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface IHeatCapacitorHolder extends IHolder{

    @Nonnull
    List<IHeatCapacitor> getHeatCapacitors(@Nullable Direction side);
}

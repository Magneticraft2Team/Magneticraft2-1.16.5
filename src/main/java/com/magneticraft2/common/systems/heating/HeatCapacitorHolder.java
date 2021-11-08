package com.magneticraft2.common.systems.heating;

import com.magneticraft2.common.utils.BasicHolder;
import com.magneticraft2.common.utils.RelativeSide;
import net.minecraft.util.Direction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class HeatCapacitorHolder extends BasicHolder<IHeatCapacitor> implements IHeatCapacitorHolder{
    public HeatCapacitorHolder(Supplier<Direction> facingSupplier) {
        super(facingSupplier);
    }

    public void addCapacitor(@Nonnull IHeatCapacitor tank, RelativeSide... sides) {
        addSlotInternal(tank, sides);
    }

    @Nonnull
    @Override
    public List<IHeatCapacitor> getHeatCapacitors(@Nullable Direction direction) {
        return getSlots(direction);
    }
}

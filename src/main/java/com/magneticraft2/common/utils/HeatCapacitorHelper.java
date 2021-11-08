package com.magneticraft2.common.utils;

import com.magneticraft2.common.systems.heating.HeatCapacitorHolder;
import com.magneticraft2.common.systems.heating.IHeatCapacitor;
import com.magneticraft2.common.systems.heating.IHeatCapacitorHolder;
import net.minecraft.util.Direction;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class HeatCapacitorHelper {
    private final IHeatCapacitorHolder slotHolder;
    private boolean built;
    private HeatCapacitorHelper(IHeatCapacitorHolder slotHolder) {
        this.slotHolder = slotHolder;
    }
    public static HeatCapacitorHelper forSide(Supplier<Direction> facingSupplier) {
        return new HeatCapacitorHelper(new HeatCapacitorHolder(facingSupplier));
    }
    public void addCapacitor(@Nonnull IHeatCapacitor capacitor) {
        if (built) {
            throw new IllegalStateException("Builder has already built.");
        }
        if (slotHolder instanceof HeatCapacitorHolder) {
            ((HeatCapacitorHolder) slotHolder).addCapacitor(capacitor);
        } else {
            throw new IllegalArgumentException("Holder does not know how to add capacitors");
        }
    }
    public void addCapacitor(@Nonnull IHeatCapacitor container, RelativeSide... sides) {
        if (built) {
            throw new IllegalStateException("Builder has already built.");
        }
        if (slotHolder instanceof HeatCapacitorHolder) {
            ((HeatCapacitorHolder) slotHolder).addCapacitor(container, sides);
        } else {
            throw new IllegalArgumentException("Holder does not know how to add capacitors on specific sides");
        }
    }
    public IHeatCapacitorHolder build() {
        built = true;
        return slotHolder;
    }
}

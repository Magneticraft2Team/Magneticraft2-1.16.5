package com.magneticraft2.common.systems.heating;

import com.magneticraft2.common.systems.IContentsListener;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.util.Direction;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public interface  IMagneticraft2HeatHandler extends ISidedHeatHandler, IContentsListener {

    default boolean canHandleHeat() {
        return true;
    }

    @Override
    default int getHeatCapacitorCount(@Nullable Direction side) {
        return getHeatCapacitors(side).size();
    }
    List<IHeatCapacitor> getHeatCapacitors(@Nullable Direction side);
    @Nullable
    default IHeatCapacitor getHeatCapacitor(int capacitor, @Nullable Direction side) {
        List<IHeatCapacitor> capacitors = getHeatCapacitors(side);
        return capacitor >= 0 && capacitor < capacitors.size() ? capacitors.get(capacitor) : null;
    }


    @Override
    default double getTemp(int capacitor, @Nullable Direction side) {
        IHeatCapacitor heatCapacitor = getHeatCapacitor(capacitor, side);
        return heatCapacitor == null ? APIHeat.AMBIENT_TEMP : heatCapacitor.getTemp();
    }

    @Override
    default double getConduction(int capacitor, @Nullable Direction side) {
        IHeatCapacitor heatCapacitor = getHeatCapacitor(capacitor, side);
        return heatCapacitor == null ? APIHeat.DEFAULT_INVERSE_CONDUCTION : heatCapacitor.getConduction();
    }

    @Override
    default double getHeatCapacity(int capacitor, @Nullable Direction side) {
        IHeatCapacitor heatCapacitor = getHeatCapacitor(capacitor, side);
        return heatCapacitor == null ? APIHeat.DEFAULT_HEAT_CAPACITY : heatCapacitor.getHeatCapacity();
    }

    @Override
    default void handleHeat(int capacitor, double transfer, @Nullable Direction side) {
        IHeatCapacitor heatCapacitor = getHeatCapacitor(capacitor, side);
        if (heatCapacitor != null) {
            heatCapacitor.handleHeat(transfer);
        }
    }
    default double getInverseInsulation(int capacitor, @Nullable Direction side) {
        IHeatCapacitor heatCapacitor = getHeatCapacitor(capacitor, side);
        return heatCapacitor == null ? APIHeat.DEFAULT_INVERSE_INSULATION : heatCapacitor.getInsulation();
    }
    default double getTotalInverseInsulation(@Nullable Direction side) {
        int heatCapacitorCount = getHeatCapacitorCount(side);
        if (heatCapacitorCount == 1) {
            return getInverseInsulation(0, side);
        }
        double sum = 0;
        double totalCapacity = getTotalHeatCapacity(side);
        for (int capacitor = 0; capacitor < heatCapacitorCount; capacitor++) {
            sum += getInverseInsulation(capacitor, side) * (getHeatCapacity(capacitor, side) / totalCapacity);
        }
        return sum;
    }
}

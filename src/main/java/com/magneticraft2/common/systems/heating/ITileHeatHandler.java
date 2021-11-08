package com.magneticraft2.common.systems.heating;

import com.magneticraft2.common.utils.EnumUtils;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.util.Direction;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public interface ITileHeatHandler extends IMagneticraft2HeatHandler {
    default void UpdateHeatCapacitors(@Nullable Direction side) {
        for (IHeatCapacitor capacitor : getHeatCapacitors(side)){
            if (capacitor instanceof  HeatCapacitorHandler){
                ((HeatCapacitorHandler) capacitor).update();
            }
        }
    }
    @Nullable
    default IHeatHandler getAdjacent(@Nullable Direction side) {
        return null;
    }
    default APIHeat.HeatTransfer simulate() {
        return new APIHeat.HeatTransfer(simulateAdjacent(), simulateEnvironment());
    }
    default double simulateEnvironment() {
        double environmentTransfer = 0;
        for (Direction side : EnumUtils.DIRECTIONS) {
            double heatCapacity = getTotalHeatCapacity(side);
            //transfer to air otherwise
            double invConduction = APIHeat.AIR_INVERSE_COEFFICIENT + getTotalInverseInsulation(side) + getTotalInverseConductionCoefficient(side);
            //transfer heat difference based on environment temperature (ambient)
            double tempToTransfer = (getTotalTemperature(side) - APIHeat.AMBIENT_TEMP) / invConduction;
            handleHeat(-tempToTransfer * heatCapacity, side);
            environmentTransfer += tempToTransfer;
        }
        return environmentTransfer;
    }

    default double simulateAdjacent() {
        double adjacentTransfer = 0;
        for (Direction side : EnumUtils.DIRECTIONS) {
            IHeatHandler sink = getAdjacent(side);
            //we use the same heat capacity for all further calculations
            double heatCapacity = getTotalHeatCapacity(side);
            if (sink != null) {
                double invConduction = sink.getTotalInverseConduction() + getTotalInverseConductionCoefficient(side);
                double tempToTransfer = (getTotalTemperature(side) - APIHeat.AMBIENT_TEMP) / invConduction;
                handleHeat(-tempToTransfer * heatCapacity, side);
                sink.handleHeat(tempToTransfer * heatCapacity);
                adjacentTransfer += tempToTransfer;
            }

        }
        return adjacentTransfer;
    }
}

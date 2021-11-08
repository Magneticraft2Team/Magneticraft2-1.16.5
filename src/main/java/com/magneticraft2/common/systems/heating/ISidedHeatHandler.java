package com.magneticraft2.common.systems.heating;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.util.Direction;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public interface ISidedHeatHandler extends IHeatHandler {

    @Nullable
    default Direction getHeatsideFor(){
        return null;
    }
    int getHeatCapacitorCount(@Nullable Direction side);

    @Override
    default int getHeatCapacitorCount(){
        return getHeatCapacitorCount(getHeatsideFor());
    }

    double getTemp(int capacitor, @Nullable Direction side);

    @Override
    default double getTemperature(int capacitor){
        return getTemp(capacitor, getHeatsideFor());
    }
    double getConduction(int capacitor, @Nullable Direction side);

    @Override
    default double getInverseConduction(int capacitor){
        return getConduction(capacitor, getHeatsideFor());
    }
    double getHeatCapacity(int capacitor, @Nullable Direction side);
    @Override
    default double getHeatCapacity(int capacitor) {
        return getHeatCapacity(capacitor, getHeatsideFor());
    }
    void handleHeat(int capacitor, double transfer, @Nullable Direction side);

    @Override
    default void handleHeat(int capacitor, double transfer) {
        handleHeat(capacitor, transfer, getHeatsideFor());
    }
    default double getTotalTemperature(@Nullable Direction side) {
        int heatCapacitorCount = getHeatCapacitorCount(side);
        if (heatCapacitorCount == 1) {
            return getTemp(0, side);
        }
        double sum = 0;
        double totalCapacity = getTotalHeatCapacity(side);
        for (int capacitor = 0; capacitor < heatCapacitorCount; capacitor++) {
            sum += getTemp(capacitor, side) * (getHeatCapacity(capacitor, side) / totalCapacity);
        }
        return sum;
    }
    default double getTotalInverseConductionCoefficient(@Nullable Direction side) {
        int heatCapacitorCount = getHeatCapacitorCount(side);
        if (heatCapacitorCount == 0) {
            return APIHeat.DEFAULT_INVERSE_CONDUCTION;
        } else if (heatCapacitorCount == 1) {
            return getConduction(0, side);
        }
        double sum = 0;
        double totalCapacity = getTotalHeatCapacity(side);
        for (int capacitor = 0; capacitor < heatCapacitorCount; capacitor++) {
            sum += getConduction(capacitor, side) * (getHeatCapacity(capacitor, side) / totalCapacity);
        }
        return sum;
    }
    default double getTotalHeatCapacity(@Nullable Direction side) {
        int heatCapacitorCount = getHeatCapacitorCount(side);
        if (heatCapacitorCount == 1) {
            return getHeatCapacity(0, side);
        }
        double sum = 0;
        for (int capacitor = 0; capacitor < heatCapacitorCount; capacitor++) {
            sum += getHeatCapacity(capacitor, side);
        }
        return sum;
    }
    default void handleHeat(double transfer, @Nullable Direction side) {
        int heatCapacitorCount = getHeatCapacitorCount(side);
        if (heatCapacitorCount == 1) {
            handleHeat(0, transfer, side);
        } else {
            double totalHeatCapacity = getTotalHeatCapacity(side);
            for (int capacitor = 0; capacitor < heatCapacitorCount; capacitor++) {
                handleHeat(capacitor, transfer * (getHeatCapacity(capacitor, side) / totalHeatCapacity), side);
            }
        }
    }
}

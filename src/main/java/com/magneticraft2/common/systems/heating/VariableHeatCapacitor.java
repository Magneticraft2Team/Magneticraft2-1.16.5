package com.magneticraft2.common.systems.heating;

import com.magneticraft2.common.systems.IContentsListener;

import javax.annotation.Nullable;
import java.util.function.DoubleSupplier;

public class VariableHeatCapacitor extends HeatCapacitorHandler{
    private final DoubleSupplier conductionCoefficientSupplier;
    private final DoubleSupplier insulationCoefficientSupplier;
    public static VariableHeatCapacitor create(double heatCapacity, boolean absorbHeat, boolean emitHeat, @Nullable IContentsListener listener) {
        return new VariableHeatCapacitor(heatCapacity, () -> APIHeat.DEFAULT_INVERSE_CONDUCTION, () -> APIHeat.DEFAULT_INVERSE_INSULATION, listener);
    }
    protected VariableHeatCapacitor(double heatCapacity, DoubleSupplier conductionCoefficient, DoubleSupplier insulationCoefficient,
                                    @Nullable IContentsListener listener) {
        super(heatCapacity, conductionCoefficient.getAsDouble(), insulationCoefficient.getAsDouble(), listener);
        this.conductionCoefficientSupplier = conductionCoefficient;
        this.insulationCoefficientSupplier = insulationCoefficient;
    }

    @Override
    public double getConduction() {
        return insulationCoefficientSupplier.getAsDouble();
    }

    @Override
    public double getInsulation() {
        return Math.max(1, conductionCoefficientSupplier.getAsDouble());
    }
}

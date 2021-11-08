package com.magneticraft2.common.systems.heating;

import com.magneticraft2.common.systems.IContentsListener;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.DoubleConsumer;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class HeatCapacitorHandler implements IHeatCapacitor{

    @Nullable
    private final IContentsListener listener;
    private double heatCapacity;
    private final double conductionEff;
    private final double insulationEff;
    protected double storedHeat;
    private double heattohandle;
    public static HeatCapacitorHandler create(double heatCapacity, @Nullable IContentsListener listener) {
        return create(heatCapacity, APIHeat.DEFAULT_INVERSE_CONDUCTION, APIHeat.DEFAULT_INVERSE_INSULATION, listener);
    }
    public static HeatCapacitorHandler create(double heatCapacity, double inverseConductionCoefficient, double inverseInsulationCoefficient, @Nullable IContentsListener listener) {
        if (heatCapacity < 1) {
            throw new IllegalArgumentException("Heat cap must be at least 1");
        }
        if (inverseConductionCoefficient < 1) {
            throw new IllegalArgumentException("Inverse conduction coefficient must be at least 1");
        }
        return new HeatCapacitorHandler(heatCapacity, inverseConductionCoefficient, inverseInsulationCoefficient, listener);
    }
    protected HeatCapacitorHandler(double heatCapacity, double inverseConductionCoefficient, double inverseInsulationCoefficient, @Nullable IContentsListener listener) {
        this.heatCapacity = heatCapacity;
        this.conductionEff = inverseConductionCoefficient;
        this.insulationEff = inverseInsulationCoefficient;
        this.listener = listener;
        storedHeat = heatCapacity * APIHeat.AMBIENT_TEMP;
    }

    @Override
    public void onContentChanged() {
        if (listener != null){
            listener.onContentChanged();
        }
    }


    @Override
    public double getTemp() {
        return getHeat() / getHeatCapacity();
    }

    @Override
    public double getConduction() {
        return conductionEff;
    }

    @Override
    public double getInsulation() {
        return insulationEff;
    }

    @Override
    public double getHeatCapacity() {
        return heatCapacity;
    }

    @Override
    public double getHeat() {
        return storedHeat;
    }

    @Override
    public void setHeat(double heat) {
        if (storedHeat != heat) {
            storedHeat = heat;
            onContentChanged();
        }
    }


    @Override
    public void handleHeat(double transfer) {
        heattohandle += transfer;
    }

    public void update() {
        if (heattohandle != 0) {
            storedHeat += heattohandle;
            onContentChanged();
        }
        heattohandle = 0;
    }
    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.setDoubleIfPresent(nbt, "heatStored", heat -> storedHeat = heat);
        this.setDoubleIfPresent(nbt, "heatCapacity", capacity -> heatCapacity = capacity);
    }
    public static void setDoubleIfPresent(CompoundNBT nbt, String key, DoubleConsumer setter) {
        if (nbt.contains(key, Constants.NBT.TAG_DOUBLE)) {
            setter.accept(nbt.getDouble(key));
        }
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putDouble("heatStored", getHeat());
        nbt.putDouble("heatCapacity", getHeatCapacity());
        return nbt;
    }

    public void setHeatCapacity(double newCapacity, boolean updateHeat) {
        if (updateHeat) {
            setHeat(getHeat() + (newCapacity - getHeatCapacity()) * APIHeat.AMBIENT_TEMP);
        }
        heatCapacity = newCapacity;
    }
}

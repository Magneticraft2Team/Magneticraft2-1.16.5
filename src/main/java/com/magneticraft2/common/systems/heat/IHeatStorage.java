package com.magneticraft2.common.systems.heat;

public interface IHeatStorage {

    double receiveHeat(double maxReceive, boolean simulate);
    double extractHeat(double maxExtract, boolean simulate);
    double getHeatStored();
    double getMaxHeatStored();
    boolean canSend();
    boolean canReceive();
}

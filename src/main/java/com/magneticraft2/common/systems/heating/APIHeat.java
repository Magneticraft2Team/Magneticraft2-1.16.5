package com.magneticraft2.common.systems.heating;

public class APIHeat {
    private APIHeat(){
    }
    public static final double AMBIENT_TEMP = 300;

    public static final double AIR_INVERSE_COEFFICIENT = 10_000;

    public static final double DEFAULT_HEAT_CAPACITY = 1;

    public static final double DEFAULT_INVERSE_CONDUCTION = 1;

    public static final double DEFAULT_INVERSE_INSULATION = 0;
    public static class HeatTransfer {
        private final double adjacentTransfer;
        private final double environmentTransfer;
        public HeatTransfer(double adjacentTransfer, double environmentTransfer) {
            this.adjacentTransfer = adjacentTransfer;
            this.environmentTransfer = environmentTransfer;
        }
        public double getAdjacentTransfer() {
            return adjacentTransfer;
        }

        public double getEnvironmentTransfer() {
            return environmentTransfer;
        }
    }
}

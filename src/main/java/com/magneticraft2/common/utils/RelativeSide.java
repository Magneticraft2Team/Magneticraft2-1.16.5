package com.magneticraft2.common.utils;

import net.minecraft.util.Direction;

import javax.annotation.Nonnull;

public enum RelativeSide implements simplekey{
    FRONT(),
    LEFT(),
    RIGHT(),
    BACK(),
    TOP(),
    BOTTOM();
    private static final RelativeSide[] SIDES = values();
    @Override
    public String getTranslationKey() {
        return null;
    }
    public static RelativeSide fromDirections(@Nonnull Direction facing, @Nonnull Direction side) {
        if (side == facing) {
            return FRONT;
        } else if (side == facing.getOpposite()) {
            return BACK;
        } else if (facing == Direction.DOWN || facing == Direction.UP) {
            if (side == Direction.NORTH) {
                return facing == Direction.DOWN ? TOP : BOTTOM;
            } else if (side == Direction.SOUTH) {
                return facing == Direction.DOWN ? BOTTOM : TOP;
            } else if (side == Direction.WEST) {
                return RIGHT;
            } else if (side == Direction.EAST) {
                return LEFT;
            }
        } else if (side == Direction.DOWN) {
            return BOTTOM;
        } else if (side == Direction.UP) {
            return TOP;
        } else if (side == facing.getCounterClockWise()) {
            return RIGHT;
        } else if (side == facing.getClockWise()) {
            return LEFT;
        }
        //Fall back to front, should never get here
        return FRONT;
    }
}

package com.magneticraft2.common.multiblock.generic;

import net.minecraft.state.BooleanProperty;

public class ConnectedTextureStates {
    public static final BooleanProperty TOP_CONNECTED_PROPERTY = BooleanProperty.create("top_connected");
    public static final BooleanProperty BOTTOM_CONNECTED_PROPERTY = BooleanProperty.create("bottom_connected");
    public static final BooleanProperty NORTH_CONNECTED_PROPERTY = BooleanProperty.create("north_connected");
    public static final BooleanProperty SOUTH_CONNECTED_PROPERTY = BooleanProperty.create("south_connected");
    public static final BooleanProperty EAST_CONNECTED_PROPERTY = BooleanProperty.create("east_connected");
    public static final BooleanProperty WEST_CONNECTED_PROPERTY = BooleanProperty.create("west_connected");

}
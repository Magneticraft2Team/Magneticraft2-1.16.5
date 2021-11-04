package com.magneticraft2.common.multiblock.rectangular;



import com.magneticraft2.common.multiblock.generic.MultiblockTile;
import com.magneticraft2.common.utils.BlockStates;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

import static com.magneticraft2.common.multiblock.rectangular.AxisPosition.*;
import static com.magneticraft2.common.multiblock.rectangular.AxisPosition.Z_AXIS_POSITION;
public abstract class RectangularMultiblockTile<ControllerType extends RectangularMultiblockController<ControllerType, TileType, BlockType>, TileType extends RectangularMultiblockTile<ControllerType, TileType, BlockType>, BlockType extends RectangularMultiblockBlock<ControllerType, TileType, BlockType>> extends MultiblockTile<ControllerType, TileType, BlockType> {

    public RectangularMultiblockTile(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    @Override
    public BlockState assembledBlockState() {
        BlockState state = super.assembledBlockState();
        @SuppressWarnings("unchecked") BlockType block = (BlockType) getBlockState().getBlock();
        if (block.usesAxisPositions()) {
            BlockPos pos = getBlockPos();

            if (pos.getX() == controller.minCoord().x()) {
                state = state.setValue(X_AXIS_POSITION, AxisPosition.LOWER);
            } else if (pos.getX() == controller.maxCoord().x()) {
                state = state.setValue(X_AXIS_POSITION, AxisPosition.UPPER);
            } else {
                state = state.setValue(X_AXIS_POSITION, AxisPosition.MIDDLE);
            }

            if (pos.getY() == controller.minCoord().y()) {
                state = state.setValue(Y_AXIS_POSITION, AxisPosition.LOWER);
            } else if (pos.getY() == controller.maxCoord().y()) {
                state = state.setValue(Y_AXIS_POSITION, AxisPosition.UPPER);
            } else {
                state = state.setValue(Y_AXIS_POSITION, AxisPosition.MIDDLE);
            }

            if (pos.getZ() == controller.minCoord().z()) {
                state = state.setValue(Z_AXIS_POSITION, AxisPosition.LOWER);
            } else if (pos.getZ() == controller.maxCoord().z()) {
                state = state.setValue(Z_AXIS_POSITION, AxisPosition.UPPER);
            } else {
                state = state.setValue(Z_AXIS_POSITION, AxisPosition.MIDDLE);
            }
        }
        if (block.usesFaceDirection()) {
            BlockPos pos = getBlockPos();
            if (pos.getX() == controller.minCoord().x()) {
                state = state.setValue(BlockStates.FACING, Direction.WEST);
            } else if (pos.getX() == controller.maxCoord().x()) {
                state = state.setValue(BlockStates.FACING, Direction.EAST);
            } else if (pos.getY() == controller.minCoord().y()) {
                state = state.setValue(BlockStates.FACING, Direction.DOWN);
            } else if (pos.getY() == controller.maxCoord().y()) {
                state = state.setValue(BlockStates.FACING, Direction.UP);
            } else if (pos.getZ() == controller.minCoord().z()) {
                state = state.setValue(BlockStates.FACING, Direction.NORTH);
            } else if (pos.getZ() == controller.maxCoord().z()) {
                state = state.setValue(BlockStates.FACING, Direction.SOUTH);
            }
        }
        return state;
    }
}
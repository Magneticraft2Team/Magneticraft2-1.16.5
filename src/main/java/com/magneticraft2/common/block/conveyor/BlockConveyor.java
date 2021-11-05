package com.magneticraft2.common.block.conveyor;

import com.magneticraft2.common.tile.conveyor.TileEntityConveyor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.AbstractMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class BlockConveyor extends Block {
    private static final int MAX_CONNECTED_UPDATE = 16;
    //main flat shape
    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);
    //Sub-shapes for angles
    protected static final VoxelShape AG00 = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 0.8D, 16.0D);
    protected static final VoxelShape AG01 = Block.box(1.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);
    protected static final VoxelShape AG02 = Block.box(2.0D, 1.0D, 0.0D, 16.0D, 2.0D, 16.0D);
    protected static final VoxelShape AG03 = Block.box(3.0D, 2.0D, 0.0D, 16.0D, 3.0D, 16.0D);
    protected static final VoxelShape AG04 = Block.box(4.0D, 3.0D, 0.0D, 16.0D, 4.0D, 16.0D);
    protected static final VoxelShape AG05 = Block.box(5.0D, 4.0D, 0.0D, 16.0D, 5.0D, 16.0D);
    protected static final VoxelShape AG06 = Block.box(6.0D, 5.0D, 0.0D, 16.0D, 6.0D, 16.0D);
    protected static final VoxelShape AG07 = Block.box(7.0D, 6.0D, 0.0D, 16.0D, 7.0D, 16.0D);
    protected static final VoxelShape AG08 = Block.box(8.0D, 7.0D, 0.0D, 16.0D, 8.0D, 16.0D);
    protected static final VoxelShape AG09 = Block.box(9.0D, 8.0D, 0.0D, 16.0D, 9.0D, 16.0D);
    protected static final VoxelShape AG10 = Block.box(10.0D, 9.0D, 0.0D, 16.0D, 10.0D, 16.0D);
    protected static final VoxelShape AG11 = Block.box(11.0D, 10.0D, 0.0D, 16.0D, 11.0D, 16.0D);
    protected static final VoxelShape AG12 = Block.box(12.0D, 11.0D, 0.0D, 16.0D, 12.0D, 16.0D);
    protected static final VoxelShape AG13 = Block.box(13.0D, 12.0D, 0.0D, 16.0D, 13.0D, 16.0D);
    protected static final VoxelShape AG14 = Block.box(14.0D, 13.0D, 0.0D, 16.0D, 14.0D, 16.0D);
    protected static final VoxelShape AG15 = Block.box(15.0D, 14.0D, 0.0D, 16.0D, 15.0D, 16.0D);
    protected static final VoxelShape AG16 = Block.box(15.5D, 15.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    //four angled shapes
    protected static final VoxelShape ANGLEEAST = VoxelShapes.or(AG00, AG01, AG02, AG03, AG04, AG05, AG06, AG07, AG08, AG09, AG10, AG11, AG12, AG13, AG14, AG15, AG16);
    protected static final VoxelShape ANGLESOUTH = VoxelShapes.or(rot(AG00), rot(AG01), rot(AG02), rot(AG03), rot(AG04), rot(AG05),
            rot(AG06), rot(AG07), rot(AG08), rot(AG09), rot(AG10), rot(AG11), rot(AG12), rot(AG13), rot(AG14), rot(AG15), rot(AG16));
    protected static final VoxelShape ANGLENORTH = VoxelShapes.or(flipx(AG00), flipx(AG01), flipx(AG02), flipx(AG03), flipx(AG04), flipx(AG05),
            flipx(AG06), flipx(AG07), flipx(AG08), flipx(AG09), flipx(AG10), flipx(AG11), flipx(AG12), flipx(AG13), flipx(AG14), flipx(AG15), flipx(AG16));
    protected static final VoxelShape ANGLEWEST = VoxelShapes.or(flipz(AG00), flipz(AG01), flipz(AG02), flipz(AG03), flipz(AG04), flipz(AG05),
            flipz(AG06), flipz(AG07), flipz(AG08), flipz(AG09), flipz(AG10), flipz(AG11), flipz(AG12), flipz(AG13), flipz(AG14), flipz(AG15), flipz(AG16));



    public static final EnumProperty<ConveyorType> TYPE = EnumProperty.create("type", ConveyorType.class);
    public static final EnumProperty<ConveyorSpeed> SPEED = EnumProperty.create("speed", ConveyorSpeed.class);
    public static final List<AbstractMap.SimpleImmutableEntry<ConveyorType, Direction>> STATE_PAIRS = generateStatePairs();

    public BlockConveyor(Properties properties) {
        super(properties);
    }
    public static VoxelShape rot(final VoxelShape shape) {
        double x1 = shape.min(Direction.Axis.X), x2 = shape.max(Direction.Axis.X);
        final double y1 = shape.min(Direction.Axis.Y), y2 = shape.max(Direction.Axis.Y);
        double z1 = shape.min(Direction.Axis.Z), z2 = shape.max(Direction.Axis.Z);
        //    if (rotationDir == Rotation.CLOCKWISE_90 || rotationDir == Rotation.COUNTERCLOCKWISE_90) {
        double temp = z1; // ]
        z1 = x1; // ] x1 <-> z1
        x1 = temp; // ]
        temp = z2; // ]
        z2 = x2; // ] x2 <-> z2
        x2 = temp; // ]
        //    }
        //    if (rotationDir == Rotation.CLOCKWISE_90 || rotationDir == Rotation.CLOCKWISE_180) {
        x1 = 1 - x1; // clockwise
        x2 = 1 - x2;
        //    }
        //    if (rotationDir == Rotation.COUNTERCLOCKWISE_90 || rotationDir == Rotation.CLOCKWISE_180) {
        //      z1 = 1 - z1; // counterclockwise
        //      z2 = 1 - z2;
        //    }
        return VoxelShapes.box(x1, y1, z1, x2, y2, z2);
    }

    public static VoxelShape flipx(final VoxelShape shape) {
        double x1 = shape.min(Direction.Axis.X);
        double x2 = shape.max(Direction.Axis.X);
        final double y1 = shape.min(Direction.Axis.Y);
        final double y2 = shape.max(Direction.Axis.Y);
        double z1 = shape.min(Direction.Axis.Z);
        double z2 = shape.max(Direction.Axis.Z);
        //flip
        double temp = z1; // ]
        z1 = x1; // ] x1 <-> z1
        x1 = temp; // ]
        temp = z2; // ]
        z2 = x2; // ] x2 <-> z2
        x2 = temp; // ]
        //
        //    if (rotationDir == Rotation.COUNTERCLOCKWISE_90 || rotationDir == Rotation.CLOCKWISE_180) {
        z1 = 1 - z1; // counterclockwise
        z2 = 1 - z2;
        return VoxelShapes.box(x1, y1, z1, x2, y2, z2);
    }

    public static VoxelShape flipz(final VoxelShape shape) {
        double x1 = shape.min(Direction.Axis.X);
        double x2 = shape.max(Direction.Axis.X);
        final double y1 = shape.min(Direction.Axis.Y);
        final double y2 = shape.max(Direction.Axis.Y);
        double z1 = shape.min(Direction.Axis.Z);
        double z2 = shape.max(Direction.Axis.Z);
        //flip
        x1 = 1 - x1; //
        x2 = 1 - x2;
        z1 = 1 - z1; //
        z2 = 1 - z2;
        return VoxelShapes.box(x1, y1, z1, x2, y2, z2);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
        if (state.getValue(TYPE) == ConveyorType.UP) {
            //      Direction facing = state.get(BlockStateProperties.HORIZONTAL_FACING);
            switch (facing) {
                case EAST:
                    return ANGLEEAST;
                case NORTH:
                    return ANGLENORTH;
                case SOUTH:
                    return ANGLESOUTH;
                case WEST:
                    return ANGLEWEST;
                case DOWN:
                case UP:
                    break;
            }
            if (state.getValue(TYPE) == ConveyorType.DOWN) {
                switch (facing) {
                    case EAST:
                        return ANGLEWEST;
                    case NORTH:
                        return ANGLESOUTH;
                    case SOUTH:
                        return ANGLENORTH;
                    case WEST:
                        return ANGLEEAST;
                    case DOWN:
                    case UP:
                        break;
                }
            }
        }
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.HORIZONTAL_FACING).add(SPEED).add(TYPE);
    }

    @Override
    public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        worldIn.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.HORIZONTAL_FACING, placer != null ? placer.getDirection() : Direction.NORTH).setValue(SPEED, ConveyorSpeed.SLOW).setValue(TYPE, ConveyorType.STRAIGHT));
        super.setPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileEntityConveyor();
    }

    public static AbstractMap.SimpleImmutableEntry<ConveyorType, Direction> nextConnectedState(ConveyorType t, Direction d) {
        List<AbstractMap.SimpleImmutableEntry<ConveyorType, Direction>> connectedStates = STATE_PAIRS.stream().filter(pair -> pair.getValue() == d).collect(Collectors.toList());
        AbstractMap.SimpleImmutableEntry<ConveyorType, Direction> pair = new AbstractMap.SimpleImmutableEntry<>(t, d);
        if (connectedStates.contains(pair)) {
            int index = connectedStates.indexOf(pair) + 1;
            return nextState(connectedStates, index);
        }
        return pair;
    }

    private static AbstractMap.SimpleImmutableEntry<ConveyorType, Direction> nextState(List<AbstractMap.SimpleImmutableEntry<ConveyorType, Direction>> list, int index) {
        return list.get(nextIndex(list, index));
    }

    private static int nextIndex(List<AbstractMap.SimpleImmutableEntry<ConveyorType, Direction>> list, int index) {
        return index >= list.size() ? index % list.size() : index;
    }

    public static List<AbstractMap.SimpleImmutableEntry<ConveyorType, Direction>> generateStatePairs() {
        List<AbstractMap.SimpleImmutableEntry<ConveyorType, Direction>> pairs = new LinkedList<>();
        for (ConveyorType t : ConveyorType.values()) {
            for (Direction d : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {
                pairs.add(new AbstractMap.SimpleImmutableEntry<>(t, d));
            }
        }
        return pairs;
    }

}

package com.magneticraft2.common.tile.conveyor;

import com.magneticraft2.common.block.conveyor.BlockConveyor;
import com.magneticraft2.common.block.conveyor.ConveyorType;
import com.magneticraft2.common.magneticraft2;
import com.magneticraft2.common.registry.TileentityRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.List;

public class TileEntityConveyor extends TileEntity implements ITickableTileEntity, IInventory {
    public TileEntityConveyor() {
        super(TileentityRegistry.Tile_Conveyor.get());
    }


    @Override
    public void tick() {
        if (level == null || worldPosition == null) {
            return;
        }

        List<Entity> entities = level.getEntitiesOfClass(Entity.class, new AxisAlignedBB(worldPosition).expandTowards(0.0F, 0.5F, 0.0F));
        for (Entity e : entities) {
            makeEntitiesTravel(e, this.getBlockState(), this.worldPosition, level);

        }
    }
    public static void makeEntitiesTravel(Entity entity, BlockState bs, BlockPos pos, World world) {
        if (entity instanceof PlayerEntity) {
            PlayerEntity p = (PlayerEntity) entity;
            if (p.isCrouching()) {
                return;
            }
        }

        double normalizedX = entity.getX() - pos.getX();
        double normalizedZ = entity.getZ() - pos.getZ();
        final double offside = 0.01D;
        //if the normalized values are >1 or <0, they entity is right at the border so dont apply it now
        Direction facing = bs.getValue(BlockStateProperties.HORIZONTAL_FACING);
        if (facing.getAxis() == Direction.Axis.Z && (normalizedX > 1 - offside || normalizedX < 0 + offside)) {
            return;
        }
        if (facing.getAxis() == Direction.Axis.X && (normalizedZ > 1 - offside || normalizedZ < 0 + offside)) {
            return;
        }
        ConveyorType type = bs.getValue(BlockConveyor.TYPE);
        double heightLimit = (type.isVertical()) ? pos.getY() + 1.3D : pos.getY() + 0.125D;
        double speed = bs.getValue(BlockConveyor.SPEED).getSpeed(); //0.08D; //temp variable, replace with speed from blockstate later
        double xSpeed = 0.0D, zSpeed = 0.0D, ySpeed = 0.0D;
        if (entity.getY() > heightLimit) {
            return;
        }

        xSpeed = facing.getStepX() * speed;
        ySpeed = 0.0D;
        zSpeed = facing.getStepZ() * speed;
        if (type.isCorner()) {
            if (facing.getAxis() == Direction.Axis.Z && (normalizedX < 0.4 || normalizedX > 0.6)) {
                entity.setPos(Math.floor(entity.getX()) + 0.5, entity.getY(), entity.getZ());
            }
            if (facing.getAxis() == Direction.Axis.X && (normalizedZ < 0.4 || normalizedZ > 0.6)) {
                //centralize Z
                entity.setPos(entity.getX(), entity.getY(), Math.floor(entity.getX()) + 0.5);
            }
        }
        if (type.isVertical()) {
            double hackEdge = 0.1;
            if (normalizedX < hackEdge || normalizedZ < hackEdge
                    || normalizedX > 1 - hackEdge || normalizedZ > 1 - hackEdge) {
                magneticraft2.LOGGER.info("jump hacks " + entity);
                entity.setPos(entity.getX(), entity.getY() + .2, entity.getZ());
            }
            ySpeed = speed * 1.3;
            if (type == ConveyorType.DOWN) {
                ySpeed *= -1;
            }
        }
        if (xSpeed != 0.0D || ySpeed != 0.0D || zSpeed != 0.0D) {
            entity.setDeltaMovement(xSpeed, ySpeed, zSpeed);
        }

    }

    public boolean moveItems(Direction myFacingDir, int max, IItemHandler handlerHere) {
        return moveItems(myFacingDir, worldPosition.offset(myFacingDir.getNormal()), max, handlerHere, 0);
    }
    public boolean moveItems(Direction myFacingDir, BlockPos posTarget, int max, IItemHandler handlerHere, int theslot) {
        if (this.level.isClientSide) {
            return false;
        }
        if (handlerHere == null) {
            return false;
        }
        Direction themFacingMe = myFacingDir.getOpposite();
        TileEntity tileTarget = level.getBlockEntity(posTarget);
        if (tileTarget == null) {
            return false;
        }
        IItemHandler handlerOutput = tileTarget.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, themFacingMe).orElse(null);
        if (handlerOutput == null) {
            return false;
        }
        if (handlerHere != null && handlerOutput != null) {
            //first simulate
            ItemStack drain = handlerHere.extractItem(theslot, max, true); // handlerHere.getStackInSlot(theslot).copy();
            int sizeStarted = drain.getCount();
            if (!drain.isEmpty()) {
                //now push it into output, but find out what was ACTUALLY taken
                for (int slot = 0; slot < handlerOutput.getSlots(); slot++) {
                    drain = handlerOutput.insertItem(slot, drain, false);
                    if (drain.isEmpty()) {
                        break; //done draining
                    }
                }
            }
            int sizeAfter = sizeStarted - drain.getCount();
            if (sizeAfter > 0) {
                handlerHere.extractItem(theslot, sizeAfter, false);
            }
            return sizeAfter > 0;
        }
        return false;
    }




    @Override
    public int getContainerSize() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public ItemStack getItem(int p_70301_1_) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItem(int p_70298_1_, int p_70298_2_) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItemNoUpdate(int p_70304_1_) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItem(int p_70299_1_, ItemStack p_70299_2_) {

    }

    @Override
    public boolean stillValid(PlayerEntity p_70300_1_) {
        return false;
    }

    @Override
    public void clearContent() {

    }
}

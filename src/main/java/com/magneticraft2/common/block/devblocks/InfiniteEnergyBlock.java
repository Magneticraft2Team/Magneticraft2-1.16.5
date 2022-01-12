package com.magneticraft2.common.block.devblocks;

import com.magneticraft2.common.block.BlockMagneticraft2;
import com.magneticraft2.common.tile.TileEntityMagneticraft2;
import com.magneticraft2.common.tile.devtiles.InfiniteEnergyTile;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class InfiniteEnergyBlock extends BlockMagneticraft2 {
    public InfiniteEnergyBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new InfiniteEnergyTile();
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult trace) {
        if (world.isClientSide) {
            TileEntity tileEntity = world.getBlockEntity(pos);
            if (tileEntity instanceof TileEntityMagneticraft2){
                player.sendMessage(new StringTextComponent( ((TileEntityMagneticraft2) tileEntity).getEnergyStorage() + " energy stored"), player.getUUID());
            }
        }
        return ActionResultType.CONSUME;

    }
}

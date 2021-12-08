package com.magneticraft2.common.block;

import com.magneticraft2.common.systems.heat.CapabilityHeat;
import com.magneticraft2.common.systems.watt.CapabilityWatt;
import com.magneticraft2.common.tile.TileEntityMagneticraft2;
import com.magneticraft2.common.tile.test;
import com.magneticraft2.compatibility.TOP.TOPDriver;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.Block;
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

public class testBlockfortestTile extends Block implements TOPDriver{
    public testBlockfortestTile(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult trace) {
        if (world.isClientSide) {
            TileEntity tileEntity = world.getBlockEntity(pos);
            if (tileEntity instanceof test){
                player.sendMessage(new StringTextComponent( ((test) tileEntity).getHeatStorage() + " heat stored"), player.getUUID());
            }
        }
        return ActionResultType.CONSUME;

    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new test();
    }


    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, PlayerEntity player, World world, BlockState blockState, IProbeHitData data) {
        TileEntity te = world.getBlockEntity(data.getPos());
        if (te instanceof TileEntityMagneticraft2){
            TileEntityMagneticraft2 tile = (TileEntityMagneticraft2) te;
            te.getCapability(CapabilityHeat.HEAT).ifPresent(h -> {
                probeInfo.horizontal(probeInfo.defaultLayoutStyle()).progress(h.getHeatStored() % 100, 100, probeInfo.defaultProgressStyle().suffix(" H").borderColor(0xFF555555));
            });
            te.getCapability(CapabilityWatt.WATT).ifPresent(h -> {
                probeInfo.horizontal(probeInfo.defaultLayoutStyle()).progress(h.getWattStored() % 100, 100, probeInfo.defaultProgressStyle().suffix(" W").borderColor(0xFF555555));
            });
        }
    }
}

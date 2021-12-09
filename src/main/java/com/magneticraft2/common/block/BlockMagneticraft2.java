package com.magneticraft2.common.block;

import com.magneticraft2.common.systems.heat.CapabilityHeat;
import com.magneticraft2.common.systems.pressure.CapabilityPressure;
import com.magneticraft2.common.systems.watt.CapabilityWatt;
import com.magneticraft2.common.tile.TileEntityMagneticraft2;
import com.magneticraft2.compatibility.TOP.TOPDriver;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class BlockMagneticraft2 extends Block implements TOPDriver {
    public BlockMagneticraft2(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, PlayerEntity player, World world, BlockState blockState, IProbeHitData data) {
        TileEntity te = world.getBlockEntity(data.getPos());
        if (te instanceof TileEntityMagneticraft2){
            TileEntityMagneticraft2 tile = (TileEntityMagneticraft2) te;
            te.getCapability(CapabilityHeat.HEAT).ifPresent(h -> {
                probeInfo.horizontal(probeInfo.defaultLayoutStyle()).progress(h.getHeatStored() % 100, 100, probeInfo.defaultProgressStyle().suffix(" HEAT").borderColor(0xFF555555));
            });
            te.getCapability(CapabilityWatt.WATT).ifPresent(h -> {
                probeInfo.horizontal(probeInfo.defaultLayoutStyle()).progress(h.getWattStored() % 100, 100, probeInfo.defaultProgressStyle().suffix(" WATT").borderColor(0xFF555555));
            });
            te.getCapability(CapabilityPressure.PRESSURE).ifPresent(h -> {
                probeInfo.horizontal(probeInfo.defaultLayoutStyle()).progress(h.getPressureStored() % 100, 100, probeInfo.defaultProgressStyle().suffix(" PSI").borderColor(0xFF555555));
            });
        }
    }
}

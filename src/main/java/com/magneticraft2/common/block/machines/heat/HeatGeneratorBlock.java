package com.magneticraft2.common.block.machines.heat;

import com.magneticraft2.client.gui.container.ContainerHeatGenerator;
import com.magneticraft2.common.block.BlockMagneticraft2;
import com.magneticraft2.common.tile.TileEntityMagneticraft2;
import com.magneticraft2.common.tile.machines.heat.HeatGeneratorTile;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class HeatGeneratorBlock extends BlockMagneticraft2 {
    public HeatGeneratorBlock(Properties properties) {
        super(properties);

    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new HeatGeneratorTile();
    }


    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult trace) {
        if (!world.isClientSide) {
            TileEntity tileEntity = world.getBlockEntity(pos);
            if (tileEntity instanceof TileEntityMagneticraft2) {
                NetworkHooks.openGui((ServerPlayerEntity) player, ((TileEntityMagneticraft2) tileEntity).containerProvider, tileEntity.getBlockPos());
            }
        }
        return ActionResultType.SUCCESS;
    }
}

package com.magneticraft2.common.registry;

import com.magneticraft2.common.tile.conveyor.TileEntityConveyor;
import com.magneticraft2.common.tile.test;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

public class TileentityRegistry {
    static void register() {}
    private static <T extends TileEntity> RegistryObject<TileEntityType<T>> register(String name, Supplier<T> factory, RegistryObject<? extends Block> block) {
        return FinalRegistry.TILE_ENTITIES.register(name, () -> {
            //noinspection ConstantConditions - null in build
            return TileEntityType.Builder.of(factory, block.get()).build(null);
        });
    }

    /**
     * none multiblock tiles
     */
    public static final RegistryObject<TileEntityType<TileEntityConveyor>> Tile_Conveyor = register("conveyor", TileEntityConveyor::new, BlockRegistry.Block_Conveyor);
    public static final RegistryObject<TileEntityType<com.magneticraft2.common.tile.test>> test = register("test", test::new, BlockRegistry.test);


    //This is for IF we make cables (prop never gonna happen but its here)
    public static void registerTileEntities(RegistryEvent.Register<TileEntityType<?>> event) {

    }


    @OnlyIn(Dist.CLIENT)
    public static void clientSetup() {

    }
}

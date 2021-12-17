package com.magneticraft2.common.registry;

import com.magneticraft2.common.block.conveyor.BlockConveyor;
import com.magneticraft2.common.block.devblocks.InfiniteHeatBlock;
import com.magneticraft2.common.block.machines.heat.HeatGeneratorBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

public class BlockRegistry {
    static void register() {}
    private static <T extends Block> RegistryObject<T> registerNoItem(String name, Supplier<T> block) {
        return FinalRegistry.BLOCKS.register(name, block);
    }
    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block) {
        RegistryObject<T> ret = registerNoItem(name, block);
        FinalRegistry.ITEMS.register(name, () -> new BlockItem(ret.get(), new Item.Properties().tab(FinalRegistry.MC2Blocks)));
        return ret;
    }

    /*
     * Dev tools
     */
    public static final RegistryObject<Block> DevT1_Heat_inf = register("infheat", () -> new InfiniteHeatBlock(AbstractBlock.Properties.of(Material.METAL).strength(3.5F).noOcclusion().requiresCorrectToolForDrops()));

    /*
     * None MB blocks
     */
    public static final RegistryObject<Block> Block_Conveyor = register("conveyor", () -> new BlockConveyor(AbstractBlock.Properties.of(Material.METAL).strength(3.5F).noOcclusion().requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> Block_Heat_Generator = register("heat_generator", () -> new HeatGeneratorBlock(AbstractBlock.Properties.of(Material.METAL).strength(3.5F).noOcclusion().requiresCorrectToolForDrops()));
    //public static final RegistryObject<Block> test = register("test", () -> new testBlockfortestTile(AbstractBlock.Properties.of(Material.METAL).strength(3.5F).noOcclusion().requiresCorrectToolForDrops()));


    //Some cable stuff (prop never going to be used)
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll();
    }
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll();
    }
}

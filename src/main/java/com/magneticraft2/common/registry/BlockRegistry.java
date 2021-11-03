package com.magneticraft2.common.registry;

import net.minecraft.block.Block;
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
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll();
    }
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll();
    }
}

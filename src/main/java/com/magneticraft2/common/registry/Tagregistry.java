package com.magneticraft2.common.registry;

import com.magneticraft2.common.magneticraft2;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

public class Tagregistry {

    public static class Blocks {


        private static Tags.IOptionalNamedTag<Block> createTag(String name) {
            return BlockTags.createOptional(new ResourceLocation(magneticraft2.MOD_ID, name));
        }
        private static Tags.IOptionalNamedTag<Block> createForgetag(String name) {
            return BlockTags.createOptional(new ResourceLocation("forge", name));
        }
    }

    public static class Items {


        private static Tags.IOptionalNamedTag<Item> createTag(String name) {
            return ItemTags.createOptional(new ResourceLocation(magneticraft2.MOD_ID, name));
        }
        private static Tags.IOptionalNamedTag<Item> createForgetag(String name) {
            return ItemTags.createOptional(new ResourceLocation("forge", name));
        }
    }
}

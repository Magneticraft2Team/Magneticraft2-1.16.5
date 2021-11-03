package com.magneticraft2.common.registry;

import com.magneticraft2.common.magneticraft2;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ContainerRegistry {
    private static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, magneticraft2.MOD_ID);
    public static void containerRegistry(){
        CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    //Container list bellow

}

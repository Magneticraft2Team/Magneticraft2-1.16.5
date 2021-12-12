package com.magneticraft2.common.registry;

import com.magneticraft2.client.gui.container.ContainerHeatGenerator;
import com.magneticraft2.client.gui.screen.ScreenHeatGenerator;
import com.magneticraft2.common.magneticraft2;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(modid = magneticraft2.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ContainerAndScreenRegistry {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, magneticraft2.MOD_ID);

    public static void containerRegistry() {
        CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<ContainerType<ContainerHeatGenerator>> HEAT_GENERATOR_CONTAINER = CONTAINERS.register("heat_generator", () -> IForgeContainerType.create(((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        World world = inv.player.getCommandSenderWorld();
        return new ContainerHeatGenerator(windowId, world, pos, inv, inv.player);
    })));



    public static void Screen(final FMLClientSetupEvent event) {
        ScreenManager.register(HEAT_GENERATOR_CONTAINER.get(), ScreenHeatGenerator::new);
    }
}

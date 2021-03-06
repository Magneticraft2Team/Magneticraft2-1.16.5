package com.magneticraft2.common;


import com.magneticraft2.common.multiblock.generic.MultiblockController;
import com.magneticraft2.common.multiblock.generic.MultiblockTile;
import com.magneticraft2.common.network.Magneticraft2Network;
import com.magneticraft2.common.registry.*;
import com.magneticraft2.common.systems.heat.CapabilityHeat;
import com.magneticraft2.common.systems.watt.CapabilityWatt;
import com.magneticraft2.common.utils.Magneticraft2ConfigCommon;
import com.magneticraft2.common.world.ore.oreGen;
import com.magneticraft2.compatibility.TOP.TOPCompatibility;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import software.bernie.geckolib3.GeckoLib;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

@Mod(magneticraft2.MOD_ID)
public class magneticraft2 {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final boolean devmode = false;
    private static long tick = 0;
    public static final String MOD_ID = "magneticraft2";


    public magneticraft2(){
        if (devmode) {
            LOGGER.warn("WARNING DEV MODE ACTIVATED");
            LOGGER.info("Please report to author!");
        }
        LOGGER.info("Starting Registry");
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::preinit);
        GeckoLib.initialize();
        FinalRegistry.register();
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Item.class, BlockRegistry::registerItems);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Item.class, ItemRegistry::registerItems);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Block.class, BlockRegistry::registerBlocks);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(TileEntityType.class, TileentityRegistry::registerTileEntities);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ContainerAndScreenRegistry::Screen);
        //FMLJavaModLoadingContext.get().getModEventBus().addListener(TagProviders::gatherData);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetups);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, oreGen::generateOres);
        MinecraftForge.EVENT_BUS.register(this);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(magneticraft2.this::clientSetup);
        });

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Magneticraft2ConfigCommon.SPEC, "magneticraft2-common.toml");
    }

    public void commonSetups(FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new BlockEvents());
        FinalRegistry.init(event);
        Magneticraft2Network.init();
    }
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void clientSetup(final FMLClientSetupEvent event) {

    }

    public void preinit(FMLCommonSetupEvent event){
        CapabilityHeat.register();
        CapabilityWatt.register();
        if (ModList.get().isLoaded("theoneprobe")){
            TOPCompatibility.register();
            LOGGER.info("The one probe compatibility done!");
        }
    }




    //Multiblockhandling DON'T TOUCH

    private static final HashMap<ServerWorld, ArrayList<MultiblockController<?, ?, ?>>> controllersToTick = new HashMap<>();
    private static final HashMap<ServerWorld, ArrayList<MultiblockTile<?, ?, ?>>> tilesToAttach = new HashMap<>();
    private static final ArrayList<MultiblockController<?, ?, ?>> newControllers = new ArrayList<>();
    private static final ArrayList<MultiblockController<?, ?, ?>> oldControllers = new ArrayList<>();
    private static final ArrayList<MultiblockTile<?, ?, ?>> newTiles = new ArrayList<>();
    public static void addController(MultiblockController<?, ?, ?> controller) {
        newControllers.add(controller);
    }
    public static void removeController(MultiblockController<?, ?, ?> controller) {
        oldControllers.add(controller);
    }

    public static void attachTile(MultiblockTile<?, ?, ?> tile) {
        newTiles.add(tile);
    }
    @SubscribeEvent
    void onWorldUnload(final WorldEvent.Unload worldUnloadEvent) {
        if (!worldUnloadEvent.getWorld().isClientSide()) {
            ArrayList<MultiblockController<?, ?, ?>> controllersToTick = magneticraft2.controllersToTick.remove(worldUnloadEvent.getWorld());
            if (controllersToTick != null) {
                for (MultiblockController<?, ?, ?> multiblockController : controllersToTick) {
                    multiblockController.suicide();
                }
            }
            tilesToAttach.remove(worldUnloadEvent.getWorld());
            newControllers.removeIf(multiblockController -> multiblockController.getWorld() == worldUnloadEvent.getWorld());
            oldControllers.removeIf(multiblockController -> multiblockController.getWorld() == worldUnloadEvent.getWorld());
            newTiles.removeIf(multiblockTile -> multiblockTile.getLevel() == worldUnloadEvent.getWorld());
        }
    }
    @SubscribeEvent
    public void advanceTick(TickEvent.ServerTickEvent e) {
        if (!e.side.isServer()) {
            return;
        }
        if (e.phase != TickEvent.Phase.END) {
            return;
        }
        tick++;



        for (MultiblockController<?, ?, ?> newController : newControllers) {
            controllersToTick.computeIfAbsent((ServerWorld) newController.getWorld(), k -> new ArrayList<>()).add(newController);
        }
        newControllers.clear();
        for (MultiblockController<?, ?, ?> oldController : oldControllers) {
            //noinspection SuspiciousMethodCalls
            ArrayList<MultiblockController<?, ?, ?>> controllers = controllersToTick.get(oldController.getWorld());
            controllers.remove(oldController);
        }
        oldControllers.clear();
        for (MultiblockTile<?, ?, ?> newTile : newTiles) {
            tilesToAttach.computeIfAbsent((ServerWorld) newTile.getLevel(), k -> new ArrayList<>()).add(newTile);
        }
        newTiles.clear();
    }
    public static long tickNumber() {
        return tick;
    }
    @SubscribeEvent
    public void tickWorld(TickEvent.WorldTickEvent e) {
        if (!(e.world instanceof ServerWorld)) {
            return;
        }
        if (e.phase != TickEvent.Phase.END) {
            return;
        }

        ArrayList<MultiblockController<?, ?, ?>> controllersToTick = magneticraft2.controllersToTick.get(e.world);
        if (controllersToTick != null) {
            for (MultiblockController<?, ?, ?> controller : controllersToTick) {
                if (controller != null) {
                    controller.update();
                }
            }
        }

        ArrayList<MultiblockTile<?, ?, ?>> tilesToAttach = magneticraft2.tilesToAttach.get(e.world);
        if (tilesToAttach != null) {
            tilesToAttach.sort(Comparator.comparing(TileEntity::getBlockPos));
            for (MultiblockTile<?, ?, ?> toAttach : tilesToAttach) {
                if (toAttach != null) {
                    toAttach.attachToNeighbors();
                }
            }
            tilesToAttach.clear();
        }

    }
}

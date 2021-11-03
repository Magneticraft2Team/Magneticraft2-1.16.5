package com.magneticraft2.common;


import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

@Mod(magneticraft2.MOD_ID)
public class magneticraft2 {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final boolean devmode = false;
    private static long tick = 0;
    public static final String MOD_ID = "magneticraft2";


    public magneticraft2(){
        if (devmode) {
            LOGGER.warn("WARNING DEV MODE ACTIVATED");
            LOGGER.info("Please report to author!");
        }
        LOGGER.info("Starting Registry");
        GeckoLib.initialize();
    }
}

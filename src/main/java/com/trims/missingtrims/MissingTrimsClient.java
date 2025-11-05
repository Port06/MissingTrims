package com.trims.missingtrims;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = MissingTrims.MODID, value = Dist.CLIENT)
public class MissingTrimsClient {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        MissingTrims.LOGGER.info("Missing Trims client setup complete");
    }
}
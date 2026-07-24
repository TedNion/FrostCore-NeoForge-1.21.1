package net.tednion.frostcore.events;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.tednion.frostcore.fluid.FrostCoreFluids;

public class ClientEvents {

    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(FrostCoreFluids.STEAM_SOURCE.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(FrostCoreFluids.STEAM_FLOWING.get(), RenderType.translucent());
        });
    }
}
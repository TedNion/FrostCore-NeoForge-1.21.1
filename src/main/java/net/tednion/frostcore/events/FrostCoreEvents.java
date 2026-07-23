package net.tednion.frostcore.events;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.tednion.frostcore.blockentity.FrostCoreBlockEntityes;

public class FrostCoreEvents {
    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.FluidHandler.BLOCK,
                FrostCoreBlockEntityes.GENERATOR.get(),
                (myBlockEntity, direction) -> myBlockEntity.getFluidHandler(direction)
        );
    }
}
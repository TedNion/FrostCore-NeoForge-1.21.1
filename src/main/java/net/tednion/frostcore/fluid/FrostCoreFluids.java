package net.tednion.frostcore.fluid;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.tednion.frostcore.FrostCore;

public class FrostCoreFluids {
    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, FrostCore.MODID);
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(Registries.FLUID, FrostCore.MODID);

    // Тип жидкости
    public static final DeferredHolder<FluidType, FluidType> STEAM_TYPE =
            FLUID_TYPES.register("steam", () -> new FluidType(FluidType.Properties.create()));

    // Источник (регистрируем, используя метод createProperties())
    public static final DeferredHolder<Fluid, Fluid> STEAM_SOURCE =
            FLUIDS.register("steam", () -> new BaseFlowingFluid.Source(createProperties()));

    // Течение (тоже используем createProperties())
    public static final DeferredHolder<Fluid, Fluid> STEAM_FLOWING =
            FLUIDS.register("steam_flowing", () -> new BaseFlowingFluid.Flowing(createProperties()));

    // Метод, который создаст Properties, когда это потребуется
    private static BaseFlowingFluid.Properties createProperties() {
        return new BaseFlowingFluid.Properties(STEAM_TYPE, STEAM_SOURCE, STEAM_FLOWING);
    }

    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
        FLUIDS.register(eventBus);
    }
}
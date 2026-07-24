package net.tednion.frostcore.fluid;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.tednion.frostcore.FrostCore;

import java.util.function.Consumer;

public class FrostCoreFluids {
    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, FrostCore.MODID);
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(Registries.FLUID, FrostCore.MODID);

    // Тип пара (газ) с текстурами
    public static final DeferredHolder<FluidType, FluidType> STEAM_TYPE =
            FLUID_TYPES.register("steam", () -> new FluidType(FluidType.Properties.create()
                    .density(-100)          // газ
                    .viscosity(0)
                    .temperature(100)
                    .canConvertToSource(false)
            ) {
                @Override
                public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
                    consumer.accept(new IClientFluidTypeExtensions() {
                        @Override
                        public ResourceLocation getStillTexture() {
                            return ResourceLocation.fromNamespaceAndPath(FrostCore.MODID, "block/steam_still");
                        }

                        @Override
                        public ResourceLocation getFlowingTexture() {
                            return ResourceLocation.fromNamespaceAndPath(FrostCore.MODID, "block/steam_flow");
                        }
                    });
                }
            });

    // Источник и течение
    public static final DeferredHolder<Fluid, BaseFlowingFluid> STEAM_SOURCE =
            FLUIDS.register("steam", () -> new SteamSource(createProperties()));
    public static final DeferredHolder<Fluid, BaseFlowingFluid> STEAM_FLOWING =
            FLUIDS.register("steam_flowing", () -> new SteamFlowing(createProperties()));

    private static BaseFlowingFluid.Properties createProperties() {
        return new BaseFlowingFluid.Properties(STEAM_TYPE, STEAM_SOURCE, STEAM_FLOWING);
    }

    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
        FLUIDS.register(eventBus);
    }

    public static class SteamSource extends BaseFlowingFluid.Source {
        public SteamSource(Properties properties) { super(properties); }
        public boolean isGaseous() { return true; }
    }

    public static class SteamFlowing extends BaseFlowingFluid.Flowing {
        public SteamFlowing(Properties properties) { super(properties); }
        public boolean isGaseous() { return true; }
    }
}
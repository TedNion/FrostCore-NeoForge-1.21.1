package net.tednion.frostcore.blockentity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.tednion.frostcore.FrostCore;
import net.tednion.frostcore.block.FrostCoreBlocks;

import java.util.function.Supplier;


public class FrostCoreBlockEntityes {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITYES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, FrostCore.MODID);

    public static final Supplier<BlockEntityType<GeneratorBlockEntity>> GENERATOR = BLOCK_ENTITYES.register("generator",
            () -> BlockEntityType.Builder.of(GeneratorBlockEntity::new,
                    FrostCoreBlocks.TESTED_GENERATOR.get()).build(null));


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITYES.register(eventBus);
    }

}

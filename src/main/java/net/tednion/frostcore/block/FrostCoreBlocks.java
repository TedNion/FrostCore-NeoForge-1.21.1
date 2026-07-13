package net.tednion.frostcore.block;


import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.tednion.frostcore.FrostCore;
import net.tednion.frostcore.item.FrostCoreItems;

import java.util.function.Supplier;

public class FrostCoreBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(FrostCore.MODID);


    public static final DeferredBlock<Block> TESTED_GENERATOR = registerBlock("tested_generator",
            () -> new Block(BlockBehaviour.Properties.of().strength(4f)));

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        RegisterBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void RegisterBlockItem(String name, DeferredBlock<T> block) {
        FrostCoreItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {BLOCKS.register(eventBus);}
}

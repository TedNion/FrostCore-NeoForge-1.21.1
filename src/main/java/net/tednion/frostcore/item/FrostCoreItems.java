package net.tednion.frostcore.item;


import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.tednion.frostcore.FrostCore;

public class FrostCoreItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(FrostCore.MODID);

    public static final DeferredItem<Item> RAW_MEAT = ITEMS.register("raw_meat",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {ITEMS.register(eventBus);}
}

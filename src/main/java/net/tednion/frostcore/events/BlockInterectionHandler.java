package net.tednion.frostcore.events;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.tednion.frostcore.FrostCore;
import net.minecraft.network.chat.Component;
import net.tednion.frostcore.blockentity.GeneratorBlockEntity;

@EventBusSubscriber(modid = FrostCore.MODID)
public class BlockInterectionHandler {
    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (event.getLevel() == null || event.getLevel().isClientSide()) {
            return;
        }

        BlockEntity blockEntity = event.getLevel().getBlockEntity(event.getPos());
        if (!(blockEntity instanceof GeneratorBlockEntity generator)) {
            return;
        }

        // Проверка пустой рукой — вывод состояния
        if (event.getItemStack().isEmpty()) {
            if (!event.getLevel().isClientSide()) {
                BlockEntity be = event.getLevel().getBlockEntity(event.getPos());
                if (be instanceof GeneratorBlockEntity) {
                    net.tednion.frostcore.blockentity.GeneratorBlockEntity generatorBlock = (GeneratorBlockEntity) be;
                    int steamAmount = generator.getSteamTank().getFluidAmount();
                    int fuel = generator.getFuelBurnTime();
                    float temp = generator.getTemperature();
                    event.getEntity().sendSystemMessage(
                            net.minecraft.network.chat.Component.literal(
                                    "Пар: " + steamAmount + " мБ | Топливо: " + fuel + " тиков | Температура: " + String.format("%.1f", temp) + "%"
                            )
                    );
                }
            }
            event.setCancellationResult(InteractionResult.SUCCESS);
            event.setCanceled(true);
            return;
        }

        if (event.getItemStack().getItem() == Items.COAL) {
            if (generator.addFuel(200)) {
                if (!event.getEntity().isCreative()) {
                    event.getItemStack().shrink(1);
                }
                event.setCancellationResult(InteractionResult.SUCCESS);
                event.setCanceled(true);
            }
        }
    }
}

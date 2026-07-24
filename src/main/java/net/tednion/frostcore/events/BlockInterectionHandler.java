package net.tednion.frostcore.events;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.tednion.frostcore.FrostCore;
import net.tednion.frostcore.blockentity.GeneratorBlockEntity;

@EventBusSubscriber(modid = FrostCore.MODID)
public class BlockInterectionHandler {

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        // Пропускаем клиент и обработку второй руки (OFF_HAND)
        if (event.getLevel().isClientSide() || event.getHand() != InteractionHand.MAIN_HAND) {
            return;
        }

        BlockEntity blockEntity = event.getLevel().getBlockEntity(event.getPos());
        if (!(blockEntity instanceof GeneratorBlockEntity generator)) {
            return;
        }

        // Проверка пустой рукой — вывод состояния
        if (event.getItemStack().isEmpty()) {
            int steamAmount = generator.getSteamTank().getFluidAmount();
            int fuel = generator.getFuelBurnTime();
            float temp = generator.getTemperature();

            event.getEntity().sendSystemMessage(
                    Component.literal(
                            "Пар: " + steamAmount + " мБ | Топливо: " + fuel + " тиков | Температура: " + String.format("%.1f", temp) + "%"
                    )
            );

            event.setCancellationResult(InteractionResult.SUCCESS);
            event.setCanceled(true);
            return;
        }

        // Заправка углем
        if (event.getItemStack().is(Items.COAL)) {
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
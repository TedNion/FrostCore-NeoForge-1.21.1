package net.tednion.frostcore.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.tednion.frostcore.blockentity.FrostCoreBlockEntityes;
import net.tednion.frostcore.blockentity.GeneratorBlockEntity;
import org.jetbrains.annotations.Nullable;

public class GeneratorBlock extends Block implements EntityBlock {
    public GeneratorBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new GeneratorBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        // Выполняем tick() ТОЛЬКО на сервере
        if (!level.isClientSide() && type == FrostCoreBlockEntityes.GENERATOR.get()) {
            return (lvl, pos, st, be) -> ((GeneratorBlockEntity) be).tick();
        }
        return null;
    }
}
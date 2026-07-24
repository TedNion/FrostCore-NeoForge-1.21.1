package net.tednion.frostcore.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.tednion.frostcore.fluid.FrostCoreFluids;

public class GeneratorBlockEntity extends BlockEntity {

    private final FluidTank waterTank = new FluidTank(10000) {
        @Override
        protected void onContentsChanged() { setChanged(); }
    };

    private final FluidTank steamTank = new FluidTank(10000) {
        @Override
        protected void onContentsChanged() { setChanged(); }
    };

    private int fuelBurnTime = 0;
    private float temperature = 0f;

    // Кэшируем обработчики для труб, чтобы Create не сходил с ума
    private final IFluidHandler downHandler = new IFluidHandler() {
        @Override public int getTanks() { return 1; }
        @Override public FluidStack getFluidInTank(int tank) { return waterTank.getFluidInTank(0); }
        @Override public int getTankCapacity(int tank) { return waterTank.getTankCapacity(0); }
        @Override public boolean isFluidValid(int tank, FluidStack stack) { return stack.getFluid().isSame(Fluids.WATER); }
        @Override public int fill(FluidStack resource, IFluidHandler.FluidAction action) {
            if (!isFluidValid(0, resource)) return 0;
            return waterTank.fill(resource, action);
        }
        @Override public FluidStack drain(FluidStack resource, IFluidHandler.FluidAction action) { return FluidStack.EMPTY; }
        @Override public FluidStack drain(int maxDrain, IFluidHandler.FluidAction action) { return FluidStack.EMPTY; }
    };

    private final IFluidHandler upHandler = new IFluidHandler() {
        @Override public int getTanks() { return 1; }
        @Override public FluidStack getFluidInTank(int tank) { return steamTank.getFluidInTank(0); }
        @Override public int getTankCapacity(int tank) { return steamTank.getTankCapacity(0); }
        @Override public boolean isFluidValid(int tank, FluidStack stack) { return false; } // Только выход
        @Override public int fill(FluidStack resource, IFluidHandler.FluidAction action) { return 0; }
        @Override public FluidStack drain(FluidStack resource, IFluidHandler.FluidAction action) {
            if (resource == null || resource.isEmpty()) return FluidStack.EMPTY;
            if (resource.getFluid().isSame(FrostCoreFluids.STEAM_SOURCE.get())) {
                // Лучше передавать сам ресурс для надежности
                return steamTank.drain(resource, action);
            }
            return FluidStack.EMPTY;
        }
        @Override public FluidStack drain(int maxDrain, IFluidHandler.FluidAction action) {
            return steamTank.drain(maxDrain, action);
        }
    };

    public GeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(FrostCoreBlockEntityes.GENERATOR.get(), pos, state);
    }

    public void tick() {
        if (fuelBurnTime > 0 && waterTank.getFluidAmount() > 0) {
            waterTank.drain(1, IFluidHandler.FluidAction.EXECUTE);
            temperature = Math.min(100f, temperature + 0.5f);
            fuelBurnTime--;

            int steamProduced = (int) (temperature * 0.2f);

            // ИСПРАВЛЕНИЕ: Создаем FluidStack только если объем больше 0
            if (steamProduced > 0) {
                FluidStack steam = new FluidStack(FrostCoreFluids.STEAM_SOURCE.get(), steamProduced);
                steamTank.fill(steam, IFluidHandler.FluidAction.EXECUTE);
            }
        } else {
            temperature = Math.max(0f, temperature - 0.1f);
        }

        setChanged();
    }

    public boolean addFuel(int burnTime) {
        if (burnTime <= 0) return false;
        if (burnTime >= 5000) return false;
        fuelBurnTime += burnTime;
        setChanged();
        return true;
    }

    public float getTemperature() { return temperature; }
    public int getFuelBurnTime() { return fuelBurnTime; }
    public FluidTank getWaterTank() { return waterTank; }
    public FluidTank getSteamTank() { return steamTank; }

    // Возвращаем кэшированные инстансы
    public IFluidHandler getFluidHandler(Direction direction) {
        if (direction == Direction.DOWN) {
            return downHandler;
        } else if (direction == Direction.UP) {
            return upHandler;
        }
        return null;
    }
}
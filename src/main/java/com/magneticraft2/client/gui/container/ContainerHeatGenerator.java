package com.magneticraft2.client.gui.container;

import com.magneticraft2.common.registry.BlockRegistry;
import com.magneticraft2.common.registry.ContainerAndScreenRegistry;
import com.magneticraft2.common.systems.heat.CapabilityHeat;
import com.magneticraft2.common.systems.heat.HeatStorage;
import com.magneticraft2.common.systems.heat.IHeatStorage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nullable;

public class ContainerHeatGenerator extends Container {

    private TileEntity tileEntity;
    private PlayerEntity playerEntity;
    private IHeatStorage heatStorage;
    private IEnergyStorage energyStorage;
    private IItemHandler playerInventory;


    public ContainerHeatGenerator(int windowid, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player) {
        super(ContainerAndScreenRegistry.HEAT_GENERATOR_CONTAINER.get(), windowid);
        tileEntity = world.getBlockEntity(pos);
        this.playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);
        layoutPlayerInventorySlots(12, 96);
    }

    @Override
    public boolean stillValid(PlayerEntity p_75145_1_) {
        return stillValid(IWorldPosCallable.create(tileEntity.getLevel(), tileEntity.getBlockPos()), playerEntity, BlockRegistry.Block_Heat_Generator.get());
    }


    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0 ; i < amount ; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0 ; j < verAmount ; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    private void layoutPlayerInventorySlots(int leftCol, int topRow){
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);
        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }
    public int getEnergy() {
        return tileEntity.getCapability(CapabilityEnergy.ENERGY).map(IEnergyStorage::getEnergyStored).orElse(0);
    }
    public int getEnergylimit(){
        return tileEntity.getCapability(CapabilityEnergy.ENERGY).map(IEnergyStorage::getMaxEnergyStored).orElse(0);
    }
    public TileEntity getTileEntity(){
        return tileEntity;
    }

    public int getHeat(){
        return tileEntity.getCapability(CapabilityHeat.HEAT).map(IHeatStorage::getHeatStored).orElse(0);
    }
    public int getHeatLimit(){
        return tileEntity.getCapability(CapabilityHeat.HEAT).map(IHeatStorage::getMaxHeatStored).orElse(0);
    }
}

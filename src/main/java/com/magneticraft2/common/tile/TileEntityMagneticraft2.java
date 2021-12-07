package com.magneticraft2.common.tile;

import com.magneticraft2.common.systems.heat.CapabilityHeat;
import com.magneticraft2.common.systems.heat.IHeatStorage;
import com.magneticraft2.common.systems.watt.CapabilityWatt;
import com.magneticraft2.common.systems.watt.IWattStorage;
import com.magneticraft2.common.utils.EnergyStorages;
import com.magneticraft2.common.utils.FluidStorages;
import com.magneticraft2.common.utils.HeatStorages;
import com.magneticraft2.common.utils.WattStorages;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public abstract class TileEntityMagneticraft2 extends TileEntity implements ITickableTileEntity, IAnimatable{
    private static final Logger LOGGER = LogManager.getLogger();
    /* Energy */
    private static Integer capacity;
    private static Integer maxtransfer;
    /* Heat */
    private static Integer capacityH;
    private static Integer maxtransferH;
    /* Watt */
    private static Integer capacityW;
    private static Integer maxtransferW;
    /* Fluid */
    private static Integer capacityF;
    private static Integer tanks;
    /* Inv */
    private static Integer invsize;
    private EnergyStorages energyStorage = createEnergy();
    private ItemStackHandler itemHandler = createHandler();
    private HeatStorages heatHandler = createHeat();
    private WattStorages wattHandler = createWatt();
    private FluidStorages fluidHandler = createFluid();
    static boolean itemcap;
    static boolean energycap;
    static boolean heatcape;
    static boolean wattcape;
    static boolean fluidcape;
    final AnimationFactory factory = new AnimationFactory(this);
    private LazyOptional<IEnergyStorage> energy = LazyOptional.of(() -> energyStorage);
    private LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
    private LazyOptional<IHeatStorage> heat = LazyOptional.of(() -> heatHandler);
    private LazyOptional<IWattStorage> watt = LazyOptional.of(() -> wattHandler);
    private LazyOptional<IFluidHandler> fluid = LazyOptional.of(() -> fluidHandler);
    public TileEntityMagneticraft2(TileEntityType<?> p_i48289_1_) {
        super(p_i48289_1_);
    }
    void shouldHaveCapability(boolean itemcaps, boolean energycaps, boolean heatcaps, boolean wattcapes, boolean fluidcapes){
        itemcap = itemcaps;
        energycap = energycaps;
        heatcape = heatcaps;
        wattcape = wattcapes;
        fluidcape = fluidcapes;
    }
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (itemcap) {
                return handler.cast();
            }
        }
        if (cap == CapabilityEnergy.ENERGY) {
            if (energycap) {
                return energy.cast();
            }
        }
        if (cap == CapabilityHeat.HEAT) {
            if (heatcape) {
                return heat.cast();
            }
        }
        if (cap == CapabilityWatt.WATT) {
            if (wattcape) {
                return watt.cast();
            }
        }
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            if (fluidcape) {
                return fluid.cast();
            }
        }
        return super.getCapability(cap, side);
    }



    /*
     *Energy, Item, Fluid & Heat handler
     */
    void setCapacity(int cap){
        capacity = cap;
    }
    void setMaxtransfer(int max){
        maxtransfer = max;
    }
    void setInvsize(int size){
        invsize = size;
    }
    void setHeatCapacity(int cap) {
        capacityH = cap;
    }
    void setMaxHeattransfer(int cap){
        maxtransferH = cap;
    }
    void setWattCapacity(int cap){
        capacityW = cap;
    }
    void setMaxWatttransfer(int cap){
        maxtransferW = cap;
    }
    void setFluidCapacity(int cap) {
        capacityF = cap;
    }
    void setFluidTanks(int cap) {
        tanks = cap;
    }

    private FluidStorages createFluid(){
        if (fluidcape) {
            return new FluidStorages() {
                @Override
                public void setTanks(int tanknumb) {
                    super.setTanks(tanks);
                }

                @Override
                public void setCapacity(int capacity) {
                    super.setCapacity(capacityF);
                }
            };
        }else {
            return null;
        }
    }
    private WattStorages createWatt(){
        if (wattcape) {
            return new WattStorages(capacityW, maxtransferW){
                @Override
                protected void onWattChanged() {
                    super.onWattChanged();
                }
            };
        }else {
            return null;
        }
    }
    private HeatStorages createHeat(){
        if (heatcape) {
            return new HeatStorages(capacityH, maxtransferH){
                @Override
                protected void onHeatChanged() {
                    setChanged();
                }
            };
        }else {
            return null;
        }
    }
    private EnergyStorages createEnergy() {
        if (energycap) {
            return new EnergyStorages(capacity, maxtransfer) {
                @Override
                protected void onEnergyChanged() {
                    setChanged();
                }
            };
        }else{
            return null;
        }
    }
    private ItemStackHandler createHandler() {
        if (energycap) {
            return new ItemStackHandler(invsize) {
                @Override
                protected void onContentsChanged(int slot) {
                    setChanged();
                }

                @Override
                public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                    return super.isItemValid(slot, stack);
                }

                @Nonnull
                @Override
                public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                    return super.insertItem(slot, stack, simulate);
                }
            };
        }else{
            return null;
        }
    }



    /*
     * Saving and loading NBT data
     */

    @Override
    public CompoundNBT save(CompoundNBT tag) {
        if (itemcap) {
            itemHandler.deserializeNBT(tag.getCompound("inv"));
        }
        if (energycap) {
            energyStorage.deserializeNBT(tag.getCompound("energy"));
        }
        if (heatcape) {
            heatHandler.deserializeNBT(tag.getCompound("heat"));
        }
        if (wattcape) {
            wattHandler.deserializeNBT(tag.getCompound("watt"));
        }
        if (fluidcape) {
            fluidHandler.deserializeNBT(tag.getCompound("fluidamount"));
            fluidHandler.deserializeNBT(tag.getCompound("fluidtype"));
        }
        return super.save(tag);
    }

    @Override
    public void load(BlockState state, CompoundNBT tag) {
        if (itemcap) {
            tag.put("inv", itemHandler.serializeNBT());
        }
        if (energycap) {
            tag.put("energy", energyStorage.serializeNBT());
        }
        if (heatcape) {
            tag.put("heat", heatHandler.serializeNBT());
        }
        if (wattcape) {
            tag.put("wat", wattHandler.serializeNBT());
        }
        if (fluidcape) {
            tag.put("fluidamount", fluidHandler.serializeNBT());
            tag.put("fluidtype", fluidHandler.serializeNBT());
        }
        super.load(state, tag);
    }

    /*
     * Get the dif types of capabilities storage and max storage.
     */

    /* Heat */
    public int getHeatStorage(){
        return this.heatHandler.getHeatStored();
    }
    public int getMaxHeatStorage(){
        return this.heatHandler.getMaxHeatStored();
    }

    /* Watt */
    public int getWattStorage(){
        return this.wattHandler.getWattStored();
    }
    public int getMaxWattStorage(){
        return this.wattHandler.getMaxWattStored();
    }

    /* Energy */
    public int getEnergyStorage(){
        return this.energyStorage.getEnergyStored();
    }
    public int getMaxEnergyStorage(){
        return this.energyStorage.getMaxEnergyStored();
    }

    /* Item */
    public int getInvSize(){
        return this.itemHandler.getSlots();
    }
    public ItemStack getItemInSlot(int size) {
        return this.itemHandler.getStackInSlot(size);
    }

    /* Fluid */
    public int getFluidTanks(){
        return this.fluidHandler.getTanks();
    }
    public int getFluidCapacity(){
        return 0;
    }

    /* end */
}

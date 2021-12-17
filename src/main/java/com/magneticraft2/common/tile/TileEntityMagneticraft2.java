package com.magneticraft2.common.tile;

import com.magneticraft2.common.systems.heat.CapabilityHeat;
import com.magneticraft2.common.systems.heat.IHeatStorage;
import com.magneticraft2.common.systems.pressure.CapabilityPressure;
import com.magneticraft2.common.systems.pressure.IPressureStorage;
import com.magneticraft2.common.systems.pressure.PressureStorage;
import com.magneticraft2.common.systems.watt.CapabilityWatt;
import com.magneticraft2.common.systems.watt.IWattStorage;
import com.magneticraft2.common.utils.*;
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


public abstract class TileEntityMagneticraft2 extends TileEntity implements ITickableTileEntity, IAnimatable{
    public static final Logger LOGGER = LogManager.getLogger();
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
    /* Pressure */
    private static Integer capacityP;
    private static Integer maxtransferP;



    /* Create handlers */
    private final EnergyStorages energyHandler = createEnergy(); //Energy (RF)
    private ItemStackHandler itemHandler = createInv(); //Item
    private HeatStorages heatHandler = createHeat(); //Heat
    private WattStorages wattHandler = createWatt(); //Watt
    private FluidStorages fluidHandler = createFluid(); //Fluid
    private PressureStorages pressureHandler = createPressure(); //Pressure

    static boolean itemcape; //Is the TileEntity capable of Item handling
    static boolean energycape; //Is the TileEntity capable of Energy (RF) handling
    static boolean heatcape; //Is the TileEntity capable of Heat handling
    static boolean wattcape; //Is the TileEntity capable of Wattage/Voltage handling
    static boolean fluidcape; //Is the TileEntity capable of Fluid handling
    static boolean pressurecape; //Is the TileEntity capable of Pressure handling

    static boolean HeatCanReceive;
    static boolean HeatCanSend;
    static boolean WattCanReceive;
    static boolean WattCanSend;
    static boolean EnergyCanReceive;
    static boolean EnergyCanSend;
    static boolean PressureCanReceive;
    static boolean PressureCanSend;

    public final AnimationFactory factory = new AnimationFactory(this);
    private LazyOptional<IEnergyStorage> energy = LazyOptional.of(() -> energyHandler); //Creating LazyOptional for Energy (RF)
    private LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler); //Creating LazyOptional for Item
    private LazyOptional<IHeatStorage> heat = LazyOptional.of(() -> heatHandler); //Creating LazyOptional for Heat
    private LazyOptional<IWattStorage> watt = LazyOptional.of(() -> wattHandler); //Creating LazyOptional for Wattage/Voltage
    private LazyOptional<IFluidHandler> fluid = LazyOptional.of(() -> fluidHandler); //Creating LazyOptional for Fluid
    private LazyOptional<IPressureStorage> pressure = LazyOptional.of(() -> pressureHandler); //Creating LazyOptional for Pressure
    public TileEntityMagneticraft2(TileEntityType<?> p_i48289_1_) {
        super(p_i48289_1_);
    }
    public void shouldHaveCapability(boolean itemcaps, boolean energycaps, boolean heatcaps, boolean wattcapes, boolean fluidcapes, boolean pressurecapes){
        itemcape = itemcaps;
        energycape = energycaps;
        heatcape = heatcaps;
        wattcape = wattcapes;
        fluidcape = fluidcapes;
        pressurecape = pressurecapes;
    }
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (itemcape) {
            if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
                return handler.cast();
            }
        }
        if (energycape) {
            if (cap == CapabilityEnergy.ENERGY) {
                return energy.cast();
            }
        }
        if (heatcape) {
            if (cap == CapabilityHeat.HEAT) {
                return heat.cast();
            }
        }
        if (wattcape) {
            if (cap == CapabilityWatt.WATT) {
                return watt.cast();
            }
        }
        if (fluidcape) {
            if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
                return fluid.cast();
            }
        }
        if (pressurecape) {
            if (cap == CapabilityPressure.PRESSURE) {
                return pressure.cast();
            }
        }
        return super.getCapability(cap, side);
    }



    /*
     *Energy, Item, Fluid, Pressure & Heat handler
     */
    void setCapacity(int cap) {
        capacity = cap;
    }
    void setMaxtransfer(int max) {
        maxtransfer = max;
    }
    void setInvsize(int size) {
        invsize = size;
    }
    void setHeatCapacity(int cap) {
        capacityH = cap;
    }
    void setMaxHeattransfer(int cap) {
        maxtransferH = cap;
    }
    void setWattCapacity(int cap) {
        capacityW = cap;
    }
    void setMaxWatttransfer(int cap) {
        maxtransferW = cap;
    }
    void setFluidCapacity(int cap) {
        capacityF = cap;
    }
    void setFluidTanks(int cap) {
        tanks = cap;
    }
    void setPressureCapacity(int cap) {
        capacityP = cap;
    }
    void setMaxPressuretransfer(int cap) {
        maxtransferP = cap;
    }

    void setEnergyCanReceive(boolean can) { EnergyCanReceive = can; }
    void setEnergyCanSend(boolean can) { EnergyCanSend = can; }
    void setWattCanReceive(boolean can) { WattCanReceive = can; }
    void setWattCanSend(boolean can) { WattCanSend = can; }
    void setHeatCanReceive(boolean can) { HeatCanReceive = can; }
    void setHeatCanSend(boolean can) { HeatCanSend = can; }
    void setPressureCanReceive(boolean can) { PressureCanReceive = can; }
    void setPressureCanSend(boolean can) { PressureCanSend = can; }

    public void setTransferAndCapacity(int EnergyCapacity, int MaxEnergyTransfer, int InventorySize, int HeatCapacity, int MaxHeatTransfer, int WattCapacity, int MaxWattTransfer, int FluidCapacity, int FluidTanks, int PressureCapacity, int MaxPressureTransfer){
        setCapacity(EnergyCapacity);
        setMaxtransfer(MaxEnergyTransfer);
        setInvsize(InventorySize);
        setHeatCapacity(HeatCapacity);
        setMaxHeattransfer(MaxHeatTransfer);
        setWattCapacity(WattCapacity);
        setMaxWatttransfer(MaxWattTransfer);
        setFluidCapacity(FluidCapacity);
        setFluidTanks(FluidTanks);
        setPressureCapacity(PressureCapacity);
        setMaxPressuretransfer(MaxPressureTransfer);
    }
    public void setReceiveAndOrSend(boolean EnergyReceive, boolean EnergySend, boolean WattReceive, boolean WattSend, boolean HeatReceive, boolean HeatSend, boolean PressureReceive, boolean PressureSend) {
        setEnergyCanReceive(EnergyReceive);
        setEnergyCanSend(EnergySend);
        setWattCanReceive(WattReceive);
        setWattCanSend(WattSend);
        setHeatCanReceive(HeatReceive);
        setHeatCanSend(HeatSend);
        setPressureCanReceive(PressureReceive);
        setPressureCanSend(PressureSend);
    }
    public void setHeatHeat(int heat) {
        createHeat().setHeat(heat);
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
                    setChanged();
                }

                @Override
                public boolean canReceive() {
                    return WattCanReceive;
                }

                @Override
                public boolean canSend() {
                    return WattCanSend;
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

                @Override
                public boolean canReceive() {
                    return HeatCanReceive;
                }

                @Override
                public boolean canSend() {
                    return HeatCanSend;
                }

                @Override
                public void setHeat(int heat) {
                    super.setHeat(heat);
                }
            };
        }else {
            return null;
        }
    }
    private EnergyStorages createEnergy() {
        if (energycape) {
            return new EnergyStorages(capacity, maxtransfer) {
                @Override
                protected void onEnergyChanged() {
                    setChanged();
                }

                @Override
                public boolean canReceive() {
                    return EnergyCanReceive;
                }

                @Override
                public boolean canExtract() {
                    return EnergyCanSend;
                }

            };
        }else{
            return null;
        }
    }
    private PressureStorages createPressure() {
        if (pressurecape) {
            return new PressureStorages(capacityP, maxtransferP) {
                @Override
                protected void onPressureChanged(){
                    setChanged();
                }

                @Override
                public boolean canReceive() {
                    return PressureCanReceive;
                }

                @Override
                public boolean canSend() {
                    return PressureCanSend;
                }
            };
        }else {
            return null;
        }
    }

    private ItemStackHandler createInv() {
        if (energycape) {
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
        if (itemcape) {
            //itemHandler.deserializeNBT(tag.getCompound("inv"));
            tag.put("inv", itemHandler.serializeNBT());
        }
        if (energycape) {
            //energyHandler.deserializeNBT(tag.getCompound("energy"));
            tag.put("energy", energyHandler.serializeNBT());
        }
        if (heatcape) {
            //heatHandler.deserializeNBT(tag.getCompound("heat"));
            tag.put("heat", heatHandler.serializeNBT());
        }
        if (wattcape) {
            //wattHandler.deserializeNBT(tag.getCompound("watt"));
            tag.put("watt", wattHandler.serializeNBT());
        }
        if (pressurecape) {
            //pressureHandler.deserializeNBT(tag.getCompound("pressure"));
            tag.put("pressure", pressureHandler.serializeNBT());
        }
        if (fluidcape) {
            //fluidHandler.deserializeNBT(tag.getCompound("fluidamount"));
            //fluidHandler.deserializeNBT(tag.getCompound("fluidtype"));
            tag.put("fluidamount", fluidHandler.serializeNBT());
            tag.put("fluidtype", fluidHandler.serializeNBT());
        }
        return super.save(tag);
    }

    @Override
    public void load(BlockState state, CompoundNBT tag) {
        if (itemcape) {
//            tag.put("inv", itemHandler.serializeNBT());
            itemHandler.deserializeNBT(tag.getCompound("inv"));
        }
        if (energycape) {
//            tag.put("energy", energyHandler.serializeNBT());
            energyHandler.deserializeNBT(tag.getCompound("energy"));
        }
        if (heatcape) {
//            tag.put("heat", heatHandler.serializeNBT());
            heatHandler.deserializeNBT(tag.getCompound("heat"));
        }
        if (wattcape) {
//            tag.put("watt", wattHandler.serializeNBT());
            wattHandler.deserializeNBT(tag.getCompound("watt"));
        }
        if (pressurecape) {
//            tag.put("pressure", pressureHandler.serializeNBT());
            pressureHandler.deserializeNBT(tag.getCompound("pressure"));
        }
        if (fluidcape) {
//            tag.put("fluidamount", fluidHandler.serializeNBT());
//            tag.put("fluidtype", fluidHandler.serializeNBT());
            fluidHandler.deserializeNBT(tag.getCompound("fluidamount"));
            fluidHandler.deserializeNBT(tag.getCompound("fluidtype"));
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
    public void addHeatToStorage(int heat){
        this.heatHandler.addHeat(heat);
    }
    public void setHeatStorage(int heat) {
        this.heatHandler.setHeat(heat);
    }
    public void removeHeatFromStorage(int heat) {
        this.heatHandler.consumeHeat(heat);
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
        return this.energyHandler.getEnergyStored();
    }
    public int getMaxEnergyStorage(){
        return this.energyHandler.getMaxEnergyStored();
    }

    public void removeEnergyFromStorage(int energy) {
        this.energyHandler.consumeEnergy(energy);
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

    /* Pressure */

    public int getPressureStorage(){
        return this.pressureHandler.getPressureStored();
    }
    public int getMaxPressureStorage(){
        return this.pressureHandler.getMaxPressureStored();
    }

    /* end */


    /*
     * Used for TOP (TheOneProbe)
     */

    public boolean getHeatCap(){
        return heatcape;
    }
    public boolean getWattCap(){
        return wattcape;
    }

    public boolean getPressureCap(){
        return pressurecape;
    }

}

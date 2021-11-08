package com.magneticraft2.common.tile;

import com.magneticraft2.common.systems.heating.HeatCapacitorHandler;
import com.magneticraft2.common.systems.heating.IHeatCapacitorHolder;
import com.magneticraft2.common.systems.heating.ITileHeatHandler;
import com.magneticraft2.common.utils.EnergyStorage;
import com.magneticraft2.common.utils.HeatCapacitorHelper;
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
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class TileEntityMagneticraft2 extends TileEntity implements ITileHeatHandler, ITickableTileEntity, IAnimatable{
    private static final Logger LOGGER = LogManager.getLogger();
    private static Integer capacity;
    private static Integer maxtransfer;
    private static Integer invsize;
    private EnergyStorage energyStorage = createEnergy();
    private ItemStackHandler itemHandler = createHandler();
    private static boolean itemcap;
    private static boolean energycap;
    private static boolean heatcape;
    private static Integer HeatCapacity;
    private static Integer ConductionEff;
    private static Integer InsulationEff;
    private boolean isDirectional;
    HeatCapacitorHandler heatCapacitor;
    final AnimationFactory factory = new AnimationFactory(this);
    private LazyOptional<IEnergyStorage> energy = LazyOptional.of(() -> energyStorage);
    private LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    public TileEntityMagneticraft2(TileEntityType<?> p_i48289_1_) {
        super(p_i48289_1_);
    }
    void shouldHaveCapability(boolean itemcaps, boolean energycaps, boolean heatcaps){
        itemcap = itemcaps;
        energycap = energycaps;
        heatcape = heatcaps;
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
        return super.getCapability(cap, side);
    }



    /*
     *Energy, Item & Heat handler
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
    void setHeatCapacity(int cap){ HeatCapacity = cap; }
    void setConductionEff(int eff){ ConductionEff = eff; }
    void setInsulationEff(int eff){ InsulationEff = eff; }

    private EnergyStorage createEnergy() {
        return new EnergyStorage(capacity, maxtransfer){
            @Override
            protected void onEnergyChanged() {
                setChanged();
            }
        };
    }
    private ItemStackHandler createHandler() {
        return new ItemStackHandler(invsize){
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
    }
    @Nullable
    private Direction cachedDirection;
    @Nullable
    protected IHeatCapacitorHolder getInitialHeatCapacitors() {
        HeatCapacitorHelper builder = HeatCapacitorHelper.forSide(this::getDirection);
        builder.addCapacitor(heatCapacitor = HeatCapacitorHandler.create(HeatCapacity, ConductionEff, InsulationEff, this));
        return builder.build();
    }
    @Nonnull
    public final Direction getDirection() {
        if (isDirectional) {
            if (cachedDirection != null) {
                return cachedDirection;
            }
            BlockState state = getBlockState();
            if (cachedDirection != null) {
                return cachedDirection;
            } else if (!getType().isValid(state.getBlock())) {
                //dosomething at some point
            }
        }
        return Direction.NORTH;
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
        super.load(state, tag);
    }
}

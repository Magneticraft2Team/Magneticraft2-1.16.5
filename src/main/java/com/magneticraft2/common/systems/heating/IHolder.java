package com.magneticraft2.common.systems.heating;
import javax.annotation.Nullable;
import net.minecraft.util.Direction;
public interface IHolder {
    default boolean canInsert(@Nullable Direction direction) {
        return true;
    }

    default boolean canExtract(@Nullable Direction direction) {
        return true;
    }
}

package me.paulf.wings.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;


public class SimpleStorage<T> implements INBTSerializable<CompoundTag> {
    private final T instance;
    private final NBTSerializer<T> serializer;

    public SimpleStorage(T instance, NBTSerializer<T> serializer) {
        this.instance = instance;
        this.serializer = serializer.withValidation();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        serializer.writeToNBT(instance, tag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        serializer.readFromNBT(instance, tag);
    }


    public T getInstance() {
        return instance;
    }
}
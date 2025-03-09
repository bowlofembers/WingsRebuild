package me.paulf.wings.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;


public interface NBTSerializer<T> {

    void writeToNBT(T instance, CompoundTag tag);

    void readFromNBT(T instance, CompoundTag tag);

    void writeToBuffer(T instance, FriendlyByteBuf buf);

    void readFromBuffer(T instance, FriendlyByteBuf buf);


    default NBTSerializer<T> withValidation() {
        return new NBTSerializer<>() {
            @Override
            public void writeToNBT(T instance, CompoundTag tag) {
                if (instance == null || tag == null) return;
                NBTSerializer.this.writeToNBT(instance, tag);
            }

            @Override
            public void readFromNBT(T instance, CompoundTag tag) {
                if (instance == null || tag == null) return;
                NBTSerializer.this.readFromNBT(instance, tag);
            }

            @Override
            public void writeToBuffer(T instance, FriendlyByteBuf buf) {
                if (instance == null || buf == null) return;
                NBTSerializer.this.writeToBuffer(instance, buf);
            }

            @Override
            public void readFromBuffer(T instance, FriendlyByteBuf buf) {
                if (instance == null || buf == null) return;
                NBTSerializer.this.readFromBuffer(instance, buf);
            }
        };
    }
}
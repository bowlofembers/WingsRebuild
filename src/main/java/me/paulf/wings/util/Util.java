package me.paulf.wings.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.joml.Vector3f;

import java.util.Optional;
import java.util.function.Supplier;


public final class Util {
    private Util() {}


    public static ResourceLocation resource(String path) {
        return new ResourceLocation("wings", path);
    }


    public static Vector3f toVector3f(Vec3 vec) {
        return new Vector3f((float)vec.x, (float)vec.y, (float)vec.z);
    }


    public static void ifClient(Runnable action) {
        if (FMLEnvironment.dist == Dist.CLIENT) {
            action.run();
        }
    }


    public static <T> Optional<T> when(boolean condition, Supplier<T> supplier) {
        return condition ? Optional.of(supplier.get()) : Optional.empty();
    }


    @SuppressWarnings("unchecked")
    public static <T> Optional<T> safeCast(Object obj, Class<T> type) {
        return Optional.ofNullable(obj)
                .filter(type::isInstance)
                .map(o -> (T) o);
    }
}
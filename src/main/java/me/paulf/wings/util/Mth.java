package me.paulf.wings.util;

import net.minecraft.util.Mth;
import org.joml.Vector3f;


public final class Mth {
    private Mth() {}


    public static float lerp(float delta, float start, float end) {
        return start + delta * (end - start);
    }


    public static float lerpAngle(float delta, float start, float end) {
        float diff = ((end - start) % net.minecraft.util.Mth.TWO_PI);
        if (diff < -net.minecraft.util.Mth.PI) {
            diff += net.minecraft.util.Mth.TWO_PI;
        } else if (diff > net.minecraft.util.Mth.PI) {
            diff -= net.minecraft.util.Mth.TWO_PI;
        }
        return start + delta * diff;
    }


    public static float smoothstep(float edge0, float edge1, float x) {
        x = net.minecraft.util.Mth.clamp((x - edge0) / (edge1 - edge0), 0.0F, 1.0F);
        return x * x * (3 - 2 * x);
    }


    public static Vector3f lerpVector(float delta, Vector3f start, Vector3f end) {
        return new Vector3f(
                lerp(delta, start.x(), end.x()),
                lerp(delta, start.y(), end.y()),
                lerp(delta, start.z(), end.z())
        );
    }
}
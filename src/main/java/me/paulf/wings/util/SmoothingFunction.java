package me.paulf.wings.util;

import net.minecraft.util.Mth;
import java.util.function.UnaryOperator;


public final class SmoothingFunction {
    private SmoothingFunction() {}


    public static UnaryOperator<Float> linear(float speed) {
        return current -> current * speed;
    }


    public static UnaryOperator<Float> exponential(float factor) {
        return current -> current * (1.0f - (float) Math.exp(-factor));
    }


    public static UnaryOperator<Float> clamped(float maxDelta) {
        return current -> Mth.clamp(current, -maxDelta, maxDelta);
    }


    public static UnaryOperator<Float> compose(UnaryOperator<Float>... functions) {
        return value -> {
            float result = value;
            for (UnaryOperator<Float> function : functions) {
                result = function.apply(result);
            }
            return result;
        };
    }
}
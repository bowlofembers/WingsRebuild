package me.paulf.wings.util.function;


@FunctionalInterface
public interface FloatUnaryOperator {

    float applyAsFloat(float operand);

    default FloatUnaryOperator andThen(FloatUnaryOperator after) {
        if (after == null) {
            throw new NullPointerException("after");
        }
        return t -> after.applyAsFloat(applyAsFloat(t));
    }


    default FloatUnaryOperator compose(FloatUnaryOperator before) {
        if (before == null) {
            throw new NullPointerException("before");
        }
        return t -> applyAsFloat(before.applyAsFloat(t));
    }


    static FloatUnaryOperator identity() {
        return t -> t;
    }
}
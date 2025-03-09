package me.paulf.wings.util.function;


@FunctionalInterface
public interface FloatConsumer {

    void accept(float value);


    default FloatConsumer andThen(FloatConsumer after) {
        if (after == null) {
            throw new NullPointerException("after");
        }
        return value -> {
            accept(value);
            after.accept(value);
        };
    }
}
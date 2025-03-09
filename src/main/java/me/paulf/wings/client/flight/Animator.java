package me.paulf.wings.client.flight;

import org.joml.Vector3f;

public interface Animator {
    void update();

    default void beginLand() {}
    default void beginGlide() {}
    default void beginIdle() {}
    default void beginLift() {}
    default void beginFall() {}
}
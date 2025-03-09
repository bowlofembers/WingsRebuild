package me.paulf.wings.util;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.function.Consumer;


public class KeyInputListener {
    private final KeyMapping keyBinding;
    private final Consumer<InputEvent.Key> handler;
    private boolean wasPressed;

    public KeyInputListener(KeyMapping keyBinding, Consumer<InputEvent.Key> handler) {
        this.keyBinding = keyBinding;
        this.handler = handler;
        this.wasPressed = false;
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onKeyInput(InputEvent.Key event) {
        boolean isPressed = keyBinding.isDown();


        if (isPressed != wasPressed) {
            wasPressed = isPressed;
            handler.accept(event);
        }
    }


    public static KeyInputListener onPress(KeyMapping keyBinding, Runnable action) {
        return new KeyInputListener(keyBinding, event -> {
            if (keyBinding.isDown()) {
                action.run();
            }
        });
    }


    public static KeyInputListener onHold(KeyMapping keyBinding, Runnable holdAction,
                                          Runnable releaseAction) {
        return new KeyInputListener(keyBinding, event -> {
            if (keyBinding.isDown()) {
                holdAction.run();
            } else {
                releaseAction.run();
            }
        });
    }
}
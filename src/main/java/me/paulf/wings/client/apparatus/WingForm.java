package me.paulf.wings.client.apparatus;

import com.google.common.collect.Maps;
import me.paulf.wings.client.flight.Animator;
import me.paulf.wings.client.model.ModelWings;
import me.paulf.wings.server.apparatus.FlightApparatus;
import net.minecraft.resources.ResourceLocation;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public final class WingForm<A extends Animator> {
    private static final Map<FlightApparatus, WingForm<?>> REGISTRY = Maps.newHashMap();

    private final Supplier<A> animatorFactory;
    private final ModelWings<A> model;
    private final ResourceLocation texture;

    private WingForm(Supplier<A> animatorFactory, ModelWings<A> model, ResourceLocation texture) {
        this.animatorFactory = animatorFactory;
        this.model = model;
        this.texture = texture;
    }

    public static <A extends Animator> WingForm<A> of(Supplier<A> animatorFactory,
                                                      ModelWings<A> model,
                                                      ResourceLocation texture) {
        return new WingForm<>(animatorFactory, model, texture);
    }

    public static void register(FlightApparatus apparatus, WingForm<?> form) {
        REGISTRY.put(apparatus, form);
    }

    public static Optional<WingForm<?>> get(FlightApparatus apparatus) {
        return Optional.ofNullable(REGISTRY.get(apparatus));
    }

    public A createAnimator() {
        return this.animatorFactory.get();
    }

    public ModelWings<A> getModel() {
        return this.model;
    }

    public ResourceLocation getTexture() {
        return this.texture;
    }
}
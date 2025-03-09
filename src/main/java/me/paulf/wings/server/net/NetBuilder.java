package me.paulf.wings.server.net;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;


public final class NetBuilder {
    private final NetworkRegistry.ChannelBuilder builder;
    private String version;
    private SimpleChannel channel;
    private int id;

    public NetBuilder(ResourceLocation name) {
        this.builder = NetworkRegistry.ChannelBuilder.named(name);
    }


    public NetBuilder version(int version) {
        return this.version(String.valueOf(version));
    }


    public NetBuilder version(String version) {
        if (this.version == null) {
            this.version = Objects.requireNonNull(version);
            this.builder.networkProtocolVersion(() -> version);
            return this;
        }
        throw new IllegalArgumentException("version already assigned");
    }


    public NetBuilder optionalServer() {
        this.builder.clientAcceptedVersions(this.optionalVersion());
        return this;
    }


    public NetBuilder requiredServer() {
        this.builder.clientAcceptedVersions(this.requiredVersion());
        return this;
    }


    public NetBuilder optionalClient() {
        this.builder.serverAcceptedVersions(this.optionalVersion());
        return this;
    }


    public NetBuilder requiredClient() {
        this.builder.serverAcceptedVersions(this.requiredVersion());
        return this;
    }

    private Predicate<String> optionalVersion() {
        String v = this.version;
        if (v == null) {
            throw new IllegalStateException("version not specified");
        }
        return value -> NetworkRegistry.ACCEPTVANILLA.equals(value)
                || NetworkRegistry.ABSENT.equals(value)
                || v.equals(value);
    }

    private Predicate<String> requiredVersion() {
        String v = this.version;
        if (v == null) {
            throw new IllegalStateException("version not specified");
        }
        return v::equals;
    }

    private SimpleChannel channel() {
        if (this.channel == null) {
            this.channel = this.builder.simpleChannel();
        }
        return this.channel;
    }


    public <T extends Message> MessageBuilder<T, ServerMessageContext> serverbound(Supplier<T> factory) {
        return new MessageBuilder<>(factory,
                new HandlerConsumerFactory<>(LogicalSide.SERVER, ServerMessageContext::new));
    }


    public <T extends Message> MessageBuilder<T, ClientMessageContext> clientbound(Supplier<T> factory) {
        return new MessageBuilder<>(factory,
                DistExecutor.runForDist(
                        () -> () -> new HandlerConsumerFactory<>(LogicalSide.CLIENT, ClientMessageContext::new),
                        () -> () -> new NoopConsumerFactory<>()
                ));
    }


    public SimpleChannel build() {
        return this.channel();
    }


    interface ConsumerFactory<T extends Message, S extends MessageContext> {
        BiConsumer<T, Supplier<NetworkEvent.Context>> create(
                Supplier<BiConsumer<? super T, S>> handlerFactory);
    }


    private static class NoopConsumerFactory<T extends Message, S extends MessageContext>
            implements ConsumerFactory<T, S> {

        @Override
        public BiConsumer<T, Supplier<NetworkEvent.Context>> create(
                Supplier<BiConsumer<? super T, S>> handlerFactory) {
            return (msg, ctx) -> ctx.get().setPacketHandled(false);
        }
    }


    private static class HandlerConsumerFactory<T extends Message, S extends MessageContext>
            implements ConsumerFactory<T, S> {

        private final LogicalSide side;
        private final Function<NetworkEvent.Context, S> contextFactory;

        HandlerConsumerFactory(LogicalSide side,
                               Function<NetworkEvent.Context, S> contextFactory) {
            this.side = side;
            this.contextFactory = contextFactory;
        }

        @Override
        public BiConsumer<T, Supplier<NetworkEvent.Context>> create(
                Supplier<BiConsumer<? super T, S>> handlerFactory) {
            return (msg, ctxSupplier) -> {
                NetworkEvent.Context ctx = ctxSupplier.get();
                ctx.enqueueWork(() -> {
                    if (ctx.getDirection().getReceptionSide() == this.side) {
                        handlerFactory.get().accept(msg, this.contextFactory.apply(ctx));
                    }
                });
                ctx.setPacketHandled(true);
            };
        }
    }
}
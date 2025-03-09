package me.paulf.wings.server.flight;

import net.minecraft.world.entity.Entity;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.Event;

public final class AttachFlightCapabilityEvent extends Event {
    private final AttachCapabilitiesEvent<Entity> event;
    private final Flight instance;

    private AttachFlightCapabilityEvent(AttachCapabilitiesEvent<Entity> event, Flight instance) {
        this.event = event;
        this.instance = instance;
    }

    /**
     * Gets the entity that the capabilities are being attached to.
     *
     * @return The target entity
     */
    public Entity getObject() {
        return this.event.getObject();
    }

    /**
     * Adds a capability provider to the entity.
     *
     * @param key The ResourceLocation key for the capability
     * @param cap The capability provider
     */
    public void addCapability(ResourceLocation key, ICapabilityProvider cap) {
        this.event.addCapability(key, cap);
    }

    /**
     * Gets the flight instance being attached.
     *
     * @return The flight capability instance
     */
    public Flight getInstance() {
        return this.instance;
    }

    /**
     * Creates a new AttachFlightCapabilityEvent.
     *
     * @param event The original capability attachment event
     * @param instance The flight capability instance being attached
     * @return A new AttachFlightCapabilityEvent
     */
    public static AttachFlightCapabilityEvent create(AttachCapabilitiesEvent<Entity> event, Flight instance) {
        return new AttachFlightCapabilityEvent(event, instance);
    }
}
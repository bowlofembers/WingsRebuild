package me.paulf.wings.server.advancement.trigger;

import com.google.gson.JsonObject;
import me.paulf.wings.WingsMod;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class WingsFlightTrigger extends SimpleCriterionTrigger<WingsFlightTrigger.Instance> {
    private static final ResourceLocation ID = new ResourceLocation(WingsMod.ID, "first_flight");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    protected Instance createInstance(JsonObject json, ContextAwarePredicate player, DeserializationContext context) {
        return new Instance();
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, instance -> true);
    }

    public static class Instance extends AbstractCriterionTriggerInstance {
        public Instance() {
            super(ID, ContextAwarePredicate.ANY);
        }

        @Override
        public JsonObject serializeToJson(SerializationContext context) {
            return new JsonObject();
        }
    }
}
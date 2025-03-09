package me.paulf.wings.server.item;

public interface WingSettings {
    /**
     * Gets the required food level to start flying.
     * @return The minimum food level needed
     */
    int getRequiredFlightSatiation();

    /**
     * Gets the food exhaustion per flight action.
     * @return The exhaustion amount
     */
    float getFlyingExertion();

    /**
     * Gets the required food level to land.
     * @return The minimum food level needed
     */
    int getRequiredLandSatiation();

    /**
     * Gets the food exhaustion on landing.
     * @return The exhaustion amount
     */
    float getLandingExertion();
}
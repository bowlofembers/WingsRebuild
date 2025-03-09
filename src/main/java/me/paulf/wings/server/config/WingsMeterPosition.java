package me.paulf.wings.server.config;

public enum WingsMeterPosition {
    TOP_LEFT,
    TOP_RIGHT,
    BOTTOM_LEFT,
    BOTTOM_RIGHT;

    public int getX(int screenWidth) {
        return switch (this) {
            case TOP_LEFT, BOTTOM_LEFT -> 10;
            case TOP_RIGHT, BOTTOM_RIGHT -> screenWidth - 90;
        };
    }

    public int getY(int screenHeight) {
        return switch (this) {
            case TOP_LEFT, TOP_RIGHT -> 10;
            case BOTTOM_LEFT, BOTTOM_RIGHT -> screenHeight - 20;
        };
    }
}
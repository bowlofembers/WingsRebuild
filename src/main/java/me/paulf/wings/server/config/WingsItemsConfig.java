package me.paulf.wings.server.config;

public class WingsItemsConfig {
    public static final WingConfig ANGEL = new WingConfig(0.8F, 256.0F);
    public static final WingConfig PARROT = new WingConfig(0.6F, 128.0F);
    public static final WingConfig BAT = new WingConfig(0.7F, 164.0F);
    public static final WingConfig BLUE_BUTTERFLY = new WingConfig(0.5F, 96.0F);
    public static final WingConfig DRAGON = new WingConfig(1.0F, 512.0F);
    public static final WingConfig EVIL = new WingConfig(0.9F, 384.0F);
    public static final WingConfig FAIRY = new WingConfig(0.4F, 64.0F);
    public static final WingConfig MONARCH_BUTTERFLY = new WingConfig(0.5F, 96.0F);
    public static final WingConfig SLIME = new WingConfig(0.6F, 128.0F);
    public static final WingConfig FIRE = new WingConfig(0.85F, 320.0F);

    public static class WingConfig {
        private final float flyingSpeed;
        private final float maxHeight;

        public WingConfig(float flyingSpeed, float maxHeight) {
            this.flyingSpeed = flyingSpeed;
            this.maxHeight = maxHeight;
        }

        public float getFlyingSpeed() {
            return flyingSpeed;
        }

        public float getMaxHeight() {
            return maxHeight;
        }
    }
}
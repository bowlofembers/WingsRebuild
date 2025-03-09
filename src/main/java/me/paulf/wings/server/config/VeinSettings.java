package me.paulf.wings.server.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class VeinSettings {
    private final ForgeConfigSpec.IntValue size;
    private final ForgeConfigSpec.IntValue count;
    private final ForgeConfigSpec.IntValue minHeight;
    private final ForgeConfigSpec.IntValue maxHeight;

    VeinSettings(ForgeConfigSpec.Builder builder, String path, int defaultSize, int defaultCount, int defaultMinHeight, int defaultMaxHeight) {
        builder.push(path);

        this.size = builder
                .comment("Size of the ore vein")
                .defineInRange("size", defaultSize, 8, 32);

        this.count = builder
                .comment("Number of ore veins per chunk")
                .defineInRange("count", defaultCount, 0, 128);

        this.minHeight = builder
                .comment("Minimum height for ore generation")
                .defineInRange("minHeight", defaultMinHeight, -64, 320);

        this.maxHeight = builder
                .comment("Maximum height for ore generation")
                .defineInRange("maxHeight", defaultMaxHeight, -64, 320);

        builder.pop();
    }

    public int getSize() {
        return this.size.get();
    }

    public int getCount() {
        return this.count.get();
    }

    public int getMinHeight() {
        return this.minHeight.get();
    }

    public int getMaxHeight() {
        return this.maxHeight.get();
    }
}
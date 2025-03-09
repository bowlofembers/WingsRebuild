package me.paulf.wings.util;

import net.minecraft.util.Mth;
import org.joml.Vector3f;


public class CubicBezier {
    private final Vector3f p0;
    private final Vector3f p1;
    private final Vector3f p2;
    private final Vector3f p3;


    public CubicBezier(Vector3f p0, Vector3f p1, Vector3f p2, Vector3f p3) {
        this.p0 = new Vector3f(p0);
        this.p1 = new Vector3f(p1);
        this.p2 = new Vector3f(p2);
        this.p3 = new Vector3f(p3);
    }


    public Vector3f evaluate(float t) {
        t = Mth.clamp(t, 0.0f, 1.0f);
        float oneMinusT = 1.0f - t;
        float oneMinusT2 = oneMinusT * oneMinusT;
        float oneMinusT3 = oneMinusT2 * oneMinusT;
        float t2 = t * t;
        float t3 = t2 * t;

        Vector3f result = new Vector3f();

        // P(t) = (1-t)³P₀ + 3(1-t)²tP₁ + 3(1-t)t²P₂ + t³P₃
        result.add(p0.mul(oneMinusT3, new Vector3f()));
        result.add(p1.mul(3 * oneMinusT2 * t, new Vector3f()));
        result.add(p2.mul(3 * oneMinusT * t2, new Vector3f()));
        result.add(p3.mul(t3, new Vector3f()));

        return result;
    }


    public Vector3f derivative(float t) {
        t = Mth.clamp(t, 0.0f, 1.0f);
        float oneMinusT = 1.0f - t;
        float t2 = t * t;

        Vector3f result = new Vector3f();

        // P'(t) = 3(1-t)²(P₁-P₀) + 6(1-t)t(P₂-P₁) + 3t²(P₃-P₂)
        result.add(new Vector3f(p1).sub(p0).mul(3 * oneMinusT * oneMinusT));
        result.add(new Vector3f(p2).sub(p1).mul(6 * oneMinusT * t));
        result.add(new Vector3f(p3).sub(p2).mul(3 * t2));

        return result;
    }


    public static CubicBezier createWingFlap() {
        return new CubicBezier(
                new Vector3f(0.0f, 0.0f, 0.0f),
                new Vector3f(0.4f, 0.0f, 0.2f),
                new Vector3f(0.6f, 0.0f, -0.2f),
                new Vector3f(1.0f, 0.0f, 0.0f)
        );
    }
}
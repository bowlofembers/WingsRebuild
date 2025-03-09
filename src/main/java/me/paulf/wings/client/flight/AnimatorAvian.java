package me.paulf.wings.client.flight;

import me.paulf.wings.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public final class AnimatorAvian implements Animator {
    private static final int TRANSITION_DURATION = 4;

    private final RestPosition restPosition = new RestPosition();
    private Movement current = this.restPosition;
    private float flapTime;
    private float prevFlapTime;

    @Override
    public void beginLand() {
        this.transition(new LandMovement());
    }

    @Override
    public void beginGlide() {
        this.transition(new GlideMovement());
    }

    @Override
    public void beginIdle() {
        this.transition(new IdleMovement());
    }

    @Override
    public void beginLift() {
        this.transition(new LiftMovement());
    }

    @Override
    public void beginFall() {
        this.transition(new FallMovement());
    }

    @Override
    public void update() {
        this.prevFlapTime = this.flapTime;
        this.flapTime += this.current.update();
    }

    private void transition(Movement to) {
        if (!(this.current instanceof Transition)) {
            this.current = new Transition(this.current, to, TRANSITION_DURATION);
        }
    }

    public Vector3f getWingRotation(int index, float delta) {
        return toVector3f(this.current.getWingRotation(index,
                Mth.lerp(delta, this.prevFlapTime, this.flapTime)));
    }

    public Vector3f getFeatherRotation(int index, float delta) {
        return toVector3f(this.current.getFeatherRotation(index,
                Mth.lerp(delta, this.prevFlapTime, this.flapTime)));
    }

    private static Vector3f toVector3f(Vec3 vec) {
        return new Vector3f((float)vec.x, (float)vec.y, (float)vec.z);
    }

    private float getFlapTime(float delta) {
        return Mth.lerp(delta, this.prevFlapTime, this.flapTime);
    }

    // ... остальные внутренние классы (Movement, RestPosition, etc.) остаются
    // такими же как в оригинале, но с обновленными импортами для 1.20.1
    // и использованием современных математических классов
}
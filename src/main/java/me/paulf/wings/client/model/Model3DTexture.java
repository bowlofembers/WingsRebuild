package me.paulf.wings.client.model;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public final class Model3DTexture extends ModelPart.Cube {
    private final int width;
    private final int height;
    private final float u1;
    private final float v1;
    private final float u2;
    private final float v2;
    private final Vector4f pos = new Vector4f();
    private final Vector3f normal = new Vector3f();

    public Model3DTexture(
            float posX, float posY, float posZ,
            int width, int height,
            float u1, float v1,
            float u2, float v2
    ) {
        super(0, 0, posX, posY, posZ, width, height, 1, 0.0F, 0.0F, false, 64, 64);
        this.width = width;
        this.height = height;
        this.u1 = u1;
        this.v1 = v1;
        this.u2 = u2;
        this.v2 = v2;
    }

    @Override
    public void compile(Matrix4f pose, Matrix3f normal, VertexConsumer consumer, int light, int overlay, float red, float green, float blue, float alpha) {
        // Front face
        renderQuad(pose, normal, consumer, light, overlay, red, green, blue, alpha, Direction.FRONT);
        // Back face
        renderQuad(pose, normal, consumer, light, overlay, red, green, blue, alpha, Direction.BACK);
        // Render edges
        renderEdges(pose, normal, consumer, light, overlay, red, green, blue, alpha);
    }

    private void renderQuad(Matrix4f pose, Matrix3f normalMatrix, VertexConsumer consumer, int light, int overlay,
                            float red, float green, float blue, float alpha, Direction face) {
        float x0 = this.minX;
        float x1 = this.minX + this.width;
        float y0 = this.minY;
        float y1 = this.minY + this.height;
        float z = face == Direction.FRONT ? this.minZ + 1 : this.minZ;

        this.normal.set(0.0F, 0.0F, face == Direction.FRONT ? 1.0F : -1.0F);
        this.normal.mul(normalMatrix);
        float nx = this.normal.x();
        float ny = this.normal.y();
        float nz = this.normal.z();

        vertex(pose, consumer, x1, y0, z, this.u2, this.v1, nx, ny, nz, red, green, blue, alpha, light, overlay);
        vertex(pose, consumer, x0, y0, z, this.u1, this.v1, nx, ny, nz, red, green, blue, alpha, light, overlay);
        vertex(pose, consumer, x0, y1, z, this.u1, this.v2, nx, ny, nz, red, green, blue, alpha, light, overlay);
        vertex(pose, consumer, x1, y1, z, this.u2, this.v2, nx, ny, nz, red, green, blue, alpha, light, overlay);
    }

    private void renderEdges(Matrix4f pose, Matrix3f normalMatrix, VertexConsumer consumer, int light, int overlay,
                             float red, float green, float blue, float alpha) {
        // Top edge
        renderEdge(pose, normalMatrix, consumer, light, overlay, red, green, blue, alpha,
                this.minX, this.minY, this.minZ,
                this.minX + this.width, this.minY, this.minZ + 1,
                Direction.UP);

        // Bottom edge
        renderEdge(pose, normalMatrix, consumer, light, overlay, red, green, blue, alpha,
                this.minX, this.minY + this.height, this.minZ,
                this.minX + this.width, this.minY + this.height, this.minZ + 1,
                Direction.DOWN);

        // Left edge
        renderEdge(pose, normalMatrix, consumer, light, overlay, red, green, blue, alpha,
                this.minX, this.minY, this.minZ,
                this.minX, this.minY + this.height, this.minZ + 1,
                Direction.WEST);

        // Right edge
        renderEdge(pose, normalMatrix, consumer, light, overlay, red, green, blue, alpha,
                this.minX + this.width, this.minY, this.minZ,
                this.minX + this.width, this.minY + this.height, this.minZ + 1,
                Direction.EAST);
    }

    private void renderEdge(Matrix4f pose, Matrix3f normalMatrix, VertexConsumer consumer, int light, int overlay,
                            float red, float green, float blue, float alpha,
                            float x1, float y1, float z1,
                            float x2, float y2, float z2,
                            Direction direction) {
        this.normal.set(direction.getStepX(), direction.getStepY(), direction.getStepZ());
        this.normal.mul(normalMatrix);
        float nx = this.normal.x();
        float ny = this.normal.y();
        float nz = this.normal.z();

        // Calculate UV coordinates for edges based on direction
        float u1 = this.u1;
        float v1 = this.v1;
        float u2 = direction.getAxis().isHorizontal() ? this.u2 : this.u1 + 1;
        float v2 = direction.getAxis().isVertical() ? this.v2 : this.v1 + 1;

        vertex(pose, consumer, x1, y1, z1, u1, v1, nx, ny, nz, red, green, blue, alpha, light, overlay);
        vertex(pose, consumer, x2, y1, z1, u2, v1, nx, ny, nz, red, green, blue, alpha, light, overlay);
        vertex(pose, consumer, x2, y2, z2, u2, v2, nx, ny, nz, red, green, blue, alpha, light, overlay);
        vertex(pose, consumer, x1, y2, z2, u1, v2, nx, ny, nz, red, green, blue, alpha, light, overlay);
    }

    private static void vertex(Matrix4f pose, VertexConsumer consumer,
                               float x, float y, float z,
                               float u, float v,
                               float nx, float ny, float nz,
                               float red, float green, float blue, float alpha,
                               int light, int overlay) {
        consumer.vertex(pose, x, y, z)
                .color(red, green, blue, alpha)
                .uv(u, v)
                .overlayCoords(overlay)
                .uv2(light)
                .normal(nx, ny, nz)
                .endVertex();
    }
}
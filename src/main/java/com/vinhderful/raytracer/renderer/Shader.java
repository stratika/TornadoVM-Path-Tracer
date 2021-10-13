package com.vinhderful.raytracer.renderer;

import com.vinhderful.raytracer.scene.Camera;
import com.vinhderful.raytracer.scene.Light;
import com.vinhderful.raytracer.scene.World;
import com.vinhderful.raytracer.utils.Color;
import com.vinhderful.raytracer.utils.Hit;
import com.vinhderful.raytracer.utils.Vector3f;

/**
 * Implementations of the Phong shading techniques
 */
public class Shader {

    /**
     * Constant values to tweak the strengths of ambient and specular lighting
     */
    public static final float AMBIENT_STRENGTH = 0.05F;
    public static final float SPECULAR_STRENGTH = 0.5F;

    /**
     * Get the full Phong color of an object given a hit event and the world
     * 
     * @param hit
     *            the hit event
     * @param world
     *            the world
     * @return the result of the Phong shading
     */
    public static Color getPhong(Hit hit, World world) {
        return getAmbient(hit, world).add(getDiffuse(hit, world)).add(getSpecular(hit, world));
    }

    /**
     * Get the ambient color of a body given a hit event and the world
     *
     * @param hit
     *            the hit event
     * @param world
     *            the world
     * @return the result of the ambient lighting
     */
    public static Color getAmbient(Hit hit, World world) {
        Color shapeColor = hit.getColor();
        Color lightColor = world.getLight().getColor();
        return shapeColor.multiply(lightColor).multiply(AMBIENT_STRENGTH);
    }

    /**
     * Get the diffuse color of a body given a hit event and the world
     *
     * @param hit
     *            the hit event
     * @param world
     *            the world
     * @return the result of the diffuse lighting
     */
    public static Color getDiffuse(Hit hit, World world) {
        Light light = world.getLight();
        Color lightColor = light.getColor();
        Color shapeColor = hit.getColor();

        float diffuseBrightness = Math.max(0F, hit.getNormal().dotProduct(light.getPosition().subtract(hit.getPosition()).normalize()));
        return shapeColor.multiply(lightColor).multiply(diffuseBrightness);
    }

    /**
     * Get the specular highlights of a body given a hit event and the world
     *
     * @param hit
     *            the hit event
     * @param world
     *            the world
     * @return the result of the specular highlights
     */
    public static Color getSpecular(Hit hit, World world) {
        Camera camera = world.getCamera();
        Light light = world.getLight();
        Color lightColor = light.getColor();
        Vector3f hitPos = hit.getPosition();
        Vector3f cameraDirection = hitPos.subtract(camera.getPosition()).normalize();
        Vector3f lightDirection = light.getPosition().subtract(hitPos).normalize();
        Vector3f reflectionVector = lightDirection.subtract(hit.getNormal().multiply(2 * lightDirection.dotProduct(hit.getNormal())));

        float specularFactor = Math.max(0F, reflectionVector.dotProduct(cameraDirection));
        float specularBrightness = (float) Math.pow(specularFactor, hit.getBody().getReflectivity());
        return lightColor.multiply(specularBrightness).multiply(SPECULAR_STRENGTH);
    }
}

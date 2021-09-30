package com.vinhderful.raytracer.shapes;

import com.vinhderful.raytracer.utils.Ray;
import com.vinhderful.raytracer.utils.Vector3f;

import javafx.scene.paint.Color;

public abstract class Shape {
    protected Vector3f position;
    protected Color color;

    public Shape(Vector3f position, Color color) {
        this.position = position;
        this.color = color;
    }

    public abstract Vector3f calculateIntersection(Ray ray);

    public Color getColor() {
        return color;
    }
}
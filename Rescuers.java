package com.mycompany.a4;

import com.codename1.charts.models.Point;

public abstract class Rescuers extends GameObject implements IGuided {
    protected static final float STEP_SIZE = 10.0f; // Step size for movement

    public Rescuers(int size, int color) {
        super(size, color);
        // If needed, translate to an initial position after construction in subclasses.
    }

    @Override
    public void moveLeft() {
        Point loc = getLocation();
        float newX = loc.getX() - STEP_SIZE;
        float dx = newX - loc.getX();
        translate(dx, 0);
    }

    @Override
    public void moveRight() {
        Point loc = getLocation();
        float newX = loc.getX() + STEP_SIZE;
        float dx = newX - loc.getX();
        translate(dx, 0);
    }

    @Override
    public void moveUp() {
        Point loc = getLocation();
        float newY = loc.getY() + STEP_SIZE;
        float dy = newY - loc.getY();
        translate(0, dy);
    }

    @Override
    public void moveDown() {
        Point loc = getLocation();
        float newY = loc.getY() - STEP_SIZE;
        float dy = newY - loc.getY();
        translate(0, dy);
    }

    @Override
    public void jumpToLocation(Point p) {
        Point current = getLocation();
        float dx = p.getX() - current.getX();
        float dy = p.getY() - current.getY();
        translate(dx, dy);
    }

    @Override
    public void setSize(int size) {
        if (size >= 50 && size <= 1000) {
            super.setSize(size);
        } else {
            System.out.println("Size out of bounds.");
        }
    }
}

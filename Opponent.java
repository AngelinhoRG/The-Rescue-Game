package com.mycompany.a4;

import java.util.ArrayList;
import java.util.Random;
import com.codename1.charts.models.Point;

public abstract class Opponent extends GameObject implements IMoving, ICollider {
    private int direction;
    private int speed;
    private ArrayList<GameObject> collisionVector; // To track collisions
    private static final int MIN_SIZE = 20;
    private static final int MAX_SIZE = 50;

    private Random random = new Random();

    protected abstract int calculateSpeed();

    public Opponent(int color) {
        super(generateRandomSize(), color);
        this.speed = calculateSpeed();
        this.direction = random.nextInt(360);
        this.collisionVector = new ArrayList<>();
    }

    private static int generateRandomSize() {
        return new Random().nextInt(MAX_SIZE - MIN_SIZE + 1) + MIN_SIZE;
    }

    @Override
    public void move(int elapsedTime) {
        int randomAdjustment = random.nextInt(51) - 25; // -25 to 25
        direction = (direction + randomAdjustment) % 360;

        // Calculate distance based on speed and elapsed time
        float dist = speed * (elapsedTime / 1000.0f);

        // direction=0 means facing up (based on the original math you used):
        // cos(90 - dir) and sin(90 - dir) were used, so we keep that logic.
        float deltaX = (float) Math.cos(Math.toRadians(90 - direction)) * dist;
        float deltaY = (float) Math.sin(Math.toRadians(90 - direction)) * dist;

        // Move by translating the opponent
        translate(deltaX, deltaY);

        // Check boundaries
        Point currentLoc = getLocation();
        float currentX = currentLoc.getX();
        float currentY = currentLoc.getY();

        // If out of bounds in X
        if (currentX < 0 || currentX > 1000) {
            direction = 180 - direction; // Bounce horizontally
            float correctedX = Math.max(0, Math.min(currentX, 1000));
            float correctionX = correctedX - currentX;
            translate(correctionX, 0);
        }

        // If out of bounds in Y
        if (currentY < 0 || currentY > 1000) {
            direction = 360 - direction; // Bounce vertically
            float correctedY = Math.max(0, Math.min(currentY, 1000));
            float correctionY = correctedY - currentY;
            translate(0, correctionY);
        }

        // Optional: rotate the Opponent to reflect direction changes if desired:
        // rotate(randomAdjustment); 
        // Or rotate to exactly match direction if you want them to face the direction they move:
        // (This would require tracking and resetting rotation angles.)
    }

    @Override
    public boolean collidesWith(GameObject otherObject) {
        float thisCenterX = this.getLocation().getX();
        float thisCenterY = this.getLocation().getY();
        float otherCenterX = otherObject.getLocation().getX();
        float otherCenterY = otherObject.getLocation().getY();

        float dx = thisCenterX - otherCenterX;
        float dy = thisCenterY - otherCenterY;
        float distanceSquared = dx * dx + dy * dy;

        float thisRadius = this.getSize() / 2.0f;
        float otherRadius = otherObject.getSize() / 2.0f;
        float radiusSum = thisRadius + otherRadius;

        return distanceSquared <= radiusSum * radiusSum;
    }

    public void handleCollisionA(GameObject otherObject, GameWorld gw) {
        if (!collisionVector.contains(otherObject)) {
            collisionVector.add(otherObject);

            if (otherObject instanceof Alien && this instanceof Alien) {
                // Create a new Alien
                Alien newAlien = new Alien(gw.generateRandomLocation(), getColor());
                gw.getGameObjectCollection().add(newAlien);

                // Update collision vectors
                collisionVector.add(newAlien);
                newAlien.getCollisionVector().add(this);
                newAlien.getCollisionVector().add(otherObject);

                System.out.println("Alien collision! New alien created.");

                if (gw.isSoundOn()) {
                    gw.getAlienCollisionSound().play(); // Play Alien-Alien collision sound
                }
            }
        }
    }

    public void handleCollisionB(GameObject otherObject, GameWorld gw) {
        if ((otherObject instanceof Astronaut && this instanceof Alien) ||
            (otherObject instanceof Alien && this instanceof Astronaut)) {
            System.out.println("Alien-Astronaut collision detected.");

            Astronaut astronaut;
            if (this instanceof Astronaut) {
                astronaut = (Astronaut) this;
            } else {
                astronaut = (Astronaut) otherObject;
            }
            astronaut.decreaseHealth();
            System.out.println("Astronaut involved in collision: " + astronaut);

            if (gw.isSoundOn()) {
                System.out.println("Playing Alien Astro Collision Sound");
                gw.getAlienAstronautCollisionSound().play();
            }
        }
    }

    public void updateCollisionVector(GameObject otherObject) {
        if (this.collidesWith(otherObject)) {
            if (!collisionVector.contains(otherObject)) {
                collisionVector.add(otherObject);
            }
        } else {
            collisionVector.remove(otherObject);
        }
    }

    public ArrayList<GameObject> getCollisionVector() {
        return collisionVector;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}

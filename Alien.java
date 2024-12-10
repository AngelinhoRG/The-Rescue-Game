package com.mycompany.a4;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;

public class Alien extends Opponent {
    private static final int SPEED_CONSTANT = 80;

    public Alien(Point location, int color) {
        super(color);
        translate(location.getX(), location.getY());
        setSpeed(calculateSpeed());
    }

    @Override
    protected int calculateSpeed() {
        return SPEED_CONSTANT;
    }

    @Override
    public void move(int elapsedTime) {
        super.move(elapsedTime); 
    }

    @Override
    public void setColor(int color) {
        System.out.println("Aliens cannot change color.");
    }

    @Override
    public String toString() {
        Point loc = getLocation();
        return "Alien: loc=" + loc.getX() + "," + loc.getY() +
                " color=[" + ColorUtil.red(getColor()) + "," + ColorUtil.green(getColor()) + "," + ColorUtil.blue(getColor()) + "]" +
                " size=" + getSize() + 
                " speed=" + getSpeed() + 
                " dir=" + getDirection();
    }

    @Override
    public void draw(Graphics g, Point pCmpRelPrnt) {
        Transform gTransform = Transform.makeIdentity();
        g.getTransform(gTransform);

        Transform localTransform = gTransform.copy();
        localTransform.translate(pCmpRelPrnt.getX(), pCmpRelPrnt.getY());
        localTransform.concatenate(getLocalTransform());
        localTransform.translate(-pCmpRelPrnt.getX(), -pCmpRelPrnt.getY());

        g.setTransform(localTransform);

        int size = getSize();
        int half = size / 2;
        g.setColor(getColor());
        g.fillRect(-half, -half, size, size);

        g.setTransform(gTransform);
    }

    @Override
    public void handleCollision(GameObject otherObject, GameWorld gw) {
        handleCollisionA(otherObject, gw);
    }

    @Override
    protected boolean shapeContainsLocalPoint(float x, float y) {
        float half = getSize() / 2f;
        return (x >= -half && x <= half && y >= -half && y <= half);
    }
}

package com.mycompany.a4;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;

public class Astronaut extends Opponent implements ISelectable {
    private int health;
    private static final int MAX_HEALTH = 100;
    private static final int SPEED_CONSTANT = 1;
    private boolean isSelected;

    public Astronaut(Point worldLocation, int color) {
        super(color);
        translate(worldLocation.getX(), worldLocation.getY());
        this.health = MAX_HEALTH;
        setSpeed(calculateSpeed());
    }

    @Override
    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }

    @Override
    public boolean isSelected() {
        return this.isSelected;
    }

    @Override
    public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt) {
        float worldX = pPtrRelPrnt.getX();
        float worldY = pPtrRelPrnt.getY();

        float[] src = { worldX, worldY };
        float[] dst = new float[2];

        Transform inverse = getInverseLocalTransform();
        inverse.transformPoint(src, dst);

        float localX = dst[0];
        float localY = dst[1];

        return shapeContainsLocalPoint(localX, localY);
    }

    @Override
    public void move(int elapsedTime) {
        if (health > 0) {
            super.move(elapsedTime);
        }
    }

    public void decreaseHealth() {
        if (health > 0) {
            health -= 10;
            updateColor();
            setSpeed(calculateSpeed());
            System.out.println("Astronaut's health decreased. Current health: " + health);
        } else {
            System.out.println("This astronaut's health is at a minimum!");
        }

        if (health == 0) {
            setSpeed(0);
            System.out.println("Astronaut has stopped moving due to zero health.");
        }
    }

    @Override
    protected int calculateSpeed() {
        return health * SPEED_CONSTANT;
    }

    private void updateColor() {
        int fadeFactor = (255 * health) / MAX_HEALTH;
        setColor(ColorUtil.rgb(fadeFactor, 0, 0));
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public String toString() {
        Point loc = getLocation();
        return "Astronaut: loc=" + loc.getX() + "," + loc.getY() +
                " color=[" + ColorUtil.red(getColor()) + "," + ColorUtil.green(getColor()) + "," + ColorUtil.blue(getColor()) + "]" +
                " size=" + getSize() +
                " speed=" + getSpeed() +
                " dir=" + getDirection() +
                " health=" + health;
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
        float half = size / 2f;
        int[] xPoints = {0, (int)half, (int)(-half)};
        int[] yPoints = {(int)(-half), (int)half, (int)half};

        g.setColor(getColor());
        if (isSelected) {
            g.drawPolygon(xPoints, yPoints, 3);
        } else {
            g.fillPolygon(xPoints, yPoints, 3);
        }

        g.setTransform(gTransform);
    }

    @Override
    public void handleCollision(GameObject otherObject, GameWorld gw) {
        super.handleCollisionB(otherObject, gw); 
    }

    public void restoreHealth() {
        this.setHealth(MAX_HEALTH);
        this.setColor(ColorUtil.rgb(255, 0, 0));
        System.out.println("Astronaut health restored!");
    }

    @Override
    protected boolean shapeContainsLocalPoint(float x, float y) {
        float half = getSize() / 2f;
        float x1 = 0, y1 = -half;
        float x2 = -half, y2 = half;
        float x3 = half, y3 = half;

        float areaMain = triangleArea(x1, y1, x2, y2, x3, y3);
        float area1 = triangleArea(x, y, x2, y2, x3, y3);
        float area2 = triangleArea(x1, y1, x, y, x3, y3);
        float area3 = triangleArea(x1, y1, x2, y2, x, y);

        float sumAreas = area1 + area2 + area3;
        return Math.abs(sumAreas - areaMain) < 0.001f;
    }

    private float triangleArea(float x1, float y1, float x2, float y2, float x3, float y3) {
        return Math.abs(x1*(y2-y3) + x2*(y3-y1) + x3*(y1-y2)) / 2.0f;
    }
}

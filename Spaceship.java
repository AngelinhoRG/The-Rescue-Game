package com.mycompany.a4;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;

public class Spaceship extends Rescuers {
    private static final int SPACESHIP_DEFAULT_SIZE = 100; 
    private static Spaceship instance; 

    private Spaceship(Point location, int color) {
        super(SPACESHIP_DEFAULT_SIZE, color);
        translate(location.getX(), location.getY());
    }

    public static Spaceship getInstance(Point location, int color) {
        if (instance == null) {
            instance = new Spaceship(location, color);
        }
        return instance;
    }

    @Override
    public void setColor(int color) {
        System.out.println("Spaceship color cannot be changed once created.");
    }
    
    @Override
    public String toString() {
        Point loc = getLocation();
        return "Spaceship: loc=" + loc.getX() + "," + loc.getY() +
               " color=[" + ColorUtil.red(getColor()) + "," + ColorUtil.green(getColor()) + "," + ColorUtil.blue(getColor()) + "]" +
               " size=" + getSize();
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
        g.fillArc(-half, -half, size, size, 0, 360);

        g.setTransform(gTransform);
    }

    @Override
    protected boolean shapeContainsLocalPoint(float x, float y) {
        float radius = getSize() / 2f;
        return (x*x + y*y <= radius*radius);
    }
}

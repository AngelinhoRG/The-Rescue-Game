package com.mycompany.a4;

import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Point;

public class Body {
    private int myRadius;
    private int myColor;
    private Transform myTranslation, myRotation, myScale;

    public Body() {
        myRadius = 10;
        myColor = 0xFFFF00; // Yellow
        myTranslation = Transform.makeIdentity();
        myRotation = Transform.makeIdentity();
        myScale = Transform.makeIdentity();
    }

    public void rotate(double degrees) {
        myRotation.rotate((float) Math.toRadians(degrees), 0, 0);
    }

    public void scale(double sx, double sy) {
        myScale.scale((float) sx, (float) sy);
    }

    public void translate(double tx, double ty) {
        myTranslation.translate((float) tx, (float) ty);
    }

    public void draw(Graphics g, Point pCmpRelPrnt, Point pCmpRelScrn) {
        g.setColor(myColor);
        Transform gXform = Transform.makeIdentity();
        g.getTransform(gXform);
        Transform gOrigXform = gXform.copy();
        gXform.translate(pCmpRelScrn.getX(), pCmpRelScrn.getY());
        gXform.translate(myTranslation.getTranslateX(), myTranslation.getTranslateY());
        gXform.concatenate(myRotation);
        gXform.scale(myScale.getScaleX(), myScale.getScaleY());
        gXform.translate(-pCmpRelScrn.getX(), -pCmpRelScrn.getY());
        g.setTransform(gXform);
        g.fillArc(pCmpRelPrnt.getX() - myRadius, pCmpRelPrnt.getY() - myRadius, 2 * myRadius, 2 * myRadius, 0, 360);
        g.setTransform(gOrigXform);
    }

    public void setColor(int rgb) {
        myColor = rgb;
    }

}


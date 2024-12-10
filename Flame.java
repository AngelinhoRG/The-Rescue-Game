package com.mycompany.a4;

import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Point;

public class Flame {
    private Triangle myTriangle;

    public Flame() {
        myTriangle = new Triangle(40, 40);
    }

    public void rotate(double degrees) {
        myTriangle.rotate((float) degrees);
    }

    public void scale(double sx, double sy) {
        myTriangle.scale((float) sx, (float) sy);
    }

    public void translate(double tx, double ty) {
        myTriangle.translate((float) tx, (float) ty);
    }

    
    public void setColor(int color) {
        myTriangle.setMyColor(color);
    }

    public void draw(Graphics g, Point pCmpRelPrnt, Point pCmpRelScrn) {
        myTriangle.draw(g, pCmpRelPrnt, pCmpRelScrn);
    }
}

package com.mycompany.a4;

import com.codename1.charts.util.ColorUtil; // Import ColorUtil for RGB colors
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Point;

public class FireOval {
    private Body myBody;
    private Flame[] flames;
    private Transform myTranslation, myRotation, myScale;
    
    // Variables for dynamic flame movement
    private double flameOffset = 0;         // Current flame distance from FireOval
    private double flameIncrement = 1;     // Change in flame distance each tick
    private double maxFlameOffset = 10;    // Max distance before reversing

    public FireOval() {
        myTranslation = Transform.makeIdentity();
        myRotation = Transform.makeIdentity();
        myScale = Transform.makeIdentity();
        myBody = new Body();
        myBody.setColor(ColorUtil.rgb(255, 255, 0)); // Yellow
        myBody.scale(2.5, 1.5);
        flames = new Flame[4];
        initializeFlames();
    }

    private void initializeFlames() {
        flames[0] = new Flame();
        flames[0].translate(0, 40);
        flames[0].scale(0.25, 1);
        flames[0].setColor(ColorUtil.rgb(0, 0, 0)); // Black

        flames[1] = new Flame();
        flames[1].translate(0, 40);
        flames[1].rotate(-90);
        flames[1].scale(0.25, 1);
        flames[1].setColor(ColorUtil.rgb(0, 255, 0)); // Green

        flames[2] = new Flame();
        flames[2].translate(0, 40);
        flames[2].rotate(180);
        flames[2].scale(0.25, 1);
        flames[2].setColor(ColorUtil.rgb(0, 0, 255)); // Blue

        flames[3] = new Flame();
        flames[3].translate(0, 40);
        flames[3].rotate(90);
        flames[3].scale(0.25, 1);
        flames[3].setColor(ColorUtil.rgb(255, 0, 255)); // Magenta
    }

    public void scale(double sx, double sy) {
        myScale.scale((float) sx, (float) sy);
    }

    public void rotate(double degrees) {
        myRotation.rotate((float) Math.toRadians(degrees), 0, 0);
    }

    public void translate(double tx, double ty) {
        myTranslation.translate((float) tx, (float) ty);
    }

    public void draw(Graphics g, Point pCmpRelPrnt, Point pCmpRelScrn) {
        Transform gXform = Transform.makeIdentity();
        g.getTransform(gXform);
        Transform gOrigXform = gXform.copy();

        // Apply transformations
        gXform.translate(pCmpRelScrn.getX(), pCmpRelScrn.getY());
        gXform.translate(myTranslation.getTranslateX(), myTranslation.getTranslateY());
        gXform.concatenate(myRotation);
        gXform.scale(myScale.getScaleX(), myScale.getScaleY());
        gXform.translate(-pCmpRelScrn.getX(), -pCmpRelScrn.getY());
        g.setTransform(gXform);

        // Draw the body and flames
        myBody.draw(g, pCmpRelPrnt, pCmpRelScrn);
        for (Flame flame : flames) {
            flame.draw(g, pCmpRelPrnt, pCmpRelScrn);
        }

        // Restore the original transformation
        g.setTransform(gOrigXform);
    }

    public void updateLTs() {
        // Update the FireOval's position and orientation
        this.translate(1, 1); // Incrementally translate FireOval
        this.rotate(1);       // Incrementally rotate FireOval by 1 degree

        // Update the flames' positions along their local Y-axis
        for (Flame flame : flames) {
            flame.translate(0, (float) flameIncrement);
        }

        // Update the flame offset and reverse increment direction if needed
        flameOffset += flameIncrement;
        if (Math.abs(flameOffset) >= maxFlameOffset) {
            flameIncrement *= -1; // Reverse the direction of flame movement
        }
    }
}

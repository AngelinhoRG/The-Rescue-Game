package com.mycompany.a4;

import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Point;

public class CustomContainer extends Container {
	
	
    private FireOval myFireOval;

    public CustomContainer() {
        myFireOval = new FireOval();
        myFireOval.scale(2, 2);
        myFireOval.rotate(45);
        myFireOval.translate(400, 200);
    }

    public FireOval getFireOval() {
        return myFireOval;
    }

    public void paint(Graphics g) {
        super.paint(g);

        // Get the current transformation of the Graphics object
        Transform gXform = Transform.makeIdentity();
        g.getTransform(gXform);

        // Move the drawing coordinates back to align with screen space
        gXform.translate(getAbsoluteX(), getAbsoluteY());

        // Apply the display mapping (flipping Y and aligning origin)
        gXform.translate(0, getHeight());
        gXform.scale(1, -1);

        // Move drawing coordinates as part of the local origin transformations
        gXform.translate(-getAbsoluteX(), -getAbsoluteY());
        g.setTransform(gXform);

        // Define the relative positions for drawing
        Point pCmpRelPrnt = new Point(this.getX(), this.getY());
        Point pCmpRelScrn = new Point(getAbsoluteX(), getAbsoluteY());

        // Tell the FireOval object to draw itself
        myFireOval.draw(g, pCmpRelPrnt, pCmpRelScrn);

        // Restore the Graphics object's transformation to avoid side effects
        g.resetAffine();
    }
}



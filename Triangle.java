package com.mycompany.a4;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;

public class Triangle {
private Point top, bottomLeft, bottomRight;
private int myColor ;
private Transform myRotation, myTranslation, myScale ;

public Triangle (int base, int height) {
top = new Point (0, height/2);
bottomLeft = new Point (-base/2, -height/2);
bottomRight = new Point (base/2, -height/2);
myColor = ColorUtil.BLUE ;
myRotation = Transform.makeIdentity();
myTranslation = Transform.makeIdentity();
myScale = Transform.makeIdentity();
}

public void rotate (float degrees) {
//pivot point (rotation origin) is (0,0), this means the rotation will be applied about
//the screen origin
myRotation.rotate ((float)Math.toRadians(degrees),0,0);
}

public void translate (float tx, float ty) {
myTranslation.translate (tx, ty);
}

public void scale (float sx, float sy) {
//remember that like other transformation methods, scale() is also applied relative to
//screen origin
myScale.scale (sx, sy);
}

public void resetTransform() {
myRotation.setIdentity();
myTranslation.setIdentity();
myScale.setIdentity();
}

/* This method applies the triangles LTs to the received Graphics objects xform, then uses this
xform (with the additional transformations) to draw the triangle. Note that we pass getAbsoluteX()
and getAbsoluteY() values of the container as pCmpRelScrn*/
public void draw (Graphics g, com.codename1.ui.geom.Point pCmpRelPrnt, com.codename1.ui.geom.Point pCmpRelScrn) {
g.setColor(myColor);
//append the triangles LTs to the xform in the Graphics object. But first move the drawing
//coordinates so that the local origin coincides with the screen origin. After LTs are applied,
//move the drawing coordinates back.
Transform gXform = Transform.makeIdentity();
g.getTransform(gXform);
gXform.translate(pCmpRelScrn.getX(),pCmpRelScrn.getY());
gXform.translate(myTranslation.getTranslateX(), myTranslation.getTranslateY());
gXform.concatenate(myRotation);
gXform.scale(myScale.getScaleX(), myScale.getScaleY());
gXform.translate(-pCmpRelScrn.getX(),-pCmpRelScrn.getY());
g.setTransform(gXform);


    // Convert floating-point coordinates to integers
    int topX = (int)(pCmpRelPrnt.getX() + top.getX());
    int topY = (int)(pCmpRelPrnt.getY() + top.getY());
    int bottomLeftX = (int)(pCmpRelPrnt.getX() + bottomLeft.getX());
    int bottomLeftY = (int)(pCmpRelPrnt.getY() + bottomLeft.getY());
    int bottomRightX = (int)(pCmpRelPrnt.getX() + bottomRight.getX());
    int bottomRightY = (int)(pCmpRelPrnt.getY() + bottomRight.getY());
    
    // Draw the triangle edges
    g.drawLine(topX, topY, bottomLeftX, bottomLeftY); 
    g.drawLine(bottomLeftX, bottomLeftY, bottomRightX, bottomRightY); 
    g.drawLine(bottomRightX, bottomRightY, topX, topY);
}

public void setMyColor(int color) {
	// TODO Auto-generated method stub
	
}
} //end of Triangle class
package com.mycompany.a4;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;

public class Square {
private Point topLeft, topRight, bottomLeft, bottomRight;
private int myColor ;
private Transform myRotation, myTranslation, myScale ;

public Square (int size) {
int corner = size/2;
topLeft = new Point (-corner, corner);
topRight = new Point(corner, corner);
bottomLeft = new Point (-corner, -corner);
bottomRight = new Point (corner, -corner);
myColor = ColorUtil.BLUE ;
myRotation = Transform.makeIdentity();
myTranslation = Transform.makeIdentity();
myScale = Transform.makeIdentity();
}

public void rotate (float degrees) {
myRotation.rotate ((float)Math.toRadians(degrees),0,0);
}

public void translate (float tx, float ty) {
myTranslation.translate (tx, ty);
}

public void scale (float sx, float sy) {
myScale.scale (sx, sy);
}

public void resetTransform() {
myRotation.setIdentity();
myTranslation.setIdentity();
myScale.setIdentity();
}


public void draw (Graphics g, Point pCmpRelPrnt, Point pCmpRelScrn) {
g.setColor(myColor);
Transform gXform = Transform.makeIdentity();
g.getTransform(gXform);
gXform.translate(pCmpRelScrn.getX(),pCmpRelScrn.getY());
gXform.translate(myTranslation.getTranslateX(), myTranslation.getTranslateY());
gXform.concatenate(myRotation);
gXform.scale(myScale.getScaleX(), myScale.getScaleY());
gXform.translate(-pCmpRelScrn.getX(),-pCmpRelScrn.getY());
g.setTransform(gXform);


    // Convert floating-point coordinates to integers
    int topLeftX = (int)(pCmpRelPrnt.getX() + topLeft.getX());
    int topLeftY = (int)(pCmpRelPrnt.getY() + topLeft.getY());
    int topRightX = (int)(pCmpRelPrnt.getX() + topRight.getX());
    int topRightY = (int)(pCmpRelPrnt.getY() + topRight.getY());
    int bottomLeftX = (int)(pCmpRelPrnt.getX() + bottomLeft.getX());
    int bottomLeftY = (int)(pCmpRelPrnt.getY() + bottomLeft.getY());
    int bottomRightX = (int)(pCmpRelPrnt.getX() + bottomRight.getX());
    int bottomRightY = (int)(pCmpRelPrnt.getY() + bottomRight.getY());
    
    // Draw the square edges
    g.drawLine(topLeftX, topLeftY, bottomLeftX, bottomLeftY); //left side
    g.drawLine(bottomRightX, bottomRightY, topRightX, topRightY); // right side
    g.drawLine(topLeftX, topLeftY, topRightX, topRightY);
    g.drawLine(bottomLeftX, bottomLeftY, bottomRightX, bottomRightY); // bottom side 
    
}
} //end of Square class
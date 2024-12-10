package com.mycompany.a4;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;

public abstract class GameObject implements IDrawable {
    private int originalSize;     
    private int color;           

    // Transformation parameters
    private float translateX = 0;
    private float translateY = 0;
    private float rotationAngle = 0; // in degrees
    private float scaleX = 1;
    private float scaleY = 1;

    private Transform myTranslate = Transform.makeIdentity();
    private Transform myRotate    = Transform.makeIdentity();
    private Transform myScale     = Transform.makeIdentity();

    public GameObject(int size, int color) {
        this.originalSize = size;
        this.color = color;
    }

    public abstract void draw(Graphics g, Point pCmpRelPrnt);

    public void translate(float tx, float ty) {
        translateX += tx;
        translateY += ty;
        myTranslate.translate(tx, ty);
    }

    public void rotate(float degrees) {
        rotationAngle += degrees;
        myRotate.rotate((float)Math.toRadians(degrees), 0, 0);
    }

    public void scale(float sx, float sy) {
        scaleX *= sx;
        scaleY *= sy;
        myScale.scale(sx, sy);
    }

    /**
     * We will use the order: Scale -> Rotate -> Translate
     * This ensures translation is in final world space, making getLocation() correspond visually.
     */
    public Transform getLocalTransform() {
        Transform localTransform = Transform.makeIdentity();

        // Scale first
        localTransform.concatenate(myScale);
        // Then rotate
        localTransform.concatenate(myRotate);
        // Finally translate
        localTransform.concatenate(myTranslate);

        return localTransform;
    }

    /**
     * Inverse of S*R*T is T^-1 * R^-1 * S^-1 in the reverse order.
     */
    public Transform getInverseLocalTransform() {
        Transform inverse = Transform.makeIdentity();

        // Inverse of translate
        inverse.translate(-translateX, -translateY);

        // Inverse of rotate
        float invRotationAngle = -rotationAngle;
        inverse.rotate((float)Math.toRadians(invRotationAngle), 0, 0);

        // Inverse of scale
        float invScaleX = 1/scaleX;
        float invScaleY = 1/scaleY;
        inverse.scale(invScaleX, invScaleY);

        return inverse;
    }

    public int getSize() {
        // Assume uniform scaling if needed:
        float uniformScale = (scaleX + scaleY) / 2f; 
        return (int)(originalSize * uniformScale);
    }

    public void setSize(int newSize) {
        this.originalSize = newSize;
        scaleX = 1;
        scaleY = 1;
        myScale = Transform.makeIdentity();
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Point getLocation() {
        // Since we do Scale->Rotate->Translate, translateX/Y represent the object's world position.
        return new Point(translateX, translateY);
    }

    public void setLocation(float x, float y) {
        translateX = x;
        translateY = y;
        myTranslate = Transform.makeIdentity();
        myTranslate.translate(x, y);
    }

    public boolean contains(Point worldPoint) {
        float[] src = { worldPoint.getX(), worldPoint.getY() };
        float[] dst = new float[2];

        Transform inverse = getInverseLocalTransform();
        inverse.transformPoint(src, dst);

        float localX = dst[0];
        float localY = dst[1];

        return shapeContainsLocalPoint(localX, localY);
    }

    protected abstract boolean shapeContainsLocalPoint(float x, float y);

    @Override
    public String toString() {
        return "GameObject{" +
                "originalSize=" + originalSize +
                ", currentSize=" + getSize() +
                ", color=" + ColorUtil.red(color) + "," + ColorUtil.green(color) + "," + ColorUtil.blue(color) +
                ", worldLocation=(" + getLocation().getX() + ", " + getLocation().getY() + ")" +
                '}';
    }
}

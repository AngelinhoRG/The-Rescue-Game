package com.mycompany.a4;

import java.util.ArrayList;

public interface ICollider {
    boolean collidesWith(GameObject otherObject);
    void handleCollision(GameObject otherObject, GameWorld gw);
    ArrayList<GameObject> getCollisionVector(); // New method in ICollider
}



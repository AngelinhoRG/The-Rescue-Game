package com.mycompany.a4;

import com.codename1.charts.models.Point;

public interface IGuided {
    void moveLeft();    // Move object to the left
    void moveRight();   // Move object to the right
    void moveUp();      // Move object upwards
    void moveDown();    // Move object downwards
    void jumpToLocation(Point p); // Jump to a specified location
}


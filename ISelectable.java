package com.mycompany.a4;

import com.codename1.charts.models.Point;
import com.codename1.ui.Graphics;

public interface ISelectable {
    void setSelected(boolean b);
    boolean isSelected();
    boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt); // Update this to accept Point arguments
    public void draw(Graphics g, Point pCmpRelPrnt);
}


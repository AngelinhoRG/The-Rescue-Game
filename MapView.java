package com.mycompany.a4;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.plaf.Border;

import java.util.Observable;
import java.util.Observer;

public class MapView extends Container implements Observer {
	private GameWorld gw;
    private Game game; // Reference to the Game instance
	private float iPx = 0;
	private float iPy = 0;
	private boolean pressed = false;

    public MapView(GameWorld gw, Game game) {
        this.gw = gw;
        this.game = game; // Store the Game instance
        this.getAllStyles().setBgColor(ColorUtil.WHITE);
        this.getAllStyles().setBgTransparency(255);
    }

    @Override
    public void pointerPressed(int x, int y) {
        if (!game.isPaused()) {
            System.out.println("Selection is disabled during play mode.");
            return; // Allow selection only when the game is paused
        }

        // Adjust pointer coordinates relative to the MapView container
        x = x - getParent().getAbsoluteX();
        y = y - getParent().getAbsoluteY();

        Point pPtrRelPrnt = new Point(x, y);
        Point pCmpRelPrnt = new Point(getX(), getY());

        boolean found = false;

        IIterator<GameObject> iterator = gw.getGameObjectCollection().getIterator();
        while (iterator.hasNext()) {
            GameObject obj = iterator.getNext();
            if (obj instanceof ISelectable) {
                ISelectable selectable = (ISelectable) obj;

                if (selectable.contains(pPtrRelPrnt, pCmpRelPrnt)) {
                    selectable.setSelected(true);
                    found = true;
                    System.out.println("Selected object: " + obj);
                } else {
                    selectable.setSelected(false);
                }
            }
        }

        if (!found) {
            System.out.println("No object selected.");
        }

        repaint(); // Redraw to reflect selection changes
    }



    @Override
    public void layoutContainer() {
        super.layoutContainer();
        //System.out.println("MapView dimensions: " + getWidth() + "x" + getHeight());
//        gw.setWorldWidth(this.getWidth());
//        gw.setWorldHeight(this.getHeight());
    }




    @Override
    public void update(Observable observable, Object data) {
        // Ensure this is called after GameWorld changes
        //System.out.println("MapView update() called");
        repaint(); // Trigger repaint to call paint()
    }

    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //System.out.println("MapView paint() invoked");

        Point pCmpRelPrnt = new Point(getX(), getY());
        IIterator<GameObject> iterator = gw.getGameObjectCollection().getIterator();

        while (iterator.hasNext()) {
            GameObject obj = iterator.getNext();
            if (obj instanceof IDrawable) {
                ((IDrawable) obj).draw(g, pCmpRelPrnt);
                //System.out.println("Drawing object: " + obj);
            }
        }
    }







}
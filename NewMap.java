package com.mycompany.a4;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.models.Point;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;

public class NewMap extends Container implements Observer{
    private GameWorld gw;
    Transform worldToND, ndToDisplay, theVTM; 
	private float winLeft, winBottom, winRight, winTop, winWidth, winHeight; 
    
    public NewMap(GameWorld world) {
        this.gw = world;
		//initialize world window 
		winLeft = 0;
		winBottom = 0;
		winRight = gw.getWorldWidth();   // match game world width
		winTop = gw.getWorldHeight();    // match game world height
 
		winWidth = winRight - winLeft; 
		winHeight = winTop - winBottom;
    }
     
    @Override
    public void update(Observable observable, Object data) {
//        this.gw = (GameWorld) observable;
//        gw.printObjects(); // existing print for debugging
        repaint(); // This should trigger the paint() method
    }
	
	public void paint (Graphics g) { 
		super.paint(g);//...[calculate winWidth and winHeight] 
		// construct the Viewing Transformation Matrix 
		worldToND = buildWorldTONDXform (winWidth, winHeight, winLeft, winBottom); 
		ndToDisplay = buildNDToDisplayXform(this.getWidth(), this.getHeight()); 
		theVTM = ndToDisplay.copy(); 
		theVTM.concatenate (worldToND); // worldToND will be applied first to points!
	
		Transform gXform = Transform.makeIdentity(); 
		g.getTransform(gXform); 
		gXform.translate (getAbsoluteX(), getAbsoluteY()); 
		//local origin gXform (part 2) 
		gXform.concatenate (theVTM); //VTM gXform 
		gXform.translate (-getAbsoluteX(),-getAbsoluteY()); //local origin gXform (part 1) 
		g.setTransform(gXform); // tell each shape to draw itself using the g (which contains the VTM) 
		Point pCmpRelPrnt = new Point(getX(),getY());
		//origin location of the component (CustomContainer) relative to the screen origin
		Point pCmpRelScreen = new Point(getAbsoluteX(),getAbsoluteY());
		//for (Shapes: shapeCollection) 
	//		s.draw(g, pCmpRelPrnt, pCmpRelScrn);
		
		IIterator<?> iter = gw.getGameObjectCollection().getIterator();


        // Iterate over game objects in GameWorld and call their draw method if needed
        GameObject obj;
		while(iter.hasNext()) {
			obj = (GameObject) iter.getNext();
			if(obj instanceof Opponent) {
				((Opponent)obj).draw(g, pCmpRelPrnt);
			}
			else {
				obj.draw(g, pCmpRelPrnt);
			}
		}
		
		g.resetAffine(); 
	} 
	
	private Transform buildWorldTONDXform (float winWidth, float winHeight, float winLeft, float winBottom) { 
		Transform tmpXfrom = Transform.makeIdentity(); 

		tmpXfrom.scale( (1/winWidth), (1/winHeight)); 
		tmpXfrom.translate(-winLeft,-winBottom); 
		return tmpXfrom;
		} 
	private Transform buildNDToDisplayXform (float displayWidth, float displayHeight) { 
		Transform tmpXfrom = Transform.makeIdentity(); 
		tmpXfrom.translate(0, displayHeight); 
		tmpXfrom.scale (displayWidth, -displayHeight); 
		return tmpXfrom; 
		}
	
	public void zoom(float factor) {
		//positive factor would zoom in (make the worldWin smaller), suggested value is 0.05f
		//negative factor would zoom out (make the worldWin larger), suggested value is -0.05f
		//...[calculate winWidth and winHeight]
		float newWinLeft = winLeft + winWidth*factor;
		float newWinRight = winRight - winWidth*factor;
		float newWinTop = winTop - winHeight*factor;
		float newWinBottom = winBottom + winHeight*factor;
		float newWinHeight = newWinTop - newWinBottom;
		float newWinWidth = newWinRight - newWinLeft;
		//in CN1 do not use world window dimensions greater than 1000!!!
		if (newWinWidth <= 1000 && newWinHeight <= 1000 && newWinWidth > 0 && newWinHeight > 0 ){
			winLeft = newWinLeft;
			winRight = newWinRight;
			winTop = newWinTop;
			winBottom = newWinBottom;
		}
		else {
			System.out.println("Cannot zoom further!");
		}
		this.repaint();
	}
	
	public void panHorizontal(double delta) {
		//positive delta would pan right (image would shift left), suggested value is 5
		//negative delta would pan left (image would shift right), suggested value is -5
		winLeft += delta;
		winRight += delta;
		this.repaint();
	}
		
	public void panVertical(double delta) {
		//positive delta would pan up (image would shift down), suggested value is 5
		//negative delta would pan down (image would shift up), suggested value is -5
		winBottom += delta;
		winTop += delta;
		this.repaint();
	}
	
	@Override
	public boolean pinch(float scale){
		if(scale < 1.0){
			//Zooming Out: two fingers come closer together (on actual device), right mouse
			//click + drag towards the top left corner of screen (on simulator)
			zoom(-0.05f);
			System.out.println("Zooming out");
		}
		else if(scale>1.0){
			//Zooming In: two fingers go away from each other (on actual device), right mouse
			//click + drag away from the top left corner of screen (on simulator)
			zoom(0.05f);
			System.out.println("Zooming in");
		}
		return true;
	} 
	
	private Point pPrevDragLoc = new Point(-1, -1);
	@Override
	public void pointerDragged(int x, int y)
	{
		if (pPrevDragLoc.getX() != -1) {
			if (pPrevDragLoc.getX() < x) {
				panHorizontal(5);
			}
			else if (pPrevDragLoc.getX() > x) {
			panHorizontal(-5);
			}
			
			if (pPrevDragLoc.getY() < y) {
				panVertical(-5);
			}
			else if (pPrevDragLoc.getY() > y) {
				panVertical(5);
			}
		}
		pPrevDragLoc.setX(x);
		pPrevDragLoc.setY(y);
	}
}
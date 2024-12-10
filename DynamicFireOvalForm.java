package com.mycompany.a4;


import com.codename1.ui.Form;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.util.UITimer;

public class DynamicFireOvalForm extends Form implements Runnable {
    private CustomContainer myCustomContainer;

    public DynamicFireOvalForm() {
        // Initialize the container and layout
        super(new BorderLayout());
        myCustomContainer = new CustomContainer();
        
        // Add the CustomContainer to the center of the form
        this.add(BorderLayout.CENTER, myCustomContainer);

        // Add placeholder buttons to other layout areas (optional)
        this.add(BorderLayout.NORTH, new com.codename1.ui.Button("North"));
        this.add(BorderLayout.SOUTH, new com.codename1.ui.Button("South"));
        this.add(BorderLayout.WEST, new com.codename1.ui.Button("West"));

        // Initialize and start the UITimer
        UITimer timer = new UITimer(this);
        timer.schedule(10, true, this); // Trigger every 10ms
    }

    @Override
    public void run() {
        // Update the local transformations of FireOval
        myCustomContainer.getFireOval().updateLTs();

        // Repaint the container to reflect changes
        myCustomContainer.repaint();
    }
}

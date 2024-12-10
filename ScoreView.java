package com.mycompany.a4;

import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.FlowLayout;
import java.util.Observable;
import java.util.Observer;

public class ScoreView extends Container implements Observer {

    // Labels to display game state information
    private Label clockLabel;
    private Label scoreLabel;
    private Label astronautsRescuedLabel;
    private Label aliensSneakedInLabel;
    private Label astronautsRemaining; 
    private Label aliensRemaining;
    private Label soundLabel;

    public ScoreView() {
        // Set layout for the container
        this.setLayout(new FlowLayout(CENTER));

        // Initialize labels
        clockLabel = new Label("Clock Time: 0");
        scoreLabel = new Label("Score: 0");
        astronautsRescuedLabel = new Label("Astronauts Rescued: 0");
        aliensSneakedInLabel = new Label("Aliens Sneaked In: 0");
        astronautsRemaining = new Label("Astronauts Remaining: 0");
        aliensRemaining = new Label("Aliens Remaining: 0");
        soundLabel = new Label("Sound: OFF");

        // Add labels to the container (order matters for UI design)
        this.add(clockLabel);
        this.add(scoreLabel);
        this.add(astronautsRescuedLabel);
        this.add(aliensSneakedInLabel);
        this.add(astronautsRemaining);
        this.add(aliensRemaining);
        this.add(soundLabel);
    }

    @Override
    public void update(Observable observable, Object data) {
        // Cast observable to GameWorld
        GameWorld gw = (GameWorld) observable;

        // Update labels with the latest game state values
        clockLabel.setText("Clock Time: " + gw.getGameClock());
        scoreLabel.setText("Score: " + gw.getScore());
        astronautsRescuedLabel.setText("Astronauts Rescued: " + gw.getAstronautsRescued());
        aliensSneakedInLabel.setText("Aliens Sneaked In: " + gw.getAliensSneakedIn());
        astronautsRemaining.setText("Astronauts Remaining: " + gw.astroRemaining());
        aliensRemaining.setText("Aliens Remaining: " + gw.getAliensRemaining()); // Update the aliens remaining label
        soundLabel.setText("Sound: " + (gw.isSoundOn() ? "ON" : "OFF"));

        // Refresh the container to reflect changes
        this.revalidate();
    }

}

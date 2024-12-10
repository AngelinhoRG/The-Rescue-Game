package com.mycompany.a4;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class ScoreCommand extends Command {
    private GameWorld gw;

    public ScoreCommand(GameWorld gw) {
        super("Score");
        this.gw = gw;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        // Call the method in GameWorld to display or update the score information
        gw.printGameStateValues();
    }
}

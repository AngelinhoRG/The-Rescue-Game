package com.mycompany.a4;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class PauseCommand extends Command {
    private Game game;

    public PauseCommand(Game game) {
        super("Pause"); // Start with "Pause"
        this.game = game;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (game.isRunning()) {
            game.pauseGame();
            this.setCommandName("Play"); // Change label to "Play"
        } else {
            game.resumeGame();
            this.setCommandName("Pause"); // Change label to "Pause"
        }
    }
}

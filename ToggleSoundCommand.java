package com.mycompany.a4;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class ToggleSoundCommand extends Command {
    private GameWorld gw;

    public ToggleSoundCommand(GameWorld gw) {
        super("Toggle Sound");
        this.gw = gw;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        gw.toggleSound();
    }
}




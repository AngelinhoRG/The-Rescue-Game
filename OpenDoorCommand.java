package com.mycompany.a4;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class OpenDoorCommand extends Command {
    private GameWorld gw;

    public OpenDoorCommand(GameWorld gw) {
        super("Open Door");
        this.gw = gw;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        gw.openDoor();
    }
}

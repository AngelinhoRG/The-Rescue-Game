package com.mycompany.a4;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class MoveUpCommand extends Command {
    private GameWorld gw;

    public MoveUpCommand(GameWorld gw) {
        super("Move Up");
        this.gw = gw;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        gw.moveShipUp();
    }
}

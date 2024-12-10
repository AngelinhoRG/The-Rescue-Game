package com.mycompany.a4;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class MoveRightCommand extends Command {
    private GameWorld gw;

    public MoveRightCommand(GameWorld gw) {
        super("Move Right");
        this.gw = gw;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        gw.moveShipRight();
    }
}

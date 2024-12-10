package com.mycompany.a4;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class MoveLeftCommand extends Command {
    private GameWorld gw;

    public MoveLeftCommand(GameWorld gw) {
        super("Move Left");
        this.gw = gw;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        gw.moveShipLeft();
    }
}

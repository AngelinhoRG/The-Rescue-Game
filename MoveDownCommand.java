package com.mycompany.a4;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class MoveDownCommand extends Command {
    private GameWorld gw;

    public MoveDownCommand(GameWorld gw) {
        super("Move Down");
        this.gw = gw;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        gw.moveShipDown();
    }
}

package com.mycompany.a4;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class ExpandCommand extends Command {
    private GameWorld gw;

    public ExpandCommand(GameWorld gw) {
        super("Expand"); // Command name
        this.gw = gw;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        gw.expand(); // Invoke the expand operation in GameWorld
    }
}

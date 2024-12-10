package com.mycompany.a4;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class PretendAlienCollisionCommand extends Command {
    private GameWorld gw;

    public PretendAlienCollisionCommand(GameWorld gw) {
        super("Alien Collision");
        this.gw = gw;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        gw.pretendAlienCollision();
    }
}

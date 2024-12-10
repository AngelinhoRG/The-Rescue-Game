package com.mycompany.a4;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class MoveToAlienCommand extends Command {
	
private GameWorld gw;
    
    public MoveToAlienCommand(GameWorld gw) {
        super("Move to Alien");
        this.gw = gw;
    }
    
    @Override
    public void actionPerformed(ActionEvent evt) {
        gw.moveShipToRandomAlien();
    }

}

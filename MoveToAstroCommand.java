package com.mycompany.a4;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;


public class MoveToAstroCommand extends Command {
	
	private GameWorld gw;

	public MoveToAstroCommand(GameWorld gw) {
		super("Move to Astronaut");
		this.gw = gw;
	}


    @Override
    public void actionPerformed(ActionEvent ev) {
    	gw.moveShipToRandomAstronaut();
    	
    }

}
